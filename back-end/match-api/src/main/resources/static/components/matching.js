document.addEventListener("DOMContentLoaded", function () {
  fetchVolunteers();
  fetchEvents();
  fetchMatches();
});

function fetchVolunteers() {
    fetch("/volunteers")
      .then(res => res.json())
      .then(data => {
        const dropdown = document.getElementById("matchingName");
        dropdown.innerHTML = `<option value="">Select a Volunteer</option>`;
        data.forEach(v => {
          let opt = document.createElement("option");
          opt.value = v.name;
          opt.textContent = v.name;
          dropdown.appendChild(opt);
        });
      });
  }  

  function fetchEvents() {
    fetch("/events")
      .then(res => res.json())
      .then(data => {
        const dropdown = document.getElementById("matchingEvent");
        dropdown.innerHTML = `<option value="">Select an Event</option>`;
  
        data.forEach(e => {
          let opt = document.createElement("option");
          opt.value = e.eventName;
          opt.textContent = `${e.eventName} (Skills: ${e.requiredSkills})`;
          dropdown.appendChild(opt);
        });
  
        dropdown.addEventListener("change", function () {
          const selectedEvent = this.value;
          if (selectedEvent) {
            fetchTopVolunteersForEvent(selectedEvent);
          }
        });
      });
  }
  
  function fetchTopVolunteersForEvent(name) {
    fetch(`/match/top-volunteers?event=${encodeURIComponent(name)}`)
      .then(res => res.json())
      .then(data => {
        const container = document.getElementById("match-suggestions");
        container.innerHTML = "<strong>Top Volunteer Matches:</strong><ul>" +
          data.map(match => `<li>${match.volunteerName} (Score: ${match.score})</li>`).join("") +
          "</ul>";
      })
      .catch(err => console.error("Failed to fetch top volunteer matches:", err));
  }
  

function fetchTopEventsForVolunteer(name) {
    fetch(`/match/top-events?volunteer=${encodeURIComponent(name)}`)
      .then(res => res.json())
      .then(data => {
        const container = document.getElementById("match-suggestions");
        container.innerHTML = "<strong>Top Event Matches:</strong><ul>" +
          data.map(match => `<li>${match.eventName} (Score: ${match.score})</li>`).join("") +
          "</ul>";
      })
      .catch(err => console.error("Failed to fetch top event matches:", err));
  }  

function fetchMatches() {
  fetch("/match")
      .then(res => res.json())
      .then(data => {
          const table = document.getElementById("match-table");
          if (!table) return;

          const tbody = table.querySelector("tbody");
          tbody.innerHTML = "";
          data.forEach(match => {
              const row = document.createElement("tr");
              row.innerHTML = `
                  <td>${match.volunteerName}</td>
                  <td>${match.eventName}</td>
              `;
              tbody.appendChild(row);
          });
      });
}

function showVolunteerInfo() {
  const selected = document.getElementById("matchingName").value;
  if (!selected) return;

  fetch("/volunteers")
      .then(res => res.json())
      .then(data => {
          const volunteer = data.find(v => v.name === selected);
          if (volunteer) {
              document.getElementById("volunteerSkills").textContent = "Skills: " + volunteer.skills;
              document.getElementById("volunteerPreferences").textContent = "Preferences: " + volunteer.preferences;
          }
      });
}

