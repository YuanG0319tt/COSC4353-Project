function initializeMatching() {
  console.log("Initializing Matching...");

  const BASE_URL = "http://localhost:8080";

  // Remove any previous listeners by replacing the form
  const oldForm = document.getElementById("volunteer-matching-form");
  if (!oldForm) return;

  const clonedForm = oldForm.cloneNode(true); // clone without listeners
  oldForm.parentNode.replaceChild(clonedForm, oldForm);

  // Re-select everything from the new DOM
  const form = document.getElementById("volunteer-matching-form");
  const volunteerDropdown = document.getElementById("matchingName");
  const eventDropdown = document.getElementById("matchingEvent");
  const volunteerSkills = document.getElementById("volunteerSkills");
  const volunteerPreferences = document.getElementById("volunteerPreferences");

  const matchTableBody = document.getElementById("top-match-table")?.querySelector("tbody");
  if (matchTableBody) matchTableBody.innerHTML = "";

  const suggestions = document.getElementById("match-suggestions");
  if (suggestions) suggestions.innerHTML = "";

  // Fetch and populate volunteers
  fetch(`${BASE_URL}/volunteers`)
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

  // Fetch and populate events
  fetch(`${BASE_URL}/events`)
    .then(res => res.json())
    .then(data => {
      eventDropdown.innerHTML = `<option value="">Select an Event</option>`;
      data.forEach(e => {
        const opt = document.createElement("option");
        opt.value = e.eventName || e.name;
        opt.textContent = `${opt.value} (Skills: ${e.requiredSkills || e.requirements?.join(", ")})`;
        eventDropdown.appendChild(opt);
      });
    })
    .catch(err => console.error("Error fetching events:", err));

  // Update volunteer info on dropdown change
  volunteerDropdown.addEventListener("change", () => {
    const selected = volunteerDropdown.value;
    if (!selected) return;

    fetch(`${BASE_URL}/volunteers`)
      .then(res => res.json())
      .then(data => {
        const volunteer = data.find(v => v.name === selected);
        if (volunteer) {
          volunteerSkills.textContent = "Skills: " + (volunteer.skills || []).join(", ");
          volunteerPreferences.textContent = "Preferences: " + (volunteer.preferences || []).join(", ");
        }
      });
  });

  // Add submit handler
  form.addEventListener("submit", function (e) {
    e.preventDefault();
    console.log("Submit triggered");

    const volunteerName = volunteerDropdown.value;
    const eventName = eventDropdown.value;

    if (!volunteerName || !eventName) {
      alert("Please select a volunteer and an event.");
      return;
    }

    fetch(`${BASE_URL}/match`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ volunteerName, eventName }),
    })
      .then(res => res.json())
      .then(data => {
        alert("Match Successful: " + data.message);
      })
      .catch(err => console.error("Match failed", err));
  });

  // Match buttons
  document.getElementById("showEventMatches")?.addEventListener("click", showMatchesForVolunteer);
  document.getElementById("showVolunteerMatches")?.addEventListener("click", showMatchesForEvent);

  // Render match table
  function renderMatchTable(data) {
    const tbody = document.getElementById("top-match-table")?.querySelector("tbody");
    if (!tbody) return;
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

  function showMatchesForVolunteer() {
    const name = volunteerDropdown.value;
    if (!name) return;

    fetch(`${BASE_URL}/match/top-events?volunteer=${encodeURIComponent(name)}`)
      .then(res => res.json())
      .then(data => renderMatchTable(data))
      .catch(err => console.error("Match error", err));
  }

  function showMatchesForEvent() {
    const name = eventDropdown.value;
    if (!name) return;

    fetch(`${BASE_URL}/match/top-volunteers?event=${encodeURIComponent(name)}`)
      .then(res => res.json())
      .then(data => renderMatchTable(data))
      .catch(err => console.error("Match error", err));
  }
}
