CREATE DATABASE IF NOT EXISTS nabaDatabase;
USE nabaDatabase;

-- Table for storing user login credentials
CREATE TABLE UserCredentials (
    UserID INT AUTO_INCREMENT PRIMARY KEY COMMENT '用户ID',
    Password VARCHAR(255) NOT NULL COMMENT '密码'
);

-- Table for storing extended user profile information
CREATE TABLE UserProfile (
    UserID INT PRIMARY KEY COMMENT '用户ID',
    FullName VARCHAR(255) NOT NULL COMMENT '名字',
    Address VARCHAR(255) COMMENT '地址',
    City VARCHAR(100) COMMENT '城市',
    State VARCHAR(100) COMMENT '州',
    ZipCode VARCHAR(20) COMMENT '邮编',
    Skills TEXT COMMENT '技能',
    Preferences TEXT COMMENT '喜好',
    Availability TEXT COMMENT '可用时间',
    FOREIGN KEY (UserID) REFERENCES UserCredentials(UserID)
);

-- Table for storing details of volunteering events
CREATE TABLE EventDetails (
    EventID INT AUTO_INCREMENT PRIMARY KEY COMMENT '事件ID',
    EventName VARCHAR(255) NOT NULL COMMENT '事件名称',
    Description TEXT COMMENT '事件描述',
    Location VARCHAR(255) COMMENT '地点',
    RequiredSkills TEXT COMMENT '所需技能',
    Urgency INT COMMENT '事件等级',
    EventDate DATE NOT NULL COMMENT '事件日期'
);

-- Table for tracking user participation in events
CREATE TABLE VolunteerHistory (
    HistoryID INT AUTO_INCREMENT PRIMARY KEY COMMENT '历史Id',
    UserID INT COMMENT '用户ID',
    EventID INT COMMENT '事件ID',
    ParticipationDate DATE COMMENT '参加日期',
    FOREIGN KEY (UserID) REFERENCES UserCredentials(UserID),
    FOREIGN KEY (EventID) REFERENCES EventDetails(EventID)
);

-- Table for listing valid U.S. state codes and names
CREATE TABLE States (
    StateCode VARCHAR(10) PRIMARY KEY COMMENT '州缩写',
    StateName VARCHAR(100) NOT NULL COMMENT '州名称'
);
