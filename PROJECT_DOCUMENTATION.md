# Task Management System — Project Documentation
**Week 4 | STEM OPT Training Program | Hashclick Solutions**

---

## 1. Project Overview

The Task Management System is a full-stack web application built as part of the STEM OPT Training Program at Hashclick Solutions. This Week 4 version extends the Week 3 application with Role-Based Access Control (RBAC), advanced features, and live cloud deployment on Render.com.

- **Live URL:** https://hashclick-week4-java.onrender.com
- **GitHub:** https://github.com/arungampala369-dotcom/hashclick-week4-java
- **Trainee:** Arun Gampala
- **Company:** Hashclick Solutions
- **Week:** Week 4 — June 8 to June 12, 2026

---

## 2. Technologies Used

| Technology | Version | Purpose |
|------------|---------|---------|
| Java | 17 | Programming language |
| Spring Boot | 3.2.5 | Backend framework |
| Spring Security | 6.2.4 | Authentication and authorization |
| JWT (jjwt) | 0.11.5 | Token-based authentication |
| Spring Data JPA | 6.4.4 | Database ORM |
| PostgreSQL | 18 | Production database (Render) |
| H2 | Latest | Local development database |
| HTML / CSS / JavaScript | - | Frontend UI |
| Maven | 3.9.16 | Build tool |
| Docker | - | Containerization for deployment |
| Render.com | - | Cloud hosting platform |

---

## 3. Features

### 3.1 Authentication
- User registration with email and password
- JWT-based login (token stored in localStorage)
- Secure password hashing with BCrypt
- Auto-redirect to dashboard on login

### 3.2 Role-Based Access Control (RBAC)
- Two roles: **USER** and **ADMIN**
- Regular users see only their own tasks
- Admin users see all tasks from all users
- Admin badge displayed in navbar
- Task owner name shown in admin view
- Users can only edit/delete their own tasks; Admin can edit/delete any task

### 3.3 Task Management (CRUD)
- Create tasks with title, description, priority, and due date
- Edit tasks — update status, priority, due date
- Delete tasks with confirmation dialog
- Filter tasks by status (To Do / In Progress / Done)
- Dashboard stats — Total, To Do, In Progress, Done counts

### 3.4 Advanced Features
- **Due date notifications** — warning banner for overdue tasks and tasks due within 2 days
- **Input validation** — title must be at least 3 characters
- **Password security** — password hash never exposed in API responses

---

## 4. Project Structure

```
WeekFourJava/
├── Dockerfile
├── pom.xml
└── src/main/
    ├── java/com/hashclick/taskmanager/
    │   ├── TaskManagerApplication.java
    │   ├── controller/
    │   │   ├── AuthController.java
    │   │   └── TaskController.java
    │   ├── service/
    │   │   ├── AuthService.java
    │   │   ├── TaskService.java
    │   │   └── UserDetailsServiceImpl.java
    │   ├── repository/
    │   │   ├── UserRepository.java
    │   │   └── TaskRepository.java
    │   ├── model/
    │   │   ├── User.java       (roles: USER, ADMIN)
    │   │   └── Task.java       (createdBy, assignedTo)
    │   ├── security/
    │   │   ├── SecurityConfig.java
    │   │   ├── JwtUtil.java
    │   │   └── JwtFilter.java
    │   └── dto/
    │       ├── AuthRequest.java
    │       ├── AuthResponse.java  (includes role field)
    │       └── RegisterRequest.java
    └── resources/
        ├── application.properties
        └── static/
            ├── login.html
            ├── register.html
            ├── dashboard.html
            ├── css/style.css
            └── js/
                ├── auth.js
                └── dashboard.js
```

---

## 5. API Endpoints

| Method | URL | Description | Auth |
|--------|-----|-------------|------|
| POST | /api/auth/register | Register new user | No |
| POST | /api/auth/login | Login and get JWT token | No |
| GET | /api/auth/make-admin | Promote user to Admin role | No (secret key) |
| GET | /api/tasks | Get tasks (filtered by role) | JWT |
| POST | /api/tasks | Create new task | JWT |
| PUT | /api/tasks/{id} | Update task | JWT |
| DELETE | /api/tasks/{id} | Delete task | JWT |
| GET | /api/tasks/users | Get all users (Admin only) | JWT |

---

## 6. Database Schema

### Users Table
| Column | Type | Description |
|--------|------|-------------|
| id | BIGINT | Primary key |
| name | VARCHAR | Full name |
| email | VARCHAR | Unique email (login) |
| password | VARCHAR | BCrypt hashed |
| role | VARCHAR | USER or ADMIN |

### Tasks Table
| Column | Type | Description |
|--------|------|-------------|
| id | BIGINT | Primary key |
| title | VARCHAR | Task title |
| description | VARCHAR | Task description |
| status | VARCHAR | TODO / IN_PROGRESS / DONE |
| priority | VARCHAR | LOW / MEDIUM / HIGH |
| due_date | DATE | Optional due date |
| created_by | BIGINT | FK → users.id |
| assigned_to | BIGINT | FK → users.id |

---

## 7. How to Run Locally

### Prerequisites
- Java 17+
- Maven 3.9+

### Steps
```bash
cd WeekFourJava
mvn spring-boot:run
```

Open browser at: http://localhost:8080/login.html

H2 Console (local only): http://localhost:8080/h2-console
- JDBC URL: `jdbc:h2:mem:taskdb`
- Username: `SA`
- Password: (leave blank)

---

## 8. Deployment

The application is deployed on **Render.com** using Docker.

### Environment Variables (set on Render)
| Variable | Description |
|----------|-------------|
| `DATABASE_URL` | PostgreSQL connection URL |
| `DB_DRIVER` | `org.postgresql.Driver` |
| `JWT_SECRET` | Secret key for JWT signing |
| `PORT` | Server port (set by Render automatically) |

### Deployment Steps
1. Code pushed to GitHub
2. Render pulls from GitHub and builds Docker image
3. Docker image runs the Spring Boot JAR
4. PostgreSQL database hosted on Render free tier

**Live URL:** https://hashclick-week4-java.onrender.com

---

## 9. Security Notes

- Passwords are hashed using BCrypt (never stored in plain text)
- JWT tokens expire after 24 hours
- Password field is hidden from all API responses (`@JsonIgnore`)
- All task endpoints require a valid JWT token
- Users cannot access or modify other users' tasks

---

## 10. Author

| Field | Details |
|-------|---------|
| Name | Arun Gampala |
| Email | arun@hashclicksolutions.com |
| Program | STEM OPT Trainee — Java |
| Company | Hashclick Solutions |
| HR Email | hr@hashclicksolutions.com |
