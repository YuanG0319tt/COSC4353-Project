USE VolunteerSystem;

INSERT INTO UserCredentials (Password) VALUES ('hashed_password_here');

-- Retrieve the last inserted ID to use for related tables
SET @last_user_id = LAST_INSERT_ID();

-- Insert initial data into the UserProfile table
INSERT INTO UserProfile (UserID, FullName, Address, City, State, ZipCode, Skills, Preferences, Availability) 
VALUES (@last_user_id, 'Jane Doe', '123 Maple St', 'Springfield', 'IL', '62701', 'Medical Assistance, Child Care', 'Weekdays', 'Mornings');

-- Insert initial data into the States table
INSERT INTO States (StateCode, StateName) VALUES 
('TX', 'Texas'),
('CA', 'California'),
('NY', 'New York'),
('FL', 'Florida'),
('IL', 'Illinois');

-- Insert initial data into the EventDetails table
INSERT INTO EventDetails (EventName, Description, Location, RequiredSkills, Urgency, EventDate) VALUES 
('Beach Cleanup', 'A community event to clean the local beach and promote environmental awareness.', 'Galveston Beach, TX', 'Organization, Environmental Conservation', 3, '2025-04-22'),
('Tech Workshop for Teens', 'A workshop to teach teenagers about programming and technology careers.', 'Public Library, CA', 'Teaching, IT Skills', 2, '2025-05-15');

-- Insert initial data into the VolunteerHistory table
INSERT INTO VolunteerHistory (UserID, EventID, ParticipationDate) VALUES 
(@last_user_id, 1, '2025-04-22');

