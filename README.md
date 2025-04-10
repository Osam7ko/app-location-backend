# Spring Boot Backend - Location App 📍

This is the backend for the Location App, built with Spring Boot.  
It handles authentication, user management, and saving favorite locations

## ✅ Features
- JWT-based Authentication & Authorization
- User registration and login
- Secure REST APIs with Spring Security
- CRUD for favorite locations
- Only allow each user to access their own data

## 📦 Project Structure
```
com.bk.wakeb.main.project.bkProject
├── controller
├── dto
├── model
├── repository
├── security
└── service
```

## ⚙️ Tech Stack
- Java 17+
- Spring Boot
- Spring Security + JWT
- H2 / PostgreSQL
- Maven / Gradle

## 🚀 How to Run
```bash
# Run from IDE (Spring Boot App)
# OR via command line:
./mvnw spring-boot:run
```

> Make sure to set up your database credentials in `application.properties`

---

## 🔐 API Authentication
All secure endpoints require a Bearer token:
```
Authorization: Bearer <your_token>
```

## 👤 Endpoints
- `/api/auth/register`
- `/api/auth/login`
- `/api/auth/me`
- `/api/location/` *(GET/POST/DELETE)*

---

## 📁 Sample Request Payload

```json
{
  "name": "Favorite Place",
  "latitude": 24.7136,
  "longitude": 46.6753
}
```

---

## 📬 Contact
Built by Osama
