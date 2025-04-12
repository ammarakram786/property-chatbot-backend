# Property Chatbot Backend

A Spring Boot application that serves as the backend for a property management chatbot system. The system handles user authentication, appointment scheduling with automated reminders, and integrates with OpenAI for chatbot functionality.

## Features

- User authentication and authorization with JWT
- Role-based access control (User and Admin roles)
- Appointment management system
- Automated appointment reminders using Quartz scheduler
- AI-powered chatbot integration using OpenAI
- PostgreSQL database storage

## Technologies

- Java
- Spring Boot
- Spring Security with JWT
- Spring Data JPA
- Quartz Scheduler
- OpenAI API integration
- PostgreSQL
- Maven

## Prerequisites

- JDK 11 or higher
- Maven
- PostgreSQL database
- OpenAI API key

## Setup and Running

### 1. Database Setup

Create a PostgreSQL database:

```bash
createdb property_chatbot
```

### 2. Environment Variables

Set up environment variable for OpenAI:

```bash
export OPENAI_API_KEY=your-openai-api-key
```

### 3. Build the Project

```bash
mvn clean package
```

### 4. Run the Application

```bash
java -jar target/property-chatbot-backend.jar
```

Alternatively, using Maven:

```bash
mvn spring-boot:run
```

The application will start on port 8085 (as configured in `application.yml`).

## Default Credentials

The system initializes with a test user:
- Username: `testuser`
- Email: `testuser@example.com`
- Password: `password`
- Role: `ROLE_USER`

## Configuration

Main configuration parameters can be modified in `application.yml`:
- Database connection details
- Server port
- JWT settings
- Logging levels
- OpenAI API integration

