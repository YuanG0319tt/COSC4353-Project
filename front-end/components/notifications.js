$(document).ready(function () {
    loadNotifications(); // Load notifications when the page loads

    $("#send-announcement").click(function () {
        $("#announcement-modal, #modal-overlay").removeClass("hidden");
    });

    $("#close-announcement-modal, #modal-overlay").click(function () {
        $("#announcement-modal, #modal-overlay").addClass("hidden");
    });

    $("#announcement-form").submit(function (event) {
        event.preventDefault();
        const title = $("#announcement-title").val();
        const message = $("#announcement-message").val();

        $.ajax({
            url: "http://localhost:8080/notifications",
            method: "POST",
            contentType: "application/json",
            data: JSON.stringify({ title, message, type: "announcement" }),
            success: function () {
                alert("Announcement sent!");
                $("#announcement-modal, #modal-overlay").addClass("hidden");
                $("#announcement-form")[0].reset();
                loadNotifications();
            },
            error: function () {
                alert("Failed to send announcement.");
            }
        });
    });

    function loadNotifications() {
        $.get("http://localhost:8080/notifications", function (data) {
            $("#notification-list").empty();
            data.forEach(notification => {
                $("#notification-list").append(`
                    <div class="notification-item ${notification.type}">
                        <strong>${notification.title}</strong>: ${notification.message}
                        <br><small><em>${notification.timestamp}</em></small>
                    </div>
                `);
            });
        });
    }

    $("#filter-all").click(() => loadNotifications());
    $("#filter-announcements").click(() => filterNotifications("announcement"));
    $("#filter-notifications").click(() => filterNotifications("notification"));

    function filterNotifications(type) {
        $.get("http://localhost:8080/notifications", function (data) {
            $("#notification-list").empty();
            data.filter(n => n.type === type).forEach(notification => {
                $("#notification-list").append(`
                    <div class="notification-item ${notification.type}">
                        <strong>${notification.title}</strong>: ${notification.message}
                        <br><small><em>${notification.timestamp}</em></small>
                    </div>
                `);
            });
        });
    }
});
