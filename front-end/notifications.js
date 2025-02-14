$(document).ready(function () {
    let notifications = [
        { 
            id: 1, 
            type: 'announcement', 
            title: 'System Update', 
            message: 'Scheduled maintenance at 10 PM', 
            timestamp: new Date().toLocaleString() 
        },
        { 
            id: 2, 
            type: 'notification', 
            title: 'Event Reminder', 
            message: 'Meeting at 3 PM', 
            timestamp: new Date().toLocaleString() 
        }
    ];

    function renderNotifications(filter = 'all') {
        $('#notification-list').empty();
        notifications.filter(n => filter === 'all' || n.type === filter).forEach(n => {
            $('#notification-list').append(
                `<div class="notification-item ${n.type}">
                    <strong>${n.title}</strong>: ${n.message}
                    <br><small><em>${n.timestamp}</em></small>
                </div>`
            );
        });
    }

    $('#send-announcement').click(function () {
        $('#announcement-modal, #modal-overlay').removeClass('hidden');
    });

    $('#close-announcement-modal, #modal-overlay').click(function () {
        $('#announcement-modal, #modal-overlay').addClass('hidden');
    });

    $('#announcement-form').submit(function (e) {
        e.preventDefault();
        let title = $('#announcement-title').val();
        let message = $('#announcement-message').val();
        let timestamp = new Date().toLocaleString(); // Capture current time

        notifications.unshift({ 
            id: Date.now(), 
            type: 'announcement', 
            title, 
            message, 
            timestamp 
        });

        renderNotifications();
        $('#announcement-modal, #modal-overlay').addClass('hidden');
        $('#announcement-form')[0].reset();
    });

    $('#filter-all').click(() => renderNotifications('all'));
    $('#filter-announcements').click(() => renderNotifications('announcement'));
    $('#filter-notifications').click(() => renderNotifications('notification'));

    renderNotifications();
});
