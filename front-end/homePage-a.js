function loadContent(_tabId, htmlFile, jsFile, containerId) {
    fetch(htmlFile)
        .then(response => response.text())
        .then(html => {
            document.getElementById(containerId).innerHTML = html;

            // Remove any previously loaded script to prevent duplicates
            let oldScript = document.getElementById("dynamic-script");
            if (oldScript) {
                oldScript.remove();
            }

            // Dynamically load the corresponding JavaScript file
            if (jsFile) {
                const script = document.createElement('script');
                script.src = jsFile;
                script.id = "dynamic-script";
                script.defer = true;
                document.body.appendChild(script);
            }
        })
        .catch(error => console.error(`Error loading ${htmlFile}:`, error));
}

function showTab(tabId) {
    switch (tabId) {
        case 'event':
            loadContent('event', 'events.html', 'events.js', 'event-management-container');
            break;
        case 'profile':
            loadContent('profile', 'profile.html', 'profile.js', 'profile');
            break;
        case 'matching':
            loadContent('matching', 'matching.html', 'matching.js', 'matching-form');
            break;
    }

    // Show the tab
    $('.nav-tabs a[href="#' + tabId + '"]').tab('show');
}

$(document).ready(function () {
    // When a tab is clicked, update the URL
    $('.nav-link').on('click', function (e) {
        e.preventDefault();

        let tabId = $(this).attr('href').substring(1); // Get the tab ID (without #)
        history.pushState(null, null, `#${tabId}`); // Update the URL

        // Load dynamic content for the tab
        showTab(tabId);
    });

    // On page load, check the URL for a specific tab and open it
    let hash = window.location.hash;
    if (hash) {
        showTab(hash.substring(1)); // Remove the # from the hash
    } else {
        // Load default tab if no hash is present
        let defaultTabId = 'profile'; // Set your default tab ID here
        showTab(defaultTabId);
    }

    // Handle back/forward buttons
    window.addEventListener('popstate', function () {
        let hash = window.location.hash;
        if (hash) {
            showTab(hash.substring(1)); // Remove the # from the hash
        } else {
            // Load default tab if no hash is present
            let defaultTabId = 'profile'; // Set your default tab ID here
            showTab(defaultTabId);
        }
    });
});
