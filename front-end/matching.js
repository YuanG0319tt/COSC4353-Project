function initializeMatching() {
    console.log("Matching script initialized");

    const volunteerSelect = document.getElementById("matchingName");
    const eventSelect = document.getElementById("matchingEvent");
    const skillsDisplay = document.getElementById("volunteerSkills");
    const preferencesDisplay = document.getElementById("volunteerPreferences");

    const volunteers = {
        johnDoe: { skills: ["deploy", "code"], preferences: "Night shifts" },
        janeDoe: { skills: ["teaching", "first aid"], preferences: "Weekends only" }
    };

    const events = {
        deploy: ["IT Support", "Tech Deployment"],
        code: ["Hackathon Assistance", "Software Dev Training"],
        teaching: ["Community Tutoring", "Language Classes"],
        firstAid: ["Medical Camps", "Emergency Response"]
    };

    // Ensure event listeners are not duplicated
    volunteerSelect.removeEventListener("change", showVolunteerInfo);
    volunteerSelect.addEventListener("change", showVolunteerInfo);

    function showVolunteerInfo() {
        const selectedVolunteer = volunteers[volunteerSelect.value];

        if (selectedVolunteer) {
            skillsDisplay.innerHTML = `Skills: ${selectedVolunteer.skills.join(", ")}`;
            preferencesDisplay.innerHTML = `Preferences: ${selectedVolunteer.preferences}`;
            updateEventOptions(selectedVolunteer.skills);
        } else {
            skillsDisplay.innerHTML = "";
            preferencesDisplay.innerHTML = "";
            eventSelect.innerHTML = `<option value="">Select an Event</option>`;
        }
    }

    function updateEventOptions(skills) {
        eventSelect.innerHTML = `<option value="">Select an Event</option>`;
        let matchedEvents = new Set();

        skills.forEach(skill => {
            if (events[skill]) {
                events[skill].forEach(event => matchedEvents.add(event));
            }
        });

        matchedEvents.forEach(event => {
            let option = document.createElement("option");
            option.value = event.toLowerCase().replace(/\s+/g, "");
            option.textContent = event;
            eventSelect.appendChild(option);
        });
    }

    // Make the function globally accessible if needed
    window.showVolunteerInfo = showVolunteerInfo;
}

// Ensure the function is called when `matching.html` loads dynamically
document.addEventListener("DOMContentLoaded", initializeMatching);
