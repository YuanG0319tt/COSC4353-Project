console.log("Event JS loaded ✅");

$(document).ready(function () {
    console.log("🧠 jQuery DOM Ready: event.js is running");
    const API_URL = "http://localhost:8080/events";
    const events = {}; // Will be filled dynamically from DB
    let currentDate = new Date();
    let selectedEvent = null;

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
    
        // Create empty slots at the start
        for (let i = 0; i < firstDay; i++) {
            calendarDays.append('<div class="calendar-day empty"></div>');
        }
    
        // Generate days
        for (let day = 1; day <= totalDays; day++) {
            const date = new Date(currentDate.getFullYear(), currentDate.getMonth(), day);
    
            // Get local YYYY-MM-DD
            const dateString = date.toISOString().split('T')[0];
    
            // DEBUG LINE
            console.log(`Calendar Day: ${dateString} →`, events[dateString]);
    
            const dayEvents = events[dateString] || [];
    
            const dayElement = $('<div class="calendar-day"></div>');
            dayElement.append(`<span class="day-number">${day}</span>`);
    
            // Add any matching events
            dayEvents.forEach(event => {
                const eventItem = $('<div class="event-item"></div>').text(event.eventName);
                eventItem.click(() => {
                    $("#event-name").text(event.eventName);
                    $("#event-time").text(event.eventDate);
                    $("#event-description").text(event.description);
                    $("#event-location").text(event.location);
                    $("#event-required-skills").text(event.requiredSkills);
                    $("#event-urgency").text(event.urgency);
                    $("#event-modal").removeClass("hidden");
                    $("#modal-overlay").removeClass("hidden");
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
            // Convert to local YYYY-MM-DD
            const localDate = new Date(event.eventDate);
            const yyyy = localDate.getFullYear();
            const mm = String(localDate.getMonth() + 1).padStart(2, '0');
            const dd = String(localDate.getDate()).padStart(2, '0');
            const dateKey = `${yyyy}-${mm}-${dd}`; // e.g. "2025-04-22"
    
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
            await fetchEvents(); // Reload events
        } catch (error) {
            console.error("Error submitting event:", error);
        }
    }

    document.getElementById("add-event-form").addEventListener("submit", function (e) {
        e.preventDefault();

        const urgencyMap = {
            High: 1,
            Medium: 2,
            Low: 3
          };
          
          const eventData = {
            eventName: document.getElementById("new-event-name").value,
            description: document.getElementById("new-event-description").value,
            location: document.getElementById("new-location").value,
            requiredSkills: Array.from(document.getElementById("new-required-skills").selectedOptions)
                                .map(opt => opt.value).join(', '),
            urgency: urgencyMap[document.getElementById("new-urgency").value],
            eventDate: document.getElementById("new-event-date").value
          };          

        submitEvent(eventData);
    });

    console.log("Calling fetchEvents()");
    fetchEvents();
    window.fetchEvents = fetchEvents;
    window.renderCalendar = renderCalendar;
});