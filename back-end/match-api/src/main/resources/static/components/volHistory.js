function initializeHistory() {
    console.log("Initializing Volunteer History...");

    let volunteerHistory = [];

    let sortOrder = {}; // Track sort order for each column

    async function fetchVolunteerHistoryFromBackend() {
        try {
            const response = await fetch("http://localhost:8080/volunteer-history");
            const data = await response.json();
            console.log("Fetched history from backend:", data);
            volunteerHistory = data;
            loadVolunteerHistory(); // Render the table
        } catch (error) {
            console.error("Failed to fetch volunteer history:", error);
        }
    }    

    function loadVolunteerHistory() {
        const tableBody = document.getElementById("historyTableBody");
        tableBody.innerHTML = "";
        volunteerHistory.forEach(record => {
            const row = `<tr>
                <td>${record.eventName}</td>
                <td>${record.eventDate}</td>
                <td>${record.name}</td>
                <td>${record.hoursVolunteered}</td>
                <td>${record.description}</td>
                <td>${record.location}</td>
                <td>${record.skills ? record.skills.split(',').join(", ") : ""}</td>
                <td>${record.urgency}</td>
                <td>${record.participationStatus}</td>
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
                        <td>${record.skills.join(", ")}</td>
                        <td>${record.urgency}</td>
                        <td>${record.participationStatus}</td>
                        <td>${record.status}</td>
                    </tr>`;
            tableBody.innerHTML += row;
        });
    }

    // Event Listeners for Sorting
    document.querySelectorAll("th").forEach((th, index) => {
        th.removeEventListener("click", () => sortTable(index)); // Ensure no duplicate events
        th.addEventListener("click", () => sortTable(index));
    });

    // Search Filtering
    let searchInput = document.getElementById("searchInput");
    if (searchInput) {
        searchInput.removeEventListener("keyup", filterHistory);
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
        statusFilter.removeEventListener("change", filterByStatus);
        statusFilter.addEventListener("change", filterByStatus);
    }

    function filterByStatus() {
        let status = statusFilter.value;
        let filteredData = status ? volunteerHistory.filter(record => record.status === status) : volunteerHistory;
        displayFilteredData(filteredData);
    }

    // Load initial data
    fetchVolunteerHistoryFromBackend();
}

// Ensure `initializeHistory()` is called when `volHistory.html` is dynamically loaded
document.addEventListener("DOMContentLoaded", () => {
    if (document.getElementById("historyTableBody")) {
        initializeHistory();
    }
});
