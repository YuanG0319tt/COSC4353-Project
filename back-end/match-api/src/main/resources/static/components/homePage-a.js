function loadContent(tabId, htmlFile, jsFile, containerId) {
    console.log(`Loading HTML: ${htmlFile} and JS: ${jsFile} for container: ${containerId}`);
  
    fetch(htmlFile)
      .then(response => response.text())
      .then(html => {
        document.getElementById(containerId).innerHTML = html;
  
        // Check if script already exists
        let existingScript = document.querySelector(`script[data-tab="${tabId}"]`);
  
        if (!existingScript) {
          const script = document.createElement('script');
          script.src = jsFile + '?v=' + new Date().getTime();
          script.defer = true;
          script.dataset.tab = tabId; // mark script with the tab
          script.onload = function () {
            console.log(`${jsFile} loaded successfully`);
  
            if (tabId === 'matching' && typeof initializeMatching === "function") {
              initializeMatching();
            }
  
            if (tabId === 'history' && typeof initializeHistory === "function") {
              initializeHistory();
            }
          };
          document.body.appendChild(script);
        } else {
          // Script is already loaded, re-run initialization
          if (tabId === 'matching' && typeof initializeMatching === "function") {
            initializeMatching();
          }
  
          if (tabId === 'history' && typeof initializeHistory === "function") {
            initializeHistory();
          }
        }
      })
      .catch(error => console.error(`Error loading ${htmlFile}:`, error));
  }
  
  function loadUserProfile() {
    console.log("Loading user profile...");
  
    fetch('./profile.html')
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
      case 'event':
        loadContent('event', './event.html', './components/event.js', 'event-management-container');
        break;
      case 'profile':
        loadContent('profile', './profile.html', './components/profile.js', 'profile');
        break;
      case 'matching':
        loadContent('matching', './matching.html', './components/matching.js', 'matching-form');
        break;
      case 'history':
        loadContent('history', './volHistory.html', './components/volHistory.js', 'history');
        break;
      case 'notification':
        loadContent('notification', './notifications.html', './components/notifications.js', 'notification');
        break;
      default:
        console.error(`Invalid tab ID: ${tabId}`);
    }
  
    $('.nav-tabs a[href="#' + tabId + '"]').tab('show');
  }
  
  // Setup tab navigation and back/forward buttons
  $(document).ready(function () {
    $('.nav-link').on('click', function (e) {
      e.preventDefault();
      let tabId = $(this).attr('href').substring(1);
      history.pushState(null, null, `#${tabId}`);
      showTab(tabId);
    });
  
    let hash = window.location.hash;
    if (hash) {
      showTab(hash.substring(1));
    } else {
      showTab('event');
    }
  
    window.addEventListener('popstate', function () {
      let hash = window.location.hash;
      if (hash) {
        showTab(hash.substring(1));
      } else {
        showTab('event');
      }
    });
  });  
