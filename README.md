# 📌 Placement Assistance Platform

## 🚀 Overview

The **Placement Assistance Platform** is a web-based application designed to help students prepare for placements and connect with job opportunities. It provides features like mock tests, resume building, job tracking, and AI-based assistance to enhance student readiness for recruitment processes.

---

## 🎯 Objectives

* Help students prepare for campus placements
* Provide mock tests and assessments
* Enable resume creation and management
* Track job applications and statuses
* Offer AI-based chatbot support for guidance

---

## 🛠️ Tech Stack

### 🔹 Backend

* Java
* Spring Boot
* Spring MVC
* Hibernate / JPA
* MySQL

### 🔹 Frontend

* Thymeleaf
* HTML
* CSS
* JavaScript

### 🔹 Tools & Technologies

* Git & GitHub
* NetBeans / IntelliJ
* Postman
* Ollama (Gemma 2B for AI integration)

---

## ✨ Features

### 👤 User Module

* User Registration & Login
* Profile Management
* Resume Builder

### 📝 Mock Test Module

* Create & Attempt Mock Tests
* Timer-based tests
* Score evaluation & results

### 💼 Job Module

* View job listings
* Apply for jobs
* Track application status

### 🤖 AI Chatbot

* Provides guidance for placement preparation
* Helps with interview questions and resume tips

### 📊 Admin Module

* Manage users
* Manage job postings
* Manage test content

---

## 📂 Project Structure

```
placement-assistance/
│── src/
│   ├── main/
│   │   ├── java/com/project/
│   │   │   ├── controller/
│   │   │   ├── service/
│   │   │   ├── repository/
│   │   │   ├── model/
│   │   │   └── config/
│   │   ├── resources/
│   │   │   ├── templates/
│   │   │   ├── static/
│   │   │   └── application.properties
│── pom.xml
│── README.md
```

---

## ⚙️ Installation & Setup

### 🔹 Prerequisites

* Java 8 or above
* Maven
* MySQL

### 🔹 Steps

1. Clone the repository:

```bash
git clone https://github.com/your-username/placement-assistance.git
```

2. Navigate to project folder:

```bash
cd placement-assistance
```

3. Configure database in `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/placement_db
spring.datasource.username=root
spring.datasource.password=your_password
```

4. Run the application:

```bash
mvn spring-boot:run
```

5. Open browser:

```
http://localhost:8080
```

---

## 📸 Screenshots

(Add screenshots of your UI here)

---

## 🔮 Future Enhancements

* Email notifications for job updates
* Integration with LinkedIn APIs
* Advanced analytics dashboard
* Mobile application version

---

## 🤝 Contribution

Contributions are welcome! Feel free to fork the repository and submit pull requests.

---

## 📧 Contact

**Gaurav Soni**
📩 Email: [your-email@example.com](mailto:your-email@example.com)
🔗 LinkedIn: your-linkedin-profile

---

## 📜 License

This project is open-source and available under the MIT License.

---

⭐ If you like this project, don't forget to give it a star!
