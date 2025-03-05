$(document).ready(function() {
    const events = {
        "2024-12-10": [{ eventName: "Food Pantry", eventTime: "2024-12-10", eventDescription: "Description for Food Pantry", location: "Location A", requiredSkills: ["Skill1"], urgency: "High" }],
        "2024-12-15": [{ eventName: "Blood Drive", eventTime: "2024-12-15", eventDescription: "Description for Blood Drive", location: "Location B", requiredSkills: ["Skill2"], urgency: "Medium" }],
        "2025-01-05": [{ eventName: "Community Cleanup", eventTime: "2025-01-05", eventDescription: "Description for Community Cleanup", location: "Location C", requiredSkills: ["Skill3"], urgency: "Low" }],
        "2025-01-20": [{ eventName: "Soup Kitchen", eventTime: "2025-01-20", eventDescription: "Description for Soup Kitchen", location: "Location D", requiredSkills: ["Skill1", "Skill2"], urgency: "High" }]
    };

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
        for (let i = 0; i < firstDay; i++) {
            calendarDays.append('<div class="calendar-day empty"></div>');
        }
        for (let day = 1; day <= totalDays; day++) {
            const date = new Date(currentDate.getFullYear(), currentDate.getMonth(), day);
            const dateString = date.toISOString().split('T')[0];
            const dayEvents = events[dateString] || [];
            const dayElement = $('<div class="calendar-day"></div>');
            dayElement.append(`<span class="day-number">${day}</span>`);
            dayEvents.forEach(event => {
                const eventItem = $('<div class="event-item"></div>');
                eventItem.text(event.eventName);
                eventItem.click(() => {
                    selectedEvent = event;
                    $("#event-name").text(event.eventName);
                    $("#event-time").text(event.eventTime);
                    $("#event-description").text(event.eventDescription);
                    $("#event-location").text(event.location);
                    $("#event-required-skills").text(event.requiredSkills.join(", "));
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
        
                $("#add-event-form").submit((e) => {
                    e.preventDefault();
                    const newEvent = {
                        eventName: $("#new-event-name").val(),
                        eventTime: $("#new-event-date").val(),
                        eventDescription: $("#new-event-description").val(),
                        location: $("#new-location").val(),
                        requiredSkills: $("#new-required-skills").val(),
                        urgency: $("#new-urgency").val()
                    };
                    const eventDate = newEvent.eventTime;
                    if (!events[eventDate]) {
                        events[eventDate] = [];
                    }
                    events[eventDate].push(newEvent);
                    renderCalendar();
                    $("#add-event-modal").addClass("hidden");
                    $("#modal-overlay").addClass("hidden");
                });
        
                renderCalendar();
            });

            const API_URL = "http://localhost:8080/events"; // Adjust if using a different port

            // Fetch events from backend
            async function fetchEvents() {
                try {
                    const response = await fetch(API_URL);
                    const events = await response.json();
                    console.log(events);
                    populateCalendar(events);
                } catch (error) {
                    console.error("Error fetching events:", error);
                }
            }
            
            // Submit event data to backend
            async function submitEvent(eventData) {
                try {
                    const response = await fetch(API_URL, {
                        method: "POST",
                        headers: {
                            "Content-Type": "application/json",
                        },
                        body: JSON.stringify(eventData),
                    });
            
                    if (!response.ok) {
                        const errorData = await response.json();
                        alert("Error: " + JSON.stringify(errorData));
                        return;
                    }
            
                    alert("Event added successfully!");
                    fetchEvents(); // Refresh events on the calendar
                } catch (error) {
                    console.error("Error submitting event:", error);
                }
            }
            
            // Populate calendar with events
            function populateCalendar(events) {
                const calendarDays = document.getElementById("calendar-days");
                calendarDays.innerHTML = ""; // Clear existing days
            
                events.forEach(event => {
                    const eventElement = document.createElement("div");
                    eventElement.classList.add("event-item");
                    eventElement.innerText = event.name;
                    calendarDays.appendChild(eventElement);
                });
            }
            
            // Handle form submission
            document.getElementById("add-event-form").addEventListener("submit", function (e) {
                e.preventDefault();
            
                const eventData = {
                    name: document.getElementById("new-event-name").value,
                    description: document.getElementById("new-event-description").value,
                    location: document.getElementById("new-location").value,
                    requiredSkills: Array.from(document.getElementById("new-required-skills").selectedOptions).map(opt => opt.value),
                    urgency: document.getElementById("new-urgency").value,
                    date: document.getElementById("new-event-date").value,
                };
            
                submitEvent(eventData);
            });
            
            // Load events when page loads
            document.addEventListener("DOMContentLoaded", fetchEvents);
            