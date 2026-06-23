# Task Management System — Week 3

STEM OPT Training Program | Hashclick Solutions | Java — Spring Boot + HTML/CSS/JS

## Description
A full-stack Task Management System with JWT authentication, built using Spring Boot for the backend and HTML/CSS/JavaScript for the frontend.

## Technologies
- Java 17
- Spring Boot 3.2.5
- Spring Security + JWT
- Spring Data JPA
- H2 In-Memory Database
- HTML + CSS + JavaScript (Frontend)
- Maven 3.9.16

## Features
- User Registration and Login with JWT
- Add, Edit, Delete Tasks
- Filter tasks by status (To Do / In Progress / Done)
- Dashboard with task statistics
- Protected API endpoints

## Project Structure
```
src/main/java/com/hashclick/taskmanager/
├── TaskManagerApplication.java
├── controller/
│   ├── AuthController.java
│   └── TaskController.java
├── service/
│   ├── AuthService.java
│   ├── TaskService.java
│   └── UserDetailsServiceImpl.java
├── repository/
│   ├── UserRepository.java
│   └── TaskRepository.java
├── model/
│   ├── User.java
│   └── Task.java
├── security/
│   ├── SecurityConfig.java
│   ├── JwtUtil.java
│   └── JwtFilter.java
└── dto/
    ├── AuthRequest.java
    ├── AuthResponse.java
    └── RegisterRequest.java

src/main/resources/static/
├── login.html
├── register.html
├── dashboard.html
├── css/style.css
└── js/
    ├── auth.js
    └── dashboard.js
```

## How to Run
```bash
mvn spring-boot:run
```
Open browser at: `http://localhost:8080/login.html`

## API Endpoints

| Method | URL | Description | Auth |
|--------|-----|-------------|------|
| POST | /api/auth/register | Register user | No |
| POST | /api/auth/login | Login user | No |
| GET | /api/tasks | Get all tasks | JWT |
| POST | /api/tasks | Create task | JWT |
| PUT | /api/tasks/{id} | Update task | JWT |
| DELETE | /api/tasks/{id} | Delete task | JWT |
