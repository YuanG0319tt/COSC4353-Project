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

    // JS for handling filter state
    const appliedFiltersContainer = document.getElementById('appliedFiltersContainer');
    const resetFiltersLink = document.getElementById('resetFilters');
    const saveFiltersButton = document.getElementById('saveFilters');
    const clearAllFiltersButton = document.getElementById('clearAllFilters');

    // Track selected filters
    // Example data structure: { location: ['New York', 'Chicago'], availability: ['Weekdays'] }
    let selectedFilters = {
      location: [],
      availability: []
    };

    // 1. Update filter chips display
    function renderFilterChips() {
      appliedFiltersContainer.innerHTML = '';

      // For each category in selectedFilters
      for (const category in selectedFilters) {
        selectedFilters[category].forEach(value => {
          const chip = document.createElement('span');
          chip.className = 'filter-chip';
          chip.textContent = `${category}: ${value} `;

          // Close (x) button for each chip
          const closeBtn = document.createElement('button');
          closeBtn.type = 'button';
          closeBtn.className = 'btn-close btn-close-white btn-sm ms-1';
          closeBtn.style.verticalAlign = 'middle';
          closeBtn.addEventListener('click', () => {
            removeFilter(category, value);
          });

          chip.appendChild(closeBtn);
          appliedFiltersContainer.appendChild(chip);
        });
      }
    }

    // 2. Remove a filter from selectedFilters
    function removeFilter(category, value) {
      selectedFilters[category] = selectedFilters[category].filter(v => v !== value);
      // Also uncheck the corresponding checkbox/radio
      const input = document.querySelector(`input[value="${value}"]`);
      if (input) input.checked = false;
      renderFilterChips();
    }

    // 3. Clear all filters
    function clearAll() {
      selectedFilters = {
        location: [],
        availability: []
      };
      document.querySelectorAll('input[type="checkbox"], input[type="radio"]').forEach(input => {
        input.checked = false;
      });
      renderFilterChips();
    }

    // Listen for changes on checkboxes (Location)
    document.querySelectorAll('input[type="checkbox"]').forEach(checkbox => {
      checkbox.addEventListener('change', function() {
        const category = 'location';
        const value = this.value;

        if (this.checked) {
          // Add to selectedFilters if not already present
          if (!selectedFilters[category].includes(value)) {
            selectedFilters[category].push(value);
          }
        } else {
          // Remove from selectedFilters
          removeFilter(category, value);
        }
        renderFilterChips();
      });
    });

    // Listen for changes on radio buttons (Availability)
    document.querySelectorAll('input[name="availability"]').forEach(radio => {
      radio.addEventListener('change', function() {
        const category = 'availability';
        const value = this.value;

        // Overwrite the availability array since it's single-select
        selectedFilters[category] = [value];

        // Uncheck other radios in that category
        document.querySelectorAll('input[name="availability"]').forEach(otherRadio => {
          if (otherRadio.value !== value) {
            otherRadio.checked = false;
          }
        });
        renderFilterChips();
      });
    });

    // "Reset Filters" link - removes all filters, but you might define it differently
    resetFiltersLink.addEventListener('click', (e) => {
      e.preventDefault();
      clearAll();
    });

    // "Clear All" button
    clearAllFiltersButton.addEventListener('click', () => {
      clearAll();
    });

    // "Save" button
    saveFiltersButton.addEventListener('click', () => {
      // Here you’d typically fetch new volunteer matches from your backend
      // using the filters in 'selectedFilters'
      alert('Filters saved! Volunteers list will be updated.');

      // Then close the offcanvas
      const offcanvasEl = document.getElementById('filterOffcanvas');
      const offcanvas = bootstrap.Offcanvas.getInstance(offcanvasEl);
      offcanvas.hide();
    });
}

// Ensure the function is called when `matching.html` loads dynamically
document.addEventListener("DOMContentLoaded", initializeMatching);
