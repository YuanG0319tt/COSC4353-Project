function initializeHistory() {
    console.log("Initializing Volunteer History...");

    let volunteerHistory = [];
    let sortOrder = {};

    async function fetchVolunteerHistoryFromBackend() {
        try {
            const response = await fetch("http://localhost:8080/volunteer-history");
            const data = await response.json();
            console.log("Fetched history from backend:", data);
            volunteerHistory = data;
            loadVolunteerHistory();
        } catch (error) {
            console.error("Failed to fetch volunteer history:", error);
        }
    }

    function loadVolunteerHistory() {
        displayFilteredData(volunteerHistory);
    }

    function sortTable(columnIndex) {
        sortOrder[columnIndex] = !sortOrder[columnIndex];
        let isAscending = sortOrder[columnIndex];
    
        const columnFields = [
            'eventName',
            'participationDate',
            'name',
            'hoursVolunteered',
            'description',
            'location',
            'skills',
            'urgency',
            'status'
        ];
    
        const fieldName = columnFields[columnIndex];
    
        volunteerHistory.sort((a, b) => {
            let valA = a[fieldName];
            let valB = b[fieldName];
    
            if (!isNaN(valA) && !isNaN(valB)) {
                return isAscending ? valA - valB : valB - valA;
            } else {
                return isAscending ? String(valA).localeCompare(String(valB)) : String(valB).localeCompare(String(valA));
            }
        });
    
        updateSortIcons(columnIndex, isAscending);
        loadVolunteerHistory();
    }    

    function updateSortIcons(columnIndex, isAscending) {
        for (let i = 0; i < 10; i++) {
            let icon = document.getElementById(`icon${i}`);
            if (icon) icon.innerHTML = "🔽";
        }
        let activeIcon = document.getElementById(`icon${columnIndex}`);
        if (activeIcon) activeIcon.innerHTML = isAscending ? "🔼" : "🔽";
    }

    function displayFilteredData(data) {
        const tableBody = document.getElementById("historyTableBody");
        tableBody.innerHTML = "";
        data.forEach(record => {
            const row = `<tr>
                <td>${record.eventName}</td>
                <td>${record.participationDate ? new Date(record.participationDate).toLocaleDateString() : ""}</td>
                <td>${record.name}</td>
                <td>${record.hoursVolunteered}</td>
                <td>${record.description}</td>
                <td>${record.location}</td>
                <td>${record.skills ? record.skills.split(',').join(", ") : ""}</td>
                <td>${record.urgency}</td>
                <td>${record.status}</td>
            </tr>`;
            tableBody.innerHTML += row;
        });
    }

    function filterHistory() {
        let searchText = searchInput.value.toLowerCase();
        let filteredData = volunteerHistory.filter(record =>
            record.eventName?.toLowerCase().includes(searchText) ||
            record.name?.toLowerCase().includes(searchText) ||
            record.description?.toLowerCase().includes(searchText) ||
            record.location?.toLowerCase().includes(searchText) ||
            record.skills?.toLowerCase().includes(searchText) ||
            String(record.urgency).toLowerCase().includes(searchText) ||
            record.participationStatus?.toLowerCase().includes(searchText) ||
            record.status?.toLowerCase().includes(searchText)
        );
        displayFilteredData(filteredData);
    }

    function filterByStatus() {
        let selectedStatus = statusFilter.value.toLowerCase();
    
        let filteredData = selectedStatus
            ? volunteerHistory.filter(record =>
                record.status && record.status.toLowerCase() === selectedStatus
              )
            : volunteerHistory;
    
        displayFilteredData(filteredData);
    }

    // Event Listeners
    document.querySelectorAll("th").forEach((th, index) => {
        th.removeEventListener("click", () => sortTable(index));
        th.addEventListener("click", () => sortTable(index));
    });

    let searchInput = document.getElementById("searchInput");
    if (searchInput) {
        searchInput.removeEventListener("keyup", filterHistory);
        searchInput.addEventListener("keyup", filterHistory);
    }

    let statusFilter = document.getElementById("statusFilter");
    if (statusFilter) {
        statusFilter.removeEventListener("change", filterByStatus);
        statusFilter.addEventListener("change", filterByStatus);
    }

    fetchVolunteerHistoryFromBackend();
}

document.addEventListener("DOMContentLoaded", () => {
    if (document.getElementById("historyTableBody")) {
        initializeHistory();
    }
});
