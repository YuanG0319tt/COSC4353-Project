function loadContent(tabId, htmlFile, jsFile, containerId) {
    console.log(`Loading HTML: ${htmlFile} and JS: ${jsFile} for container: ${containerId}`);

    fetch(htmlFile)
        .then(response => response.text())
        .then(html => {
            document.getElementById(containerId).innerHTML = html;

            let oldScript = document.getElementById("dynamic-script");
            if (oldScript) {
                oldScript.remove();
            }

            if (jsFile) {
                const script = document.createElement('script');
                script.src = jsFile + '?v=' + new Date().getTime();
                script.id = "dynamic-script";
                script.defer = true;
                script.onload = function () {
                    console.log(`${jsFile} loaded successfully`);
                    
                    // Call the initialize function after the script is loaded
                    if (tabId === 'matching' && typeof initializeMatching === "function") {
                        initializeMatching();
                    }

                    if (tabId === 'history' && typeof initializeHistory === "function") {
                        initializeHistory();
                    }
                };
                document.body.appendChild(script);
            }
        })
        .catch(error => console.error(`Error loading ${htmlFile}:`, error));
}

function showTab(tabId) {
    switch (tabId) {
        case 'event':
            loadContent('event', 'event.html', 'event.js', 'event-management-container');
            break;
        case 'profile':
            loadContent('profile', 'profile.html', 'profile.js', 'profile');
            break;
        case 'matching':
            loadContent('matching', 'matching.html', 'matching.js', 'matching-form');
            break;
        case 'history':
            loadContent('history', 'volHistory.html', 'volHistory.js', 'history');
            break;
        case 'notification':
            loadContent('notification', 'notifications.html', 'notifications.js', 'notification');
            break;
        default:
            console.error(`Invalid tab ID: ${tabId}`);
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
        let defaultTabId = 'event'; // Set your default tab ID here
        showTab(defaultTabId);
    }

    // Handle back/forward buttons
    window.addEventListener('popstate', function () {
        let hash = window.location.hash;
        if (hash) {
            showTab(hash.substring(1)); // Remove the # from the hash
        } else {
            // Load default tab if no hash is present
            let defaultTabId = 'event'; // Set your default tab ID here
            showTab(defaultTabId);
        }
    });
});

function showTab(tabId) {
    switch (tabId) {
        case 'event':
            loadContent('event', 'event.html', 'event.js', 'event-management-container');
            break;
        case 'profile':
            loadContent('profile', 'profile.html', 'profile.js', 'profile');
            break;
        case 'matching':
            loadContent('matching', 'matching.html', 'matching.js', 'matching-form');
            break;
        case 'history':
            loadContent('history', 'volHistory.html', 'volHistory.js', 'history');
            break;
        case 'notification':
            loadContent('notification', 'notifications.html', 'notifications.js', 'notification');
            break;
        default:
            console.error(`Invalid tab ID: ${tabId}`);
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
        let defaultTabId = 'event'; // Set your default tab ID here
        showTab(defaultTabId);
    }

    // Handle back/forward buttons
    window.addEventListener('popstate', function () {
        let hash = window.location.hash;
        if (hash) {
            showTab(hash.substring(1)); // Remove the # from the hash
        } else {
            // Load default tab if no hash is present
            let defaultTabId = 'event'; // Set your default tab ID here
            showTab(defaultTabId);
        }
    });
});
