(function() {
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
          document.getElementById(`icon${i}`).innerHTML = "🔽"; // Reset all to down arrow
      }
      document.getElementById(`icon${columnIndex}`).innerHTML = isAscending ? "🔼" : "🔽";
  }

  document.getElementById("searchInput").addEventListener("keyup", function() {
      let searchText = this.value.toLowerCase();
      let filteredData = volunteerHistory.filter(record =>
          record.event.toLowerCase().includes(searchText) ||
          record.role.toLowerCase().includes(searchText)
      );
      displayFilteredData(filteredData);
  });

  document.getElementById("statusFilter").addEventListener("change", function() {
      let status = this.value;
      let filteredData = status ? volunteerHistory.filter(record => record.status === status) : volunteerHistory;
      displayFilteredData(filteredData);
  });

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

  document.addEventListener("DOMContentLoaded", function() {
      loadVolunteerHistory();

      // Attach sortTable function to each column header
      document.querySelectorAll("th").forEach((th, index) => {
          th.addEventListener("click", () => sortTable(index));
      });
  });
})();
