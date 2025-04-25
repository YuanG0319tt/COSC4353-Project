let selectedEvent = null;
$(document).ready(function () {
    const API_URL = "http://localhost:8080/events";
    const events = {}; // Will be filled dynamically from DB
    let currentDate = new Date();

    function daysInMonth(date) {
        return new Date(date.getFullYear(), date.getMonth() + 1, 0).getDate();
    }

    function firstDayOfMonth(date) {
        return new Date(date.getFullYear(), date.getMonth(), 1).getDay();
    }

    function renderCalendar() {
        const calendarDays = $("#calendar-days");
        calendarDays.empty();
        const totalDays = daysInMonth(currentDate);
        const firstDay = firstDayOfMonth(currentDate);

        for (let i = 0; i < firstDay; i++) {
            calendarDays.append('<div class="calendar-day empty"></div>');
        }

        for (let day = 1; day <= totalDays; day++) {
            const date = new Date(currentDate.getFullYear(), currentDate.getMonth(), day);
            const dateString = date.toISOString().split('T')[0];
            console.log(`Calendar Day: ${dateString} →`, events[dateString]);

            const dayEvents = events[dateString] || [];

            const dayElement = $('<div class="calendar-day"></div>');
            dayElement.append(`<span class="day-number">${day}</span>`);

            dayEvents.forEach(event => {
                const eventItem = $('<div class="event-item"></div>').text(event.eventName);
                eventItem.click(() => {
                    selectedEvent = event;

                    $("#event-name").text(event.eventName);
                    $("#event-time").text(new Date(event.eventDate).toISOString().split('T')[0]);
                    $("#event-description").text(event.description);
                    $("#event-location").text(event.location);
                    $("#event-required-skills").text(event.requiredSkills);
                    $("#event-urgency").text(event.urgency);
                    $("#event-modal").removeClass("hidden");
                    $("#modal-overlay").removeClass("hidden");

                    // Rebind delete handler each time modal opens
                    $("#delete-event-button").off("click").on("click", async () => {
                        if (!selectedEvent || !selectedEvent.eventID) {
                            console.warn("Missing event or eventID:", selectedEvent);
                            alert("No valid event selected.");
                            return;
                        }

                        if (!confirm("Are you sure you want to delete this event?")) return;

                        try {
                            const res = await fetch(`${API_URL}/${selectedEvent.eventID}`, { method: "DELETE" });
                            if (!res.ok) throw new Error("Failed to delete event");

                            alert("Event deleted.");
                            $("#event-modal").addClass("hidden");
                            $("#modal-overlay").addClass("hidden");
                            fetchEvents();
                        } catch (err) {
                            console.error(err);
                            alert("Error deleting event.");
                        }
                    });

                    $("#edit-event-button").on("click", () => {
                        if (!selectedEvent) return alert("No event selected.");

                        // Pre-fill form fields
                        $("#edit-event-name").val(selectedEvent.eventName);
                        $("#edit-event-description").val(selectedEvent.description);
                        $("#edit-location").val(selectedEvent.location);
                        $("#edit-required-skills").val(selectedEvent.requiredSkills);
                        $("#edit-urgency").val(selectedEvent.urgency);
                        $("#edit-event-date").val(selectedEvent.eventDate);

                        $("#edit-event-modal").removeClass("hidden");
                        $("#modal-overlay").removeClass("hidden");
                        $("#event-modal").addClass("hidden");
                    });

                    $("#close-edit-event-modal").on("click", () => {
                        $("#edit-event-modal").addClass("hidden");
                        $("#modal-overlay").addClass("hidden");
                    });

                    $("#edit-event-form").on("submit", async function (e) {
                        e.preventDefault();

                        const eventName = $("#edit-event-name").val().trim();
                        const description = $("#edit-event-description").val().trim();
                        const location = $("#edit-location").val().trim();
                        const requiredSkills = $("#edit-required-skills").val().trim();
                        const urgency = parseInt($("#edit-urgency").val());
                        const eventDate = $("#edit-event-date").val();

                        if (!eventName || eventName.length > 100) {
                            return alert("Event name is required and must be less than 100 characters.");
                        }

                        if (!description) return alert("Description is required.");
                        if (!location) return alert("Location is required.");
                        if (!requiredSkills) return alert("Required skills cannot be empty.");
                        if (![1, 2, 3].includes(urgency)) return alert("Urgency must be High, Medium, or Low.");
                        if (!eventDate || isNaN(new Date(eventDate).getTime())) return alert("A valid event date is required.");

                        const updatedEvent = {
                            eventName,
                            description,
                            location,
                            requiredSkills,
                            urgency,
                            eventDate
                        };

                        try {
                            const response = await fetch(`${API_URL}/${selectedEvent.eventID}`, {
                                method: "PUT",
                                headers: {
                                    "Content-Type": "application/json"
                                },
                                body: JSON.stringify(updatedEvent)
                            });

                            if (!response.ok) throw new Error("Update failed.");

                            alert("Event updated.");
                            $("#edit-event-modal").addClass("hidden");
                            $("#modal-overlay").addClass("hidden");
                            fetchEvents();
                        } catch (err) {
                            console.error(err);
                            alert("Error updating event.");
                        }
                    });
                });
                dayElement.append(eventItem);
            });

            calendarDays.append(dayElement);
        }

        $("#current-month-year").text(`${currentDate.toLocaleString('default', { month: 'long' })} ${currentDate.getFullYear()}`);
    }

    function changeMonth(increment) {
        currentDate = new Date(currentDate.getFullYear(), currentDate.getMonth() + increment, 1);
        renderCalendar();
    }

    $("#prev-month").click(() => changeMonth(-1));
    $("#next-month").click(() => changeMonth(1));
    $("#close-event-modal").click(() => {
        $("#event-modal").addClass("hidden");
        $("#modal-overlay").addClass("hidden");
    });

    $("#add-event-button").click(() => {
        $("#add-event-modal").removeClass("hidden");
        $("#modal-overlay").removeClass("hidden");
    });

    $("#close-add-event-modal").click(() => {
        $("#add-event-modal").addClass("hidden");
        $("#modal-overlay").addClass("hidden");
    });

    function populateCalendar(backendEvents) {
        console.log("populateCalendar() called");
        console.log("Incoming backend events:", backendEvents);
        for (const key in events) delete events[key];

        backendEvents.forEach(event => {
            const localDate = new Date(event.eventDate);
            const yyyy = localDate.getFullYear();
            const mm = String(localDate.getMonth() + 1).padStart(2, '0');
            const dd = String(localDate.getDate()).padStart(2, '0');
            const dateKey = `${yyyy}-${mm}-${dd}`;

            if (!events[dateKey]) events[dateKey] = [];
            events[dateKey].push(event);
        });

        console.log("Mapped event keys:", Object.keys(events));
        renderCalendar();
    }

    async function fetchEvents() {
        try {
            const response = await fetch(API_URL);
            const data = await response.json();
            console.log("Fetched from backend:", data);
            populateCalendar(data);
        } catch (error) {
            console.error("Error fetching events:", error);
        }
    }

    async function submitEvent(eventData) {
        try {
            const response = await fetch(API_URL, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(eventData)
            });

            if (!response.ok) {
                const errorData = await response.json();
                alert("Error: " + JSON.stringify(errorData));
                return;
            }

            alert("Event added successfully!");
            $("#add-event-modal").addClass("hidden");
            $("#modal-overlay").addClass("hidden");
            await fetchEvents();
        } catch (error) {
            console.error("Error submitting event:", error);
        }
    }

    document.getElementById("add-event-form").addEventListener("submit", async function (e) {
        e.preventDefault();

        const eventName = document.getElementById("new-event-name").value.trim();
        const description = document.getElementById("new-event-description").value.trim();
        const location = document.getElementById("new-location").value.trim();
        const requiredSkills = Array.from(document.getElementById("new-required-skills").selectedOptions)
            .map(opt => opt.value).join(', ');
        const urgencyStr = document.getElementById("new-urgency").value;
        const eventDate = document.getElementById("new-event-date").value;

        const urgencyMap = { High: 1, Medium: 2, Low: 3 };
        const urgency = urgencyMap[urgencyStr];

        // Frontend validation
        if (!eventName || eventName.length > 100) {
            return alert("Event name is required and must be less than 100 characters.");
        }

        if (!description) return alert("Description is required.");
        if (!location) return alert("Location is required.");
        if (!requiredSkills) return alert("Required skills must be selected.");
        if (!urgency || ![1, 2, 3].includes(urgency)) return alert("Urgency must be selected.");
        if (!eventDate || isNaN(new Date(eventDate).getTime())) return alert("Valid event date is required.");

        const eventData = {
            eventName,
            description,
            location,
            requiredSkills,
            urgency,
            eventDate
        };

        try {
            const response = await fetch(API_URL, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(eventData)
            });

            if (!response.ok) {
                const errorData = await response.json();
                alert("Error: " + JSON.stringify(errorData));
                return;
            }

            alert("Event added successfully!");
            $("#add-event-modal").addClass("hidden");
            $("#modal-overlay").addClass("hidden");
            await fetchEvents();
        } catch (error) {
            console.error("Error submitting event:", error);
        }
    });

    console.log("Calling fetchEvents()");
    fetchEvents();
    window.fetchEvents = fetchEvents;
    window.renderCalendar = renderCalendar;
});
