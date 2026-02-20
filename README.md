# 🏋️ Ai-Powered-Fitness-Microservice

> AI-powered Fitness Microservices built using Spring Boot, Apache Kafka, Keycloak, and Eureka for scalable, secure, and event-driven fitness insights generation.

![Java](https://img.shields.io/badge/Java-17+-blue)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen)
![Kafka](https://img.shields.io/badge/Apache-Kafka-black)
![Architecture](https://img.shields.io/badge/Architecture-Microservices-orange)

---

## 📌 Overview

*Fitness-Microservices* is a distributed microservices-based system designed to process user fitness activities and generate AI-powered insights.

The system follows an event-driven architecture using Apache Kafka and implements secure authentication using Keycloak.

It is scalable, modular, and production-ready.

---

## 🚀 Features

- ✅ Microservices Architecture
- ✅ API Gateway Routing
- ✅ Service Discovery with Eureka
- ✅ Centralized Configuration Server
- ✅ Event-Driven Communication using Kafka
- ✅ Secure Authentication & Authorization (Keycloak + JWT)
- ✅ Docker Support
- ✅ Scalable & Cloud-Ready Design

---

## 🛠 Tech Stack

| Component | Technology |
|------------|------------|
| Language | Java 17+ |
| Framework | Spring Boot 3.x |
| Messaging | Apache Kafka |
| Security | Keycloak |
| Service Discovery | Eureka |
| Configuration | Spring Cloud Config |
| API Gateway | Spring Cloud Gateway |
| Build Tool | Maven |
| Containerization | Docker |

---

## 🏗 Microservices Included

- *userService* → Handles user registration & profile management  
- *activityService* → Processes fitness activity data  
- *aiService* → Generates AI-based fitness insights  
- *gateway* → API Gateway (Entry point for all services)  
- *configServer* → Centralized configuration server  
- *edureka* → Eureka Service Discovery server  

---

## 📁 Project Structure

```
ai-powered-fitness-microservice/
│
├── activity-service/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/fitness/activity/
│   │   │   │   ├── controller/
│   │   │   │   │   └── ActivityController.java
│   │   │   │   ├── service/
│   │   │   │   │   └── ActivityService.java
│   │   │   │   ├── repository/
│   │   │   │   │   └── ActivityRepository.java
│   │   │   │   ├── model/
│   │   │   │   │   └── Activity.java
│   │   │   │   ├── dto/
│   │   │   │   │   └── ActivityDTO.java
│   │   │   │   └── ActivityServiceApplication.java
│   │   │   │
│   │   │   └── resources/
│   │   │       ├── application.yml
│   │   │       └── data.sql
│   │   │
│   │   └── test/
│   │       └── java/com/fitness/activity/
│   │           └── ActivityServiceTest.java
│   │
│   ├── Dockerfile
│   ├── pom.xml
│   └── README.md
│
├── user-service/
│   ├── src/main/java/com/fitness/user/
│   │   ├── controller/
│   │   ├── service/
│   │   ├── repository/
│   │   ├── model/
│   │   ├── dto/
│   │   └── UserServiceApplication.java
│   ├── src/main/resources/
│   │   └── application.yml
│   ├── Dockerfile
│   ├── pom.xml
│   └── README.md
│
├── ai-service/
│   ├── src/main/java/com/fitness/ai/
│   │   ├── controller/
│   │   ├── service/
│   │   ├── client/            
│   │   ├── model/
│   │   ├── dto/
│   │   └── AiServiceApplication.java
│   ├── src/main/resources/
│   │   └── application.yml
│   ├── Dockerfile
│   ├── pom.xml
│   └── README.md
│
├── api-gateway/
│   ├── src/main/java/com/fitness/gateway/
│   │   └── GatewayApplication.java
│   ├── src/main/resources/
│   │   └── application.yml
│   ├── Dockerfile
│   ├── pom.xml
│   └── README.md
│
├── eureka-server/
│   ├── src/main/java/com/fitness/eureka/
│   │   └── EurekaServerApplication.java
│   ├── src/main/resources/
│   │   └── application.yml
│   ├── Dockerfile
│   ├── pom.xml
│   └── README.md
│
├── config-server/
│   ├── src/main/java/com/fitness/config/
│   │   └── ConfigServerApplication.java
│   ├── src/main/resources/
│   │   └── application.yml
│   ├── Dockerfile
│   ├── pom.xml
│   └── README.md
├── .gitignore
├── README.md
```

---

## ⚙️ Prerequisites

Before running the project, ensure you have:

- Java 17+
- Maven 3.8+
- Docker & Docker Compose
- Apache Kafka
- Keycloak
- Git

---

## 🐳 Run with Docker (Recommended)

bash
# Clone the repository
git clone https://github.com/patelabhay12/Fitness-Microservices.git

cd Fitness-Microservices

# Start all services
docker-compose up -d

# Verify running containers
docker ps


---

## 💻 Run Manually

### 1️⃣ Start Infrastructure Services

- Start Kafka
- Start Keycloak
- Start Eureka Server (edureka)
- Start Config Server

### 2️⃣ Build All Services

bash
mvn clean install


### 3️⃣ Run Services Individually

bash
mvn spring-boot:run -pl edureka
mvn spring-boot:run -pl configServer
mvn spring-boot:run -pl userService
mvn spring-boot:run -pl activityService
mvn spring-boot:run -pl aiService
mvn spring-boot:run -pl gateway


---

## 🔐 Authentication Flow

1. User authenticates via Keycloak
2. Keycloak generates JWT token
3. API Gateway validates the token
4. Requests are routed to respective services
5. Services communicate asynchronously using Kafka

---

## 📡 Sample API Endpoints

### Create User

bash
POST /api/users


### Submit Activity

bash
POST /api/activities


### Get AI Insights

bash
GET /api/recommendation/{userId}


---

## 🏛 Architecture Overview


Client
   │
   ▼
API Gateway
   │
   ├── userService
   ├── activityService
   └── aiService
            │
            ▼
        Apache Kafka
            │
            ▼
         Database


---

## 🔄 Event-Driven Flow

1. User submits activity  
2. activityService publishes event to Kafka  
3. aiService consumes event  
4. AI logic processes data  
5. Insights returned via API Gateway  

---

## 🤝 Contributing

1. Fork the repository  
2. Create feature branch  
3. Commit your changes  
4. Push branch  
5. Open Pull Request  


## 👨‍💻 Author

Abhay Patel  
GitHub: https://github.com/patelabhay12  
Repository: https://github.com/patelabhay12/ai-powered-fitness-microservice

---

⭐ If you found this project useful, please give it a star!
