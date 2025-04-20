function initializeMatching() {
  console.log("Initializing Matching...");

  const volunteerDropdown = document.getElementById("matchingName");
  const eventDropdown = document.getElementById("matchingEvent");
  const volunteerSkills = document.getElementById("volunteerSkills");
  const volunteerPreferences = document.getElementById("volunteerPreferences");
  const form = document.getElementById("volunteer-matching-form");

  // Reset match table and suggestion section
  const matchTableBody = document.getElementById("top-match-table")?.querySelector("tbody");
  if (matchTableBody) matchTableBody.innerHTML = "";
  const suggestions = document.getElementById("match-suggestions");
  if (suggestions) suggestions.innerHTML = "";

  // Fetch and populate volunteers
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

  // Fetch and populate events
  fetch("/events")
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

  // Handle dropdown change to show volunteer info
  volunteerDropdown.addEventListener("change", () => {
    const selected = volunteerDropdown.value;
    if (!selected) return;

    fetch("/volunteers")
      .then(res => res.json())
      .then(data => {
        const volunteer = data.find(v => v.name === selected);
        if (volunteer) {
          volunteerSkills.textContent = "Skills: " + (volunteer.skills || []).join(", ");
          volunteerPreferences.textContent = "Preferences: " + (volunteer.preferences || []).join(", ");
        }
      });
  });

  // Form submission: assign volunteer
  if (form) {
    form.addEventListener("submit", function (e) {
      e.preventDefault();
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
        .then(res => res.json())
        .then(data => {
          alert("Match Successful: " + data.message);
        })
        .catch(err => console.error("Match failed", err));
    }, { once: true }); // ✅ ensure it only runs once per load
  }

  // Match button listeners — assigned only once
  document.getElementById("showEventMatches")?.addEventListener("click", showMatchesForVolunteer);
  document.getElementById("showVolunteerMatches")?.addEventListener("click", showMatchesForEvent);

  // Render match results in table
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

  // Show top matching events for selected volunteer
  function showMatchesForVolunteer() {
    const name = volunteerDropdown.value;
    if (!name) return;

    fetch(`/match/top-events?volunteer=${encodeURIComponent(name)}`)
      .then(res => res.json())
      .then(data => renderMatchTable(data))
      .catch(err => console.error("Match error", err));
  }

  // Show top matching volunteers for selected event
  function showMatchesForEvent() {
    const name = eventDropdown.value;
    if (!name) return;

    fetch(`/match/top-volunteers?event=${encodeURIComponent(name)}`)
      .then(res => res.json())
      .then(data => renderMatchTable(data))
      .catch(err => console.error("Match error", err));
  }
}
