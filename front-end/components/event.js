$(document).ready(function() {
    const API_URL = "http://localhost:8080/api/events";
    let currentDate = new Date();
    let selectedEvent = null;
    let editingEventId = null;
    let events = {};


    async function init() {
        await fetchEvents();
        renderCalendar();
        setupEventListeners();
    }

    async function fetchEvents() {
        try {
            const response = await fetch(API_URL+'/list', {
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${localStorage.getItem('userToken') || ''}`
                }
            });

            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }

            const result = await response.json();
            events = {};

            result.data.forEach(event => {
                const dateStr = event.date;

                if (!events[dateStr]) {
                    events[dateStr] = [];
                }

                const skills = typeof event.requiredSkills === 'string'
                    ? [event.requiredSkills]
                    : event.requiredSkills || [];

                events[dateStr].push({
                    eventName: event.name,
                    eventTime: event.date,
                    eventDescription: event.description,
                    location: event.location,
                    requiredSkills: skills,
                    urgency: event.urgency,
                    id: event.id
                });
            });
        } catch (error) {
            console.error("Error fetching events:", error);
            showMessage(error.message, "error");
        }
    }

    function formatLocalDate(date) {
        return date.toLocaleDateString('en-US', {
            timeZone: 'UTC',
            year: 'numeric',
            month: 'short',
            day: '2-digit'
        });
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
            const dateString = formatLocalDate(date);

            const dayEvents = events[dateString] || [];

            const dayElement = $(`<div class="calendar-day" data-date="${dateString}"></div>`);
            dayElement.append(`<span class="day-number">${day}</span>`);

            dayEvents.forEach(event => {
                const eventItem = $(`<div class="event-item ${event.urgency.toLowerCase()}"></div>`);
                eventItem.text(event.eventName);
                eventItem.click(() => showEventDetails(event));
                dayElement.append(eventItem);
            });

            calendarDays.append(dayElement);
        }

        $("#current-month-year").text(`${currentDate.toLocaleString('default', { month: 'long' })} ${currentDate.getFullYear()}`);
    }


    function showEventDetails(event) {
        selectedEvent = event;
        $("#event-name").text(event.eventName);
        $("#event-time").text(event.eventTime);
        $("#event-description").text(event.eventDescription);
        $("#event-location").text(event.location);
        $("#event-required-skills").text(event.requiredSkills.join(", "));
        $("#event-urgency").text(event.urgency);
        $("#event-modal").removeClass("hidden");
        $("#modal-overlay").removeClass("hidden");
    }

    function showEditEventModal() {
        if (!selectedEvent) return;

        // hide view modal
        $("#event-modal").addClass("hidden");

        // populate edit form fields
        $("#edit-event-name").val( selectedEvent.eventName );
        $("#edit-event-description").val( selectedEvent.eventDescription );
        $("#edit-location").val( selectedEvent.location );

        // if your select allows multiple, make sure <select multiple> in HTML
        $("#edit-required-skills").val( selectedEvent.requiredSkills );
        $("#edit-urgency").val( selectedEvent.urgency );

        // eventDate should be YYYY-MM-DD
        $("#edit-event-date").val( selectedEvent.eventDate );

        // show edit modal
        $("#edit-event-modal").removeClass("hidden");
        $("#modal-overlay").removeClass("hidden");
    }

    async function addNewEvent(eventData) {
        event.preventDefault();
        const submitButton = document.querySelector("#add-event-form button[type='submit']");
        if (submitButton.disabled) return;
        submitButton.disabled = true;

        try {

            const dateObj = new Date(eventData.eventTime);
            const formattedDate = formatLocalDate(dateObj);

            const response = await fetch(API_URL+'/add', {
                method: "POST",
                headers: {
                    'Authorization': `Bearer ${localStorage.getItem('userToken') || ''}`,
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    name: eventData.eventName,
                    description: eventData.eventDescription,
                    location: eventData.location,
                    requiredSkills: eventData.requiredSkills,
                    urgency: eventData.urgency,
                    date: formattedDate
                })
            });

            showMessage("Successful", "success");
            await fetchEvents(); // 刷新事件列表
            renderCalendar();
            return true;
        } catch (error) {
            console.error("Error adding event:", error);
            showMessage(error.message, "error");
            return false;
        } finally {
            submitButton.disabled = false;
        }
    }



    function daysInMonth(date) {
        return new Date(date.getFullYear(), date.getMonth() + 1, 0).getDate();
    }

    function firstDayOfMonth(date) {
        return new Date(date.getFullYear(), date.getMonth(), 1).getDay();
    }

    function changeMonth(increment) {
        currentDate = new Date(currentDate.getFullYear(), currentDate.getMonth() + increment, 1);
        renderCalendar();
    }

    function showMessage(message, type) {
        const alertClass = type === "success" ? "alert-success" : "alert-danger";
        const messageDiv = $(`
            <div class="alert ${alertClass} alert-dismissible fade show" role="alert">
                ${message}
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        `);

        $("#messageContainer").html(messageDiv);
        setTimeout(() => messageDiv.alert('close'), 3000);
    }

    
    function setupEventListeners() {
        $("#prev-month").click(() => changeMonth(-1));
        $("#next-month").click(() => changeMonth(1));

        $("#close-event-modal, #modal-overlay").click(() => {
            $("#event-modal").addClass("hidden");
            $("#modal-overlay").addClass("hidden");
        });

        $("#add-event-button").click(() => {
            $("#new-event-date").val(new Date().toISOString().split('T')[0]);
            $("#add-event-modal").removeClass("hidden");
            $("#modal-overlay").removeClass("hidden");
        });

        $("#close-add-event-modal").click(() => {
            $("#add-event-modal").addClass("hidden");
            $("#modal-overlay").addClass("hidden");
        });

        $("#edit-event-button").click(() => {
            $("#new-event-date").val(new Date().toISOString().split('T')[0]);
            $("#edit-event-modal").removeClass("hidden");
            //$("#modal-overlay").removeClass("hidden");
        });


        // $("#close-edit-event-modal, #modal-overlay").click(() => {
        //     $("#edit-event-modal, #modal-overlay").addClass("hidden");
        // });

        $("#delete-event-button").click(async () => {
            //if (!confirm("Are you sure you want to delete this event?")) return;
            try {
                await fetch(`${API_URL}/delete/${selectedEvent.id}`, {
                    method: "DELETE",
                    headers: {
                        'Authorization': `Bearer ${localStorage.getItem('userToken')||''}`
                    }
                });
                showMessage("Event deleted", "success");
                $("#event-modal, #modal-overlay").addClass("hidden");
                await fetchEvents();
                renderCalendar();
            } catch (err) {
                console.error(err);
                showMessage("Delete failed", "error");
            }
        });

        $("#add-event-form").submit(async (e) => {
            e.preventDefault();

            const newEvent = {
                eventName: $("#new-event-name").val().trim(),
                eventTime: $("#new-event-date").val(),
                eventDescription: $("#new-event-description").val().trim(),
                location: $("#new-location").val().trim(),
                requiredSkills: $("#new-required-skills").val() || [],
                urgency: $("#new-urgency").val()
            };

            if (!newEvent.eventName || !newEvent.eventTime) {
                showMessage("Please fill this", "error");
                return;
            }

            const success = await addNewEvent(newEvent);
            if (success) {
                $("#add-event-form")[0].reset();
                $("#add-event-modal").addClass("hidden");
                $("#modal-overlay").addClass("hidden");
            }
        });
    }


    init();
});
