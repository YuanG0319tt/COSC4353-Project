$(document).ready(function () {
    loadNotifications(); // Load notifications when the page loads

    // $("#send-announcement").click(function () {
    //     $("#announcement-modal, #modal-overlay").removeClass("hidden");
    // });

    $("#close-announcement-modal, #modal-overlay").click(function () {
        $("#announcement-modal, #modal-overlay").addClass("hidden");
    });

    $("#announcement-form").submit(function (event) {
        event.preventDefault();
        const submitButton = document.querySelector("#announcement-form button[type='submit']");
    if (submitButton.disabled) return; // 如果按钮已禁用，则直接返回
    // 禁用提交按钮
    submitButton.disabled = true;
        const title = $("#announcement-title").val();
        const message = $("#announcement-message").val();

        $.ajax({
            url: "http://localhost:8080/api/notifications/add",
            method: "POST",
            contentType: "application/json",
            data: JSON.stringify({ title, message, type: "announcement" }),
            success: function () {
                alert("Announcement sent!");
                $("#announcement-modal, #modal-overlay").addClass("hidden");
                $("#announcement-form")[0].reset();
                loadNotifications();
                submitButton.disabled = false;
            },
            error: function () {
                submitButton.disabled = false;
                alert("Failed to send announcement.");
            }
        });
    });

    function loadNotifications() {
        $.get("http://localhost:8080/api/notifications/list", function (data) {
            $("#notification-list").empty();
            data.data.reverse().forEach(notification => {
                $("#notification-list").append(`
                    <div class="notification-item ${notification.type}">
                        <strong>${notification.title}</strong>: ${notification.message}
                        <br><small><em>${notification.createTime}</em></small>
                    </div>
                `);
            });
        });
    }

    // Set "All" as active by default
    $("#filter-all").addClass("active");

    $("#filter-all").click(function() {
        loadNotifications();
        // Update active state
        $(".notification-filters button").removeClass("active");
        $(this).addClass("active");
    });

    $("#filter-announcements").click(function() {
        filterNotifications("Announcements");
        // Update active state
        $(".notification-filters button").removeClass("active");
        $(this).addClass("active");
    });

    $("#filter-notifications").click(function() {
        filterNotifications("Notifications");
        // Update active state
        $(".notification-filters button").removeClass("active");
        $(this).addClass("active");
    });

    function filterNotifications(type) {
        $.get("http://localhost:8080/api/notifications/list", function (data) {
            $("#notification-list").empty();
            data.data.filter(n => n.type === type).forEach(notification => {
                $("#notification-list").append(`
                    <div class="notification-item ${notification.type}">
                        <strong>${notification.title}</strong>: ${notification.message}
                        <br><small><em>${notification.createTime}</em></small>
                    </div>
                `);
            });
        });
    } 
});