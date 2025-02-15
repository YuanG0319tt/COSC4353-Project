function initializeHistory() {
    console.log("Initializing Volunteer History...");

    let volunteerHistory = [
        { event: "Food Pantry", date: "2024-12-10", role: "Helper", hours: 5, status: "Completed" },
        { event: "Blood Drive", date: "2024-12-15", role: "Donor", hours: 2, status: "Completed" },
        { event: "Community Cleanup", date: "2025-01-05", role: "Organizer", hours: 8, status: "Pending" },
        { event: "Soup Kitchen", date: "2025-01-20", role: "Cook", hours: 6, status: "Completed" }
    ];

    let sortOrder = {}; // Track sort order for each column

    function loadVolunteerHistory() {
        const tableBody = document.getElementById("historyTableBody");
        tableBody.innerHTML = "";
        volunteerHistory.forEach(record => {
            const row = `<tr>
                        <td title="${record.event}" class="truncate">${record.event}</td>
                        <td>${record.date}</td>
                        <td>${record.role}</td>
                        <td>${record.hours}</td>
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
        for (let i = 0; i < 5; i++) {
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
            record.role.toLowerCase().includes(searchText)
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
    loadVolunteerHistory();
}

// Ensure `initializeHistory()` is called when `volHistory.html` is dynamically loaded
document.addEventListener("DOMContentLoaded", () => {
    if (document.getElementById("historyTableBody")) {
        initializeHistory();
    }
});
