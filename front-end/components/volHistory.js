let volunteerHistory = []; // Define globally so all functions can access it
let sortOrder = {}; // Track sort order for each column

async function initializeHistory() {
    console.log("Fetching Volunteer History from Backend...");

    try {
        let response = await fetch("http://localhost:8080/api/volunteer-history"); // Adjust if needed
        if (!response.ok) {
            throw new Error("Failed to fetch volunteer history");
        }

        volunteerHistory = await response.json();
        loadVolunteerHistory();
        console.log("Success - Volunteer History loaded!");
    } catch (error) {
        console.error("Error loading volunteer history:", error);
    }
    console.log("Response status:", response.status);
    console.log("Response data:", volunteerHistory);

}

// In your loadVolunteerHistory function:
function loadVolunteerHistory() {
    const tableBody = document.getElementById("historyTableBody");
    tableBody.innerHTML = "";

    volunteerHistory.forEach(record => {
        const row = `<tr>
                    <td title="${record.eventName}" class="truncate">${record.eventName}</td>
                    <td>${record.eventDate}</td>
                    <td>${record.role || "N/A"}</td>
                    <td>${record.hoursVolunteered}</td>
                    <td>${record.description || "N/A"}</td>
                    <td>${record.location || "N/A"}</td>
                    <td>${record.skills ? record.skills.join(", ") : "N/A"}</td>
                    <td>${record.urgency || "N/A"}</td>
                    <td>${record.participationStatus || "N/A"}</td>
                    <td>${record.status}</td>
                </tr>`;
        tableBody.innerHTML += row;
    });
}

function sortTable(columnIndex) {
    sortOrder[columnIndex] = !sortOrder[columnIndex]; // Toggle order (true = asc, false = desc)
    let isAscending = sortOrder[columnIndex];

    volunteerHistory.sort((a, b) => {
        let valA = Object.values(a)[columnIndex];
        let valB = Object.values(b)[columnIndex];

        if (!isNaN(valA) && !isNaN(valB)) {
            return isAscending ? valA - valB : valB - valA;
        } else {
            return isAscending ? valA.localeCompare(valB) : valB.localeCompare(valA);
        }
    });

    updateSortIcons(columnIndex, isAscending);
    loadVolunteerHistory();
}

function updateSortIcons(columnIndex, isAscending) {
    for (let i = 0; i < 10; i++) {
        let icon = document.getElementById(`icon${i}`);
        if (icon) icon.innerHTML = "🔽"; // Reset all to down arrow
    }
    let activeIcon = document.getElementById(`icon${columnIndex}`);
    if (activeIcon) activeIcon.innerHTML = isAscending ? "🔼" : "🔽";
}

function displayFilteredData(data) {
    const tableBody = document.getElementById("historyTableBody");
    tableBody.innerHTML = "";
    data.forEach(record => {
        const row = `<tr>
                    <td title="${record.event}" class="truncate">${record.event}</td>
                    <td>${record.date}</td>
                    <td>${record.role}</td>
                    <td>${record.hours}</td>
                    <td>${record.description}</td>
                    <td>${record.location}</td>
                    <td>${record.skills ? record.skills.join(", ") : ""}</td>
                    <td>${record.urgency}</td>
                    <td>${record.participationStatus}</td>
                    <td>${record.status}</td>
                </tr>`;
        tableBody.innerHTML += row;
    });
}

// Event Listeners for Sorting
document.querySelectorAll("th").forEach((th, index) => {
    th.addEventListener("click", () => sortTable(index));
});

// Search Filtering
let searchInput = document.getElementById("searchInput");
if (searchInput) {
    searchInput.addEventListener("keyup", filterHistory);
}

function filterHistory() {
    let searchText = searchInput.value.toLowerCase();
    let filteredData = volunteerHistory.filter(record =>
        record.event.toLowerCase().includes(searchText) ||
        record.role.toLowerCase().includes(searchText) ||
        record.description.toLowerCase().includes(searchText) ||
        record.location.toLowerCase().includes(searchText) ||
        record.skills.join(", ").toLowerCase().includes(searchText) ||
        record.urgency.toLowerCase().includes(searchText) ||
        record.participationStatus.toLowerCase().includes(searchText) ||
        record.status.toLowerCase().includes(searchText)
    );
    displayFilteredData(filteredData);
}

// Status Filter
let statusFilter = document.getElementById("statusFilter");
if (statusFilter) {
    statusFilter.addEventListener("change", filterByStatus);
}

function filterByStatus() {
    let status = statusFilter.value;
    let filteredData = status ? volunteerHistory.filter(record => record.status === status) : volunteerHistory;
    displayFilteredData(filteredData);
}

// Ensure `initializeHistory()` is called when `volHistory.html` is dynamically loaded
document.addEventListener("DOMContentLoaded", () => {
    if (document.getElementById("historyTableBody")) {
        initializeHistory();
    }
});
