// Define an array to store events
let events = [];

// Variables to store event input fields and reminder list
let eventDateInput = document.getElementById("eventDate");
let eventTitleInput = document.getElementById("eventTitle");
let eventDescriptionInput = document.getElementById("eventDescription");
let reminderList = document.getElementById("reminderList");

// Counter to generate unique event IDs
let eventIdCounter = 1;

// Function to add events
function addEvent() {
    let date = new Date(eventDateInput.value + "T00:00:00");
    let title = eventTitleInput.value;
    let description = eventDescriptionInput.value;

    if (!isNaN(date.getTime()) && title.trim() !== "") {
        let eventId = eventIdCounter++;

        events.push({
            id: eventId,
            date: date.toISOString().split("T")[0], // Store in YYYY-MM-DD format
            title: title,
            description: description
        });

        showCalendar(currentMonth, currentYear);
        eventDateInput.value = "";
        eventTitleInput.value = "";
        eventDescriptionInput.value = "";
        displayReminders();
    }
}

// Function to delete an event by ID
function deleteEvent(eventId) {
    let eventIndex = events.findIndex((event) => event.id === eventId);

    if (eventIndex !== -1) {
        events.splice(eventIndex, 1);
        showCalendar(currentMonth, currentYear);
        displayReminders();
    }
}

// Function to display reminders
function displayReminders() {
    reminderList.innerHTML = "";
    for (let i = 0; i < events.length; i++) {
        let event = events[i];
        let eventDate = new Date(event.date + "T00:00:00");

        if (eventDate.getMonth() === currentMonth && eventDate.getFullYear() === currentYear) {
            let listItem = document.createElement("li");
            listItem.className = "list-group-item d-flex justify-content-between align-items-center";
            listItem.innerHTML = `<span><strong>${event.title}</strong> - ${event.description} on ${eventDate.toLocaleDateString()}</span>`;

            let deleteButton = document.createElement("button");
            deleteButton.className = "btn btn-danger btn-sm";
            deleteButton.textContent = "Delete";
            deleteButton.onclick = function () {
                deleteEvent(event.id);
            };

            listItem.appendChild(deleteButton);
            reminderList.appendChild(listItem);
        }
    }
}

// Function to generate year options
function generate_year_range(start, end) {
    let years = "";
    for (let year = start; year <= end; year++) {
        years += `<option value="${year}">${year}</option>`;
    }
    return years;
}

// Initialize date-related variables
let today = new Date();
let currentMonth = today.getMonth();
let currentYear = today.getFullYear();
let selectYear = document.getElementById("year");
let selectMonth = document.getElementById("month");

selectYear.innerHTML = generate_year_range(1970, 2050);

let calendar = document.getElementById("calendar");

let months = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];
let days = ["Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"];

document.getElementById("thead-month").innerHTML = "<tr>" + days.map(day => `<th>${day}</th>`).join('') + "</tr>";

let monthAndYear = document.getElementById("monthAndYear");
showCalendar(currentMonth, currentYear);

// Function to navigate months
function next() {
    currentYear = currentMonth === 11 ? currentYear + 1 : currentYear;
    currentMonth = (currentMonth + 1) % 12;
    showCalendar(currentMonth, currentYear);
}

function previous() {
    currentYear = currentMonth === 0 ? currentYear - 1 : currentYear;
    currentMonth = currentMonth === 0 ? 11 : currentMonth - 1;
    showCalendar(currentMonth, currentYear);
}

function jump() {
    currentYear = parseInt(selectYear.value);
    currentMonth = parseInt(selectMonth.value);
    showCalendar(currentMonth, currentYear);
}

// Function to display the calendar
function showCalendar(month, year) {
    let firstDay = new Date(year, month, 1).getDay();
    let tbl = document.getElementById("calendar-body");
    tbl.innerHTML = "";
    monthAndYear.innerHTML = `${months[month]} ${year}`;
    selectYear.value = year;
    selectMonth.value = month;

    let date = 1;
    for (let i = 0; i < 6; i++) {
        let row = document.createElement("tr");
        for (let j = 0; j < 7; j++) {
            if (i === 0 && j < firstDay) {
                let cell = document.createElement("td");
                cell.appendChild(document.createTextNode(""));
                row.appendChild(cell);
            } else if (date > daysInMonth(month, year)) {
                break;
            } else {
                let cell = document.createElement("td");
                cell.setAttribute("data-date", date);
                cell.setAttribute("data-month", month + 1);
                cell.setAttribute("data-year", year);
                cell.setAttribute("data-month_name", months[month]);
                cell.className = "date-picker";
                cell.innerHTML = `<span>${date}</span>`;

                if (date === today.getDate() && year === today.getFullYear() && month === today.getMonth()) {
                    cell.classList.add("selected");
                }

                if (hasEventOnDate(date, month, year)) {
                    cell.classList.add("event-marker");
                    cell.appendChild(createEventTooltip(date, month, year));
                }

                row.appendChild(cell);
                date++;
            }
        }
        tbl.appendChild(row);
    }

    displayReminders();
}

// Function to create an event tooltip
function createEventTooltip(date, month, year) {
    let tooltip = document.createElement("div");
    tooltip.className = "event-tooltip";
    let eventsOnDate = getEventsOnDate(date, month, year);
    eventsOnDate.forEach(event => {
        let eventDate = new Date(event.date + "T00:00:00");
        let eventElement = document.createElement("p");
        eventElement.innerHTML = `<strong>${event.title}</strong> - ${event.description} on ${eventDate.toLocaleDateString()}`;
        tooltip.appendChild(eventElement);
    });
    return tooltip;
}

// Function to get events on a specific date
function getEventsOnDate(date, month, year) {
    return events.filter(event => {
        let eventDate = new Date(event.date + "T00:00:00");
        return eventDate.getDate() === date && eventDate.getMonth() === month && eventDate.getFullYear() === year;
    });
}

// Function to check if there are events on a specific date
function hasEventOnDate(date, month, year) {
    return getEventsOnDate(date, month, year).length > 0;
}

// Function to get the number of days in a month
function daysInMonth(iMonth, iYear) {
    return 32 - new Date(iYear, iMonth, 32).getDate();
}

// Call showCalendar initially
showCalendar(currentMonth, currentYear);
