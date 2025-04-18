$(document).ready(function() {
    const API_URL = "http://localhost:8080/api/events"; // 注意复数形式更符合REST规范
    let currentDate = new Date();
    let selectedEvent = null;
    let events = {}; // 将从后端加载事件数据

    // 初始化函数
    async function init() {
        await fetchEvents();
        renderCalendar();
        setupEventListeners();
    }

// 从后端获取事件数据
async function fetchEvents() {
    try {
        const response = await fetch(API_URL+'/list', { // 直接使用基础API路径
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${localStorage.getItem('userToken') || ''}`
            }
        });
        
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        
        const result = await response.json();
        
        // 将事件数据转换为前端需要的格式
        events = {};
        result.data.forEach(event => {
            if (!events[event.date]) {
                events[event.date] = [];
            }
            
            // 处理requiredSkills - 如果是字符串转换为数组
            const skills = typeof event.requiredSkills === 'string' 
                ? [event.requiredSkills] 
                : event.requiredSkills || [];
            
            events[event.date].push({
                eventName: event.name,
                eventTime: event.date,
                eventDescription: event.description,
                location: event.location,
                requiredSkills: skills, // 确保是数组
                urgency: event.urgency,
                id: event.id
            });
        });
    } catch (error) {
        console.error("Error fetching events:", error);
        showMessage(error.message, "error");
    }
}

function formatLocalDate(date) {
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    return `${year}-${month}-${day}`;
}


    // 渲染日历
    function renderCalendar() {
        const calendarDays = $("#calendar-days");
        calendarDays.empty();
        const totalDays = daysInMonth(currentDate);
        const firstDay = firstDayOfMonth(currentDate);
        
        // 添加空白格子
        for (let i = 0; i < firstDay; i++) {
            calendarDays.append('<div class="calendar-day empty"></div>');
        }
        
        // 添加日期格子
        for (let day = 1; day <= totalDays; day++) {
            const date = new Date(currentDate.getFullYear(), currentDate.getMonth(), day);
            const dateString = formatLocalDate(date); 
            const dayEvents = events[dateString] || [];
            
            const dayElement = $(`<div class="calendar-day" data-date="${dateString}"></div>`);
            dayElement.append(`<span class="day-number">${day}</span>`);
            
            dayEvents.forEach(event => {
                const eventItem = $(`<div class="event-item ${event.urgency.toLowerCase()}"></div>`);
                eventItem.text(event.eventName);
                eventItem.click(() => showEventDetails(event));
                dayElement.append(eventItem);
            });
            
            calendarDays.append(dayElement);
        }
        
        $("#current-month-year").text(`${currentDate.toLocaleString('default', { month: 'long' })} ${currentDate.getFullYear()}`);
    }

    // 显示事件详情
    function showEventDetails(event) {
        selectedEvent = event;
        $("#event-name").text(event.eventName);
        $("#event-time").text(event.eventTime);
        $("#event-description").text(event.eventDescription);
        $("#event-location").text(event.location);
        $("#event-required-skills").text(event.requiredSkills.join(", "));
        $("#event-urgency").text(event.urgency);
        $("#event-modal").removeClass("hidden");
        $("#modal-overlay").removeClass("hidden");
    }

    // 添加新事件
    async function addNewEvent(eventData) {
        event.preventDefault();
        const submitButton = document.querySelector("#add-event-form button[type='submit']");
    if (submitButton.disabled) return; // 如果按钮已禁用，则直接返回
    // 禁用提交按钮
    submitButton.disabled = true;
        try {
            const response = await fetch(API_URL+'/add', {
                method: "POST",
                headers: {
                    'Authorization': `Bearer ${localStorage.getItem('userToken') || ''}`,
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    name: eventData.eventName,
                    description: eventData.eventDescription,
                    location: eventData.location,
                    requiredSkills: eventData.requiredSkills,
                    urgency: eventData.urgency,
                    date: eventData.eventTime
                })
            });
            
            if (!response.ok) {
                const errorData = await response.json();
                throw new Error(errorData.message || "添加事件失败");
            }
            
            showMessage("事件添加成功", "success");
            await fetchEvents(); // 刷新事件列表
            renderCalendar();
            return true;
        } catch (error) {
            console.error("Error adding event:", error);
            showMessage("添加事件失败: " + error.message, "error");
            return false;
        }finally{
            // 重新启用提交按钮，无论成功还是失败
            submitButton.disabled = false;
        };
    }

    // 辅助函数
    function daysInMonth(date) {
        return new Date(date.getFullYear(), date.getMonth() + 1, 0).getDate();
    }

    function firstDayOfMonth(date) {
        return new Date(date.getFullYear(), date.getMonth(), 1).getDay();
    }

    function changeMonth(increment) {
        currentDate = new Date(currentDate.getFullYear(), currentDate.getMonth() + increment, 1);
        renderCalendar();
    }

    function showMessage(message, type) {
        const alertClass = type === "success" ? "alert-success" : "alert-danger";
        const messageDiv = $(`
            <div class="alert ${alertClass} alert-dismissible fade show" role="alert">
                ${message}
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        `);
        
        $("#messageContainer").html(messageDiv);
        setTimeout(() => messageDiv.alert('close'), 3000);
    }

    // 设置事件监听
    function setupEventListeners() {
        $("#prev-month").click(() => changeMonth(-1));
        $("#next-month").click(() => changeMonth(1));
        
        $("#close-event-modal, #modal-overlay").click(() => {
            $("#event-modal").addClass("hidden");
            $("#modal-overlay").addClass("hidden");
        });
        
        $("#add-event-button").click(() => {
            $("#new-event-date").val(new Date().toISOString().split('T')[0]);
            $("#add-event-modal").removeClass("hidden");
            $("#modal-overlay").removeClass("hidden");
        });
        
        $("#close-add-event-modal").click(() => {
            $("#add-event-modal").addClass("hidden");
            $("#modal-overlay").addClass("hidden");
        });
        
        $("#add-event-form").submit(async (e) => {
            e.preventDefault();
            
            const newEvent = {
                eventName: $("#new-event-name").val().trim(),
                eventTime: $("#new-event-date").val(),
                eventDescription: $("#new-event-description").val().trim(),
                location: $("#new-location").val().trim(),
                requiredSkills: $("#new-required-skills").val() || [],
                urgency: $("#new-urgency").val()
            };
            
            if (!newEvent.eventName || !newEvent.eventTime) {
                showMessage("请填写事件名称和日期", "error");
                return;
            }
            
            const success = await addNewEvent(newEvent);
            if (success) {
                $("#add-event-form")[0].reset();
                $("#add-event-modal").addClass("hidden");
                $("#modal-overlay").addClass("hidden");
            }
        });
    }

    // 初始化应用
    init();
});