var selectedEvent = null;

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

        const year = currentDate.getFullYear();
        const month = currentDate.getMonth() + 1;

        for (let day = 1; day <= totalDays; day++) {
            const yyyy = year;
            const mm = String(month).padStart(2, '0');
            const dd = String(day).padStart(2, '0');
            const dateString = `${yyyy}-${mm}-${dd}`;

            const dayEvents = events[dateString] || [];
            const dayElement = $('<div class="calendar-day"></div>');
            dayElement.append(`<span class="day-number">${day}</span>`);

            dayEvents.forEach(event => {
                const eventItem = $('<div class="event-item"></div>').text(event.eventName);
                eventItem.click(() => openEventModal(event));
                dayElement.append(eventItem);
            });

            calendarDays.append(dayElement);
        }

        $("#current-month-year").text(`${currentDate.toLocaleString('default', { month: 'long' })} ${currentDate.getFullYear()}`);
    }

    function openEventModal(event) {
        selectedEvent = event;
    
        let eventDateStr = "";
        if (typeof event.eventDate === "string") {
            eventDateStr = event.eventDate.slice(0, 10);
        } else if (typeof event.eventDate === "number") {
            // If it's a timestamp
            eventDateStr = new Date(event.eventDate).toISOString().slice(0, 10);
        } else if (event.eventDate instanceof Object && event.eventDate !== null) {
            // If it's a Date object
            eventDateStr = new Date(event.eventDate).toISOString().slice(0, 10);
        } else {
            eventDateStr = "Unknown Date";
        }
    
        $("#event-name").text(event.eventName);
        $("#event-time").text(eventDateStr);
        $("#event-description").text(event.description);
        $("#event-location").text(event.location);
        $("#event-required-skills").text(event.requiredSkills);
        $("#event-urgency").text(event.urgency);
        $("#event-modal, #modal-overlay").removeClass("hidden");
    
        $("#edit-event-button").off("click").on("click", () => {
            if (!selectedEvent) return alert("No event selected.");
    
            $("#edit-event-name").val(selectedEvent.eventName);
            $("#edit-event-description").val(selectedEvent.description);
            $("#edit-location").val(selectedEvent.location);
            $("#edit-required-skills").val(selectedEvent.requiredSkills);
            $("#edit-urgency").val(selectedEvent.urgency);
            $("#edit-event-date").val(eventDateStr);
    
            $("#edit-event-modal").removeClass("hidden");
            $("#modal-overlay").removeClass("hidden");
            $("#event-modal").addClass("hidden");
        });
    
        $("#close-edit-event-modal").off("click").on("click", () => {
            $("#edit-event-modal, #modal-overlay").addClass("hidden");
        });
    }    

    function changeMonth(increment) {
        currentDate = new Date(currentDate.getFullYear(), currentDate.getMonth() + increment, 1);
        renderCalendar();
    }

    $("#prev-month").click(() => changeMonth(-1));
    $("#next-month").click(() => changeMonth(1));
    $("#close-event-modal").click(() => { $("#event-modal, #modal-overlay").addClass("hidden"); });
    $("#add-event-button").click(() => { $("#add-event-modal, #modal-overlay").removeClass("hidden"); });
    $("#close-add-event-modal").click(() => { $("#add-event-modal, #modal-overlay").addClass("hidden"); });

    function populateCalendar(backendEvents) {
        for (const key in events) delete events[key];
    
        backendEvents.forEach(event => {
            const rawDate = event.eventDate;
            const dateStr = typeof rawDate === 'string'
                ? rawDate.slice(0, 10)
                : new Date(rawDate).toISOString().slice(0, 10); // fallback if it's a Date object
    
            if (!events[dateStr]) events[dateStr] = [];
            events[dateStr].push(event);
        });
    
        renderCalendar();
    }    

    async function fetchEvents() {
        try {
            const response = await fetch(API_URL);
            const data = await response.json();
            populateCalendar(data);
        } catch (error) {
            console.error("Error fetching events:", error);
        }
    }

    $("#edit-event-form").off("submit").on("submit", async function (e) {
        e.preventDefault();

        const eventName = $("#edit-event-name").val().trim();
        const description = $("#edit-event-description").val().trim();
        const location = $("#edit-location").val().trim();
        const requiredSkills = $("#edit-required-skills").val().trim();
        const urgency = parseInt($("#edit-urgency").val());
        const eventDate = $("#edit-event-date").val().trim();

        const datePattern = /^\d{4}-\d{2}-\d{2}$/;

        if (!eventName || eventName.length > 100) return alert("Event name is required and must be less than 100 characters.");
        if (!description) return alert("Description is required.");
        if (!location) return alert("Location is required.");
        if (!requiredSkills) return alert("Required skills cannot be empty.");
        if (![1,2,3].includes(urgency)) return alert("Urgency must be High, Medium, or Low.");
        if (!datePattern.test(eventDate)) return alert("A valid event date (YYYY-MM-DD) is required.");

        const updatedEvent = { eventName, description, location, requiredSkills, urgency, eventDate };

        try {
            const response = await fetch(`${API_URL}/${selectedEvent.eventID}`, {
                method: "PUT",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(updatedEvent)
            });

            if (!response.ok) throw new Error("Update failed.");

            alert("Event updated successfully.");
            $("#edit-event-modal, #modal-overlay").addClass("hidden");
            await fetchEvents();
        } catch (err) {
            console.error(err);
            alert("Error updating event.");
        }
    });

    document.getElementById("add-event-form").addEventListener("submit", async function (e) {
        e.preventDefault();

        const eventName = document.getElementById("new-event-name").value.trim();
        const description = document.getElementById("new-event-description").value.trim();
        const location = document.getElementById("new-location").value.trim();
        const requiredSkills = Array.from(document.getElementById("new-required-skills").selectedOptions).map(opt => opt.value).join(', ');
        const urgencyStr = document.getElementById("new-urgency").value;
        const eventDateInput = document.getElementById("new-event-date").value.trim();

        const urgencyMap = { High: 1, Medium: 2, Low: 3 };
        const urgency = urgencyMap[urgencyStr];

        if (!eventName || eventName.length > 100) return alert("Event name is required and must be less than 100 characters.");
        if (!description) return alert("Description is required.");
        if (!location) return alert("Location is required.");
        if (!requiredSkills) return alert("Required skills must be selected.");
        if (!urgency || ![1,2,3].includes(urgency)) return alert("Urgency must be selected.");
        if (!eventDateInput || isNaN(new Date(eventDateInput).getTime())) return alert("Valid event date is required.");

        const eventDate = eventDateInput;
        const eventData = { eventName, description, location, requiredSkills, urgency, eventDate };

        try {
            const response = await fetch(API_URL, {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(eventData)
            });

            if (!response.ok) throw new Error("Failed to create event.");

            alert("Event added successfully!");
            $("#add-event-modal, #modal-overlay").addClass("hidden");
            await fetchEvents();
        } catch (error) {
            console.error("Error submitting event:", error);
        }
    });

    $("#delete-event-button").off("click").on("click", async () => {
        if (!selectedEvent || !selectedEvent.eventID) {
            return alert("No valid event selected.");
        }

        if (!confirm("Are you sure you want to delete this event?")) return;

        try {
            const response = await fetch(`${API_URL}/${selectedEvent.eventID}`, { 
                method: "DELETE",
                headers: { "Content-Type": "application/json" }
            });

            if (!response.ok) throw new Error("Failed to delete event.");

            alert("Event deleted successfully!");
            $("#event-modal, #modal-overlay").addClass("hidden");
            await fetchEvents();
        } catch (err) {
            console.error("Delete error:", err);
            alert(`Error deleting event: ${err.message}`);
        }
    });

    fetchEvents();
    window.fetchEvents = fetchEvents;
    window.renderCalendar = renderCalendar;
});
