# <img src="https://github.com/user-attachments/assets/8a2bf592-5845-426c-bb48-438d08896ab9" height="30" /> Naba - Volunteer Management System

Welcome to the Volunteer Management System! This system is designed to help organizations manage volunteer activities efficiently. It includes features like event creation, volunteer matching, and tracking volunteer participation history.

## Why "Naba"?
"Naba" is derived from the Arabic word meaning "good news" or "announcement." We chose this name because our system is designed to bring positive change by connecting volunteers with meaningful opportunities. Just as "Naba" signifies an important message, our platform serves as a crucial link between organizations and volunteers, making a lasting impact in communities.

## Table of Contents

- [Features](#features)
- [Getting Started](#getting-started)
- [Usage](#usage)
- [Contributing](#contributing)
- [License](#license)

## Features

- **Event Creation**: Create and manage events with details like event name, description, location, required skills, urgency, event date, and participation status.
- **Volunteer Matching**: Match volunteers to events based on filters like location and availability.
- **Volunteer History**: Track volunteer participation and completion status.
- **Display Notifications**: Provides updates log of all events on users' accounts and upcoming events. Admin can also make their own "announcement" notifications.
- **User Profiles**: Enables users to control, view, and edit their personal details, availability, and skill sets.

## Getting Started

To get a local copy up and running, follow these simple steps.

### Prerequisites

- **Backend**: Maven for building and running the Java backend.
- **Frontend**: Node.js and npm for managing and running the frontend.
- **Technologies**:
  - HTML, CSS, JavaScript
  - Bootstrap 5 CSS for styling
  - MySQL for the database

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/YuanG0319tt/COSC4353-Project.git
   ```

2. **Backend Setup (Maven)**:
   - Navigate to the backend directory:
     ```bash
     cd path/to/backend
     ```
   - Build the project using Maven:
     ```bash
     mvn clean install
     ```
   - Run the backend application:
     ```bash
     mvn spring-boot:run
     ```
   - The backend should now be running at `http://localhost:8080`.

3. **Frontend Setup**:
   - Navigate to the frontend directory:
     ```bash
     cd path/to/frontend
     ```
   - Install the required dependencies:
     ```bash
     npm install
     ```
   - Start the frontend application:
     ```bash
     npm start
     ```
   - The frontend should now be running at `http://localhost:3000`.

---

Now, the backend and frontend should be running locally. You can open your browser and go to `http://localhost:3000` to access the application.

### Usage

Once everything is up and running, you can start interacting with the Volunteer Management System by creating events, matching volunteers, and managing user profiles.