function initializeMatching() {
    console.log("Initializing Matching...");
  
    const volunteerDropdown = document.getElementById("matchingName");
    const eventDropdown = document.getElementById("matchingEvent");
    const volunteerSkills = document.getElementById("volunteerSkills");
    const volunteerPreferences = document.getElementById("volunteerPreferences");
  
    function fetchVolunteers() {
      fetch("/volunteers")
        .then(res => res.json())
        .then(data => {
          volunteerDropdown.innerHTML = `<option value="">Select a Volunteer</option>`;
          data.forEach(v => {
            const opt = document.createElement("option");
            opt.value = v.name;
            opt.textContent = v.name;
            volunteerDropdown.appendChild(opt);
          });
        })
        .catch(err => console.error("Error fetching volunteers:", err));
    }
  
    function fetchEvents() {
      fetch("/events")
        .then(res => res.json())
        .then(data => {
          eventDropdown.innerHTML = `<option value="">Select an Event</option>`;
          data.forEach(e => {
            const opt = document.createElement("option");
            opt.value = e.eventName || e.name; // fallback if eventName isn't used
            opt.textContent = `${opt.value} (Skills: ${e.requiredSkills || e.requirements?.join(", ")})`;
            eventDropdown.appendChild(opt);
          });
        })
        .catch(err => console.error("Error fetching events:", err));
    }
  
    function showVolunteerInfo() {
      const selected = volunteerDropdown.value;
      if (!selected) return;
  
      fetch("/volunteers")
        .then(res => res.json())
        .then(data => {
          const volunteer = data.find(v => v.name === selected);
          if (volunteer) {
            volunteerSkills.textContent = "Skills: " + volunteer.skills.join(", ");
            volunteerPreferences.textContent = "Preferences: " + volunteer.preferences.join(", ");
          }
        });
    }
  
    function showMatchesForVolunteer() {
      const name = volunteerDropdown.value;
      if (!name) return;
  
      fetch(`/match/top-events?volunteer=${encodeURIComponent(name)}`)
        .then(res => res.json())
        .then(data => renderMatchTable(data))
        .catch(err => console.error("Match error", err));
    }
  
    function showMatchesForEvent() {
      const name = eventDropdown.value;
      if (!name) return;
  
      fetch(`/match/top-volunteers?event=${encodeURIComponent(name)}`)
        .then(res => res.json())
        .then(data => renderMatchTable(data))
        .catch(err => console.error("Match error", err));
    }
  
    function renderMatchTable(data) {
      const tbody = document.getElementById("top-match-table").querySelector("tbody");
      tbody.innerHTML = "";
  
      data.forEach(match => {
        const volunteer = match.volunteerName || "-";
        const event = match.eventName || "-";
        const score = match.score ? ` (Score: ${match.score})` : "";
  
        const row = document.createElement("tr");
        row.innerHTML = `<td>${volunteer}</td><td>${event}${score}</td>`;
        tbody.appendChild(row);
      });
    }
  
    // Form Submission
    const form = document.getElementById("volunteer-matching-form");
    if (form) {
      form.addEventListener("submit", function (event) {
        event.preventDefault();
  
        const volunteerName = volunteerDropdown.value;
        const eventName = eventDropdown.value;
  
        if (!volunteerName || !eventName) {
          alert("Please select a volunteer and an event.");
          return;
        }
  
        fetch("/match", {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify({ volunteerName, eventName }),
        })
          .then(response => response.json())
          .then(data => alert("Match Successful: " + data.message))
          .catch(error => console.error("Error assigning volunteer:", error));
      });
    }
  
    // Hook up dropdown change
    if (volunteerDropdown) {
      volunteerDropdown.addEventListener("change", showVolunteerInfo);
    }
  
    // Hook up match buttons
    const matchEventBtn = document.querySelector('button[onclick="showMatchesForEvent()"]');
    const matchVolunteerBtn = document.querySelector('button[onclick="showMatchesForVolunteer()"]');
  
    if (matchEventBtn) matchEventBtn.addEventListener("click", showMatchesForEvent);
    if (matchVolunteerBtn) matchVolunteerBtn.addEventListener("click", showMatchesForVolunteer);
  
    // Initial data load
    fetchVolunteers();
    fetchEvents();
  }  

document.getElementById("volunteer-matching-form").addEventListener("submit", function (e) {
  e.preventDefault();
  const volunteerName = document.getElementById("matchingName").value;
  const eventName = document.getElementById("matchingEvent").value;

  fetch("/match", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ volunteerName, eventName })
  })
      .then(res => res.json())
      .then(data => alert("Match Successful: " + data.message))
      .catch(err => console.error("Match failed", err));
});
