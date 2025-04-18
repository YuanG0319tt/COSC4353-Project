// matching.js

// Main initialization function
function initializeMatching() {
  console.log("Matching system initialized");
  
  // Fetch data when page loads
  fetchVolunteers();
  fetchEvents();
  
  // Set up event listeners
  setupEventListeners();
}

// Fetch volunteers from API
function fetchVolunteers() {
  console.log("Fetching volunteers from API...");
  fetch("http://localhost:8080/api/list")
      .then(response => {
          if (!response.ok) {
              throw new Error(`HTTP error! Status: ${response.status}`);
          }
          return response.json();
      })
      .then(data => {
          console.log("API Response:", data);
          const volunteerDropdown = document.getElementById("matchingName");
          volunteerDropdown.innerHTML = '<option value="">Select a Volunteer</option>';
          
          if (data && data.data && Array.isArray(data.data)) {
              data.data.forEach(volunteer => {
                  const option = document.createElement("option");
                  option.value = volunteer.id;
                  option.textContent = volunteer.email;
                  volunteerDropdown.appendChild(option);
              });
          } else {
              console.error("Unexpected API response format:", data);
          }
      })
      .catch(error => {
          console.error("Error fetching volunteers:", error);
          alert("Failed to load volunteers. Check console for details.");
      });
}

// Fetch events (mock data - replace with your actual API call)
function fetchEvents() {
  console.log("Fetching events...");
  // This is mock data - replace with your actual API call
  fetch("http://localhost:8080/api/events/list")
  .then(response => {
      if (!response.ok) {
          throw new Error(`HTTP error! Status: ${response.status}`);
      }
      return response.json();
  })
  .then(data => {
      console.log("API Response:", data);
      const eventDropdown = document.getElementById("matchingEvent");
    eventDropdown.innerHTML = '<option value="">Select an Event</option>';
      
      if (data && data.data && Array.isArray(data.data)) {
          data.data.forEach(eve => {
              const option = document.createElement("option");
              option.value = eve.id;
              option.textContent = eve.name;
              eventDropdown.appendChild(option);
          });
      } else {
          console.error("Unexpected API response format:", data);
      }
  })
  .catch(error => {
      console.error("Error fetching volunteers:", error);
      alert("Failed to load volunteers. Check console for details.");
  });
}

// Set up all event listeners
function setupEventListeners() {
  // Volunteer selection change
  document.getElementById("matchingName").addEventListener("change", function() {
      const selectedEmail = this.value;
      if (!selectedEmail) return;

      fetch("http://localhost:8080/api/list")
          .then(response => response.json())
          .then(data => {
              const volunteer = data.data.find(v => v.email === selectedEmail);
              if (volunteer) {
                  document.getElementById("volunteerSkills").textContent = `Email: ${volunteer.email}`;
                  document.getElementById("volunteerPreferences").textContent = `ID: ${volunteer.id}`;
              }
          })
          .catch(error => console.error("Error fetching volunteer details:", error));
  });
  // Form submission
  document.getElementById("volunteer-matching-form").addEventListener("submit", function(event) {
    event.preventDefault();

    const submitButton = document.querySelector("#volunteer-matching-form button[type='submit']");
    if (submitButton.disabled) return; // 如果按钮已禁用，则直接返回

    const userId = document.getElementById("matchingName").value;
    const eventId = document.getElementById("matchingEvent").value;
    const participationDate = document.getElementById("new-event-date").value;

    if (!userId || !eventId) {
        alert("Please select a volunteer and an event.");
        return;
    }
    if (!participationDate) {
        alert("Please select a event date.");
        return;
    }

    // 禁用提交按钮
    submitButton.disabled = true;

    // 模拟 API 调用 - 替换为实际实现
    fetch("http://localhost:8080/api/volunteerHistory/add", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ userId, eventId,participationDate})
    })
    .then(response => response.json())
    .then(data => {
        alert("Match Successful !");
    })
    .catch(error => {
        console.error("Error assigning volunteer:", error);
        alert("An error occurred. Please try again.");
    })
    .finally(() => {
        // 重新启用提交按钮，无论成功还是失败
        submitButton.disabled = false;
    });
});
}

// Initialize when DOM is fully loaded
document.addEventListener("DOMContentLoaded", initializeMatching);