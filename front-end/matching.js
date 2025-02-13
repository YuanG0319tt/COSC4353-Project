document.addEventListener("DOMContentLoaded", function () {
    const volunteerSelect = document.getElementById("matchingName");
    const eventSelect = document.getElementById("matchingEvent");
    const skillsDisplay = document.getElementById("volunteerSkills");
    const preferencesDisplay = document.getElementById("volunteerPreferences");

    // Placeholder volunteer data (replace with database fetch)
    const volunteers = {
        johnDoe: { skills: ["deploy", "code"], preferences: "Night shifts" },
        janeDoe: { skills: ["teaching", "first aid"], preferences: "Weekends only" }
    };

    // Placeholder event data (replace with database fetch)
    const events = {
        deploy: ["IT Support", "Tech Deployment"],
        code: ["Hackathon Assistance", "Software Dev Training"],
        teaching: ["Community Tutoring", "Language Classes"],
        firstAid: ["Medical Camps", "Emergency Response"]
    };

    // Display selected volunteer’s details
    volunteerSelect.addEventListener("change", function () {
        const selectedVolunteer = volunteers[this.value];

        if (selectedVolunteer) {
            skillsDisplay.innerHTML = `Skills: ${selectedVolunteer.skills.join(", ")}`;
            preferencesDisplay.innerHTML = `Preferences: ${selectedVolunteer.preferences}`;

            // Autofill event dropdown based on volunteer skills
            updateEventOptions(selectedVolunteer.skills);
        } else {
            skillsDisplay.innerHTML = "";
            preferencesDisplay.innerHTML = "";
            eventSelect.innerHTML = `<option value="">Select an Event</option>`;
        }
    });

    // Update event dropdown based on volunteer skills
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
            option.value = event.toLowerCase().replace(/\s+/g, ""); // Convert name to value format
            option.textContent = event;
            eventSelect.appendChild(option);
        });
    }

    // Handle form submission
    document.getElementById("volunteer-matching-form").addEventListener("submit", function (event) {
        event.preventDefault();
        
        const selectedVolunteer = volunteerSelect.value;
        const selectedEvent = eventSelect.value;

        if (!selectedVolunteer || !selectedEvent) {
            alert("Please select both a volunteer and an event.");
            return;
        }

        // Normally, this would be sent to a backend server
        alert(`Volunteer assigned to event: ${selectedEvent}`);

        // Example of sending data to a backend (if implemented)
        /*
        fetch('/assign-volunteer', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ volunteer: selectedVolunteer, event: selectedEvent })
        }).then(response => response.json())
          .then(data => console.log(data))
          .catch(error => console.error('Error:', error));
        */
    });
});
