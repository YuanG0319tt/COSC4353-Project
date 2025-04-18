function initializeHistory() {
    console.log("Initializing Volunteer History...");

    getLsit();

    let volunteerHistory = [
    
    ];

    let sortOrder = {}; // Track sort order for each column

    function loadVolunteerHistory() {
        const role = localStorage.getItem("role");
            if(role == 'admin'){
                loadAdminData();
            }else{
                loadUserData();
            }
        
        
    }

    function loadAdminData(){
        const tableBody = document.getElementById("historyTableBody");
        tableBody.innerHTML = "";
    
        volunteerHistory.forEach((record, index) => {
            // Role 和 Hours 作为可编辑的文本框
            const roleInput = `<textarea class="list-input" data-index="${index}" data-field="role">${record.role}</textarea>`;
            const hoursInput = `<textarea class="list-input" data-index="${index}" data-field="hours">${record.hours}</textarea>`;
    
            // Participation Status radio buttons
            const participationStatusRadios = `
                <div class="status-radio">
                    <label><input type="radio" name="participationStatus-${index}" value="Attended" ${record.participationStatus === "Attended" ? "checked" : ""}> Attended</label>
                    <label><input type="radio" name="participationStatus-${index}" value="Not Attend" ${record.participationStatus === "Not Attend" ? "checked" : ""}> Not Attend</label>
                </div>
            `;
    
            // Completion Status radio buttons
            const completionStatusRadios = `
                <div class="status-radio">
                    <label><input type="radio" name="completionStatus-${index}" value="Completed" ${record.completionStatus === "Completed" ? "checked" : ""}> Completed</label>
                    <label><input type="radio" name="completionStatus-${index}" value="Pending" ${record.completionStatus === "Pending" ? "checked" : ""}> Pending</label>
                </div>
            `;
    
            const row = `
                <tr data-id="${record.id}">
                    <td title="${record.eventName}" class="truncate">${record.eventName}</td>
                    <td>${record.participationDate}</td>
                    <td>${roleInput}</td> 
                    <td>${hoursInput}</td>
                    <td>${record.description}</td>
                    <td>${record.location}</td>
                    <td>${record.requiredSkills}</td>
                    <td>${record.urgency}</td>
                    <td>${participationStatusRadios}</td>
                    <td>${completionStatusRadios}</td>
                </tr>
            `;
            tableBody.innerHTML += row;
        });
    
        // 添加事件监听器以处理输入框的更改
        addEditableFieldListeners();
        bindRadioButtons();
    }

    function loadUserData(){
        const tableBody = document.getElementById("historyTableBody");
        tableBody.innerHTML = "";
        volunteerHistory.forEach(record => {
            const row = `<tr>
                        <td title="${record.eventName}" class="truncate">${record.eventName}</td>
                        <td>${record.participationDate}</td>
                        <td>${record.role}</td>
                        <td>${record.hours}</td>
                        <td>${record.description}</td>
                        <td>${record.location}</td>
                        <td>${record.requiredSkills}</td>
                        <td>${record.urgency}</td>
                        <td>${record.participationStatus}</td>
                        <td>${record.completionStatus}</td>
                    </tr>`;
            tableBody.innerHTML += row;
        });
    }
    
    searchButton.addEventListener("click", function() {
        // 调用getLsit函数重新获取数据
        getLsit();
    });

    function getLsit() {
        let uid = 0;
        const juese = localStorage.getItem("role");
        if(juese != 'admin'){
            uid = localStorage.getItem("uid");
        }
        let eventOrRole = '';
        let status = '';
        eventOrRole = document.getElementById("searchInput").value;
        status = document.getElementById("statusFilter").value;

        // in your getLsit()
        $.get("http://localhost:8080/api/volunteerHistory/list", resp => {
            volunteerHistory = resp.data;   // list() returns everything
            loadVolunteerHistory();
        });


        $.get(
            "http://localhost:8080/api/volunteerHistory/search?eventOrRole="
            + encodeURIComponent(eventOrRole)
            + "&status=" + encodeURIComponent(status)
            + "&uid=" + uid,
            function(data) {
                // ← Insert your logs here:
                console.log("🔍 full response:", data);
                // handle both flat-array and paginated responses:
                const items = Array.isArray(data.data)
                    ? data.data
                    : (Array.isArray(data.data?.records)
                        ? data.data.records
                        : []);
                console.log("🔢 parsed volunteerHistory length:", items.length);

                // Now update your state and re‑render
                volunteerHistory = items;
                loadVolunteerHistory();
            }
        )
            .fail(function(error) {
                console.error("Error fetching data:", error);
                volunteerHistory = [];
                loadVolunteerHistory();
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


    // Load initial data
    loadVolunteerHistory();
}

// Ensure `initializeHistory()` is called when `volHistory.html` is dynamically loaded
document.addEventListener("DOMContentLoaded", () => {
    if (document.getElementById("historyTableBody")) {
        initializeHistory();
    }
});

function addEditableFieldListeners() {
    document.querySelectorAll(".list-input").forEach(input => {
        input.addEventListener("blur", function () {
            const row = this.closest("tr");
            const recordId = row.dataset.id;

            // 显示加载状态
            input.disabled = true;
            input.style.backgroundColor = "#f0f0f0";

            const updatedData = {
                id: recordId,
                role: row.querySelector("[data-field='role']").value,
                hours: row.querySelector("[data-field='hours']").value,
                participationStatus: row.querySelector("input[name^='participationStatus']:checked")?.value || "",
                completionStatus: row.querySelector("input[name^='completionStatus']:checked")?.value || ""
            };

            fetch(`http://localhost:8080/api/volunteerHistory/update`, {
                method: "PUT",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(updatedData)
            })
                .then(response => response.json())
                .then(data => {
                    console.log("Update successful:", data);
                    alert("Record updated successfully!");

                    // 恢复输入框状态
                    input.disabled = false;
                    input.style.backgroundColor = "";
                })
                .catch(error => {
                    console.error("Error updating record:", error);
                    alert("Failed to update record. Please try again.");

                    // 恢复输入框状态
                    input.disabled = false;
                    input.style.backgroundColor = "";
                });
        });
    });
}

function bindRadioButtons() {

    document.querySelectorAll(".status-radio input[type='radio']").forEach(radio => {
        radio.addEventListener("change", function () {
            const row = this.closest("tr");
            const recordId = row.dataset.id;


            this.disabled = true;


            const updatedData = {
                id: recordId,
                participationStatus: row.querySelector("input[name^='participationStatus']:checked")?.value || "",
                completionStatus: row.querySelector("input[name^='completionStatus']:checked")?.value || ""
            };


            fetch(`http://localhost:8080/api/volunteerHistory/update`, {
                method: "PUT",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(updatedData)
            })
                .then(response => response.json())
                .then(data => {
                    console.log("Update successful:", data);
                    alert("Record updated successfully!");


                    this.disabled = false;
                })
                .catch(error => {
                    console.error("Error updating record:", error);
                    alert("Failed to update record. Please try again.");


                    this.disabled = false;
                });
        });
    });
}