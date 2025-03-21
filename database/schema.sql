
CREATE DATABASE IF NOT EXISTS nabaDatabase;
USE nabaDatabase;


CREATE TABLE UserCredentials (
    UserID INT AUTO_INCREMENT PRIMARY KEY,
    Password VARCHAR(255) NOT NULL
);


CREATE TABLE UserProfile (
    UserID INT PRIMARY KEY,
    FullName VARCHAR(255) NOT NULL,
    Address VARCHAR(255),
    City VARCHAR(100),
    State VARCHAR(100),
    ZipCode VARCHAR(20),
    Skills TEXT,
    Preferences TEXT,
    Availability TEXT,
    FOREIGN KEY (UserID) REFERENCES UserCredentials(UserID)
);


CREATE TABLE EventDetails (
    EventID INT AUTO_INCREMENT PRIMARY KEY,
    EventName VARCHAR(255) NOT NULL,
    Description TEXT,
    Location VARCHAR(255),
    RequiredSkills TEXT,
    Urgency INT,
    EventDate DATE NOT NULL
);


CREATE TABLE VolunteerHistory (
    HistoryID INT AUTO_INCREMENT PRIMARY KEY,
    UserID INT,
    EventID INT,
    ParticipationDate DATE,
    FOREIGN KEY (UserID) REFERENCES UserCredentials(UserID),
    FOREIGN KEY (EventID) REFERENCES EventDetails(EventID)
);


CREATE TABLE States (
    StateCode VARCHAR(10) PRIMARY KEY,
    StateName VARCHAR(100) NOT NULL
);
