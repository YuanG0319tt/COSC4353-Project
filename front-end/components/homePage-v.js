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

                    if (tabId === 'history' && typeof initializeHistory === "function") {
                        initializeHistory();
                    }
                };
                document.body.appendChild(script);
            }
        })
        .catch(error => console.error(`Error loading ${htmlFile}:`, error));
}

function loadUserProfile() {
    console.log("Loading user profile...");

    fetch('./pages/profile.html')
      .then(response => response.text())
      .then(html => {
        document.getElementById('profile-content').innerHTML = html;

        let oldScript = document.getElementById("profile-script");
        if (oldScript) {
          oldScript.remove();
        }

        const script = document.createElement('script');
        script.src = './components/profile.js?v=' + new Date().getTime();
        script.id = "profile-script";
        script.defer = true;
        script.onload = function () {
          console.log("Profile.js loaded successfully");
        };
        document.body.appendChild(script);
      })
      .catch(error => console.error("Error loading profile.html:", error));

    // Hide Tabs and Show Profile Section
    document.getElementById('tab-container').style.display = 'none';
    document.getElementById('profile-section').style.display = 'block';
  }

  function showDashboard() {
    document.getElementById('tab-container').style.display = 'block';
    document.getElementById('profile-section').style.display = 'none';
  }

function showTab(tabId) {
    switch (tabId) {
        case 'profile':
            loadContent('profile', './pages/profile.html', './components/profile.js', 'profile');
            break;
        case 'notifications':
            loadContent('notifications', './pages/notifications-v.html', './components/notifications.js', 'notification-container');
            break;
        case 'event':
            loadContent('event', './pages/event-v.html', './components/event.js', 'event-management-container');
            break;
        case 'history':
            loadContent('history', './pages/volHistory.html', './components/volHistory.js', 'history');
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
        let defaultTabId = 'notifications'; // Set your default tab ID here
        showTab(defaultTabId);
    }

    // Handle back/forward buttons
    window.addEventListener('popstate', function () {
        let hash = window.location.hash;
        if (hash) {
            showTab(hash.substring(1)); // Remove the # from the hash
        } else {
            // Load default tab if no hash is present
            let defaultTabId = 'notifications'; // Set your default tab ID here
            showTab(defaultTabId);
        }
    });
});
