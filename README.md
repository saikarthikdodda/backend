Capstone – Investment Banking Deal Pipeline Management Portal

Project Overview

Investment Banking Deal Pipeline Management Portal  is a Spring Boot–based Deal Management System designed to manage users and business deals efficiently.
It follows a layered architecture (Controller → Service → Repository) and exposes REST APIs that can be consumed by a frontend (Angular).

The application supports:

--User management

--Deal creation and updates

--Secure APIs

--Dockerized deployment

**Tech Stack
Backend

Java 17

Spring Boot

Spring Web (REST APIs)


Spring Security 

Maven

Database

MongoDB (NoSQL)

Tools & DevOps

Maven 

Docker

Git

📂 Project Structure
Capstone1
│
├── src/main/java
│   └── com.java.capstone1
│       ├── controller   → REST controllers
│       ├── service      → Business logic
│       ├── repository   → Database access (JPA)
│       ├── model        → Entity classes
│       ├── dto          → Data Transfer Objects
│       └── exception    → Custom exceptions
│
├── src/main/resources
│   ├── application.properties
│   └── application.yml
│
├── Dockerfile
├── pom.xml
└── README.md

--Application Architecture

This project follows standard Spring Boot best practices:

Controller Layer
Handles HTTP requests and responses

Service Layer
Contains business logic

Repository Layer
Communicates with the database using JPA

DTOs
Used to transfer data safely between layers

* Security

Spring Security is used to protect APIs

Role-based access can be enabled (ADMIN / USER)

Unauthorized access is restricted

* Exception Handling

Custom exceptions like ResourceNotFoundException

Proper HTTP status codes returned

Centralized error handling

▶️ How to Run the Application (Without Docker)
1️⃣ Prerequisites

Java 17 installed

Maven installed

MongoDB running locally or remotely

2️⃣ Build the Project
mvn clean package

3️⃣ Run the Application
mvn spring-boot:run


OR

java -jar target/*.war

4️⃣ Access the App
http://localhost:8080 

🐳 Run Using Docker
1️⃣ Build the WAR File
mvn clean package

2️⃣ Build Docker Image
docker build -t capstone1-app .

3️⃣ Run Docker Container
docker run -d -p 8080:8080 capstone1-app

4️⃣ Access Application
http://localhost:8081

📌 Sample APIs
Method	Endpoint	Description
POST	/users	Create user
GET	/users	Get all users
POST	/deals	Create deal
PATCH	/deals/{id}/stage	Update deal stage
PATCH	/deals/{id}/sector	Update sector
PATCH	/deals/{id}/summary	Update summary

* Key Features

✔ Clean layered architecture

✔ RESTful API design

✔ Exception handling

✔ Secure endpoints

✔ Docker support

✔ Easy frontend integration (Angular)

* Future Enhancements


Pagination & sorting

Swagger API documentation

CI/CD pipeline

Cloud deployment

** Author

Dodda Sai Karthik

Java Full Stack Developer