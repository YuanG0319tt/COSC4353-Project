document.addEventListener("DOMContentLoaded", function () {
    const profileForm = document.getElementById("profile");

    // Load saved user profile data (if available)
    function loadProfileData() {
        const profileData = JSON.parse(localStorage.getItem("profileData"));
        if (profileData) {
            document.getElementById("fullName").value = profileData.fullName || "";
            document.getElementById("address1").value = profileData.address1 || "";
            document.getElementById("address2").value = profileData.address2 || "";
            document.getElementById("city").value = profileData.city || "";
            document.getElementById("state").value = profileData.state || "";
            document.getElementById("zip").value = profileData.zip || "";
            document.getElementById("preferences").value = profileData.preferences || "";

            // Set skills
            if (profileData.skills) {
                const skillsSelect = document.getElementById("skills");
                Array.from(skillsSelect.options).forEach(option => {
                    if (profileData.skills.includes(option.value)) {
                        option.selected = true;
                    }
                });
            }

            // Load availability dates
            if (profileData.availability) {
                profileData.availability.forEach(date => addAvailabilityDate(date));
            }
        }
    }

    // Save form data on submission
    profileForm.addEventListener("submit", function (event) {
        event.preventDefault();

        const profileData = {
            fullName: document.getElementById("fullName").value,
            address1: document.getElementById("address1").value,
            address2: document.getElementById("address2").value,
            city: document.getElementById("city").value,
            state: document.getElementById("state").value,
            zip: document.getElementById("zip").value,
            skills: Array.from(document.getElementById("skills").selectedOptions).map(opt => opt.value),
            preferences: document.getElementById("preferences").value,
            availability: Array.from(document.querySelectorAll(".availability-input")).map(input => input.value)
        };

        // Store data locally (for now)
        localStorage.setItem("profileData", JSON.stringify(profileData));

        alert("Profile saved successfully!");
    });

    // Add another availability date
    document.getElementById("addDate").addEventListener("click", function () {
        addAvailabilityDate();
    });

    // Function to add a new availability date field
    function addAvailabilityDate(value = "") {
        const container = document.getElementById("availabilityContainer");
        const newDateGroup = document.createElement("div");
        newDateGroup.classList.add("input-group", "mb-2", "availability-date");

        newDateGroup.innerHTML = `
            <input type="date" class="form-control availability-input" value="${value}" required>
            <div class="input-group-append">
                <button class="btn btn-danger remove-date" type="button">Remove</button>
            </div>
        `;

        container.appendChild(newDateGroup);

        // Add event listener to remove button
        newDateGroup.querySelector(".remove-date").addEventListener("click", function () {
            newDateGroup.remove();
        });
    }

    // Load profile data when the page is loaded
    loadProfileData();
});
