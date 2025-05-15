# Spring Boot Job Tracker Backend

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen) ![MongoDB](https://img.shields.io/badge/MongoDB-NoSQL-blue) ![Solr](https://img.shields.io/badge/Apache%20Solr-Full--Text%20Search-orange) ![JWT](https://img.shields.io/badge/Auth-JWT-green) ![Docker](https://img.shields.io/badge/Deployable-Docker-blue)

## 🔍 Project Overview
This is the **backend service** for a full-featured **Job Tracking Portal**, built using **Spring Boot 3.x**, designed to allow users to:

- 🔎 Search for jobs (full-text search using **Apache Solr**)
- ✅ Track the application status
- 🔐 Authenticate securely using **JWT-based Spring Security**

This is a scalable, container-ready, cloud-deployable **RESTful API service**, part of a full-stack project with Angular frontend and Kubernetes deployment support.

> 🧩 **Note:** This backend service is a small but essential part of a larger **cloud-native solution** built using **Spring Boot MVC**, **Docker**, and **Kubernetes** for real-world scalability.

## 🚀 Tech Stack & Features

- **Spring Boot 3.x (Spring MVC)** – Modern, production-grade Java backend
- **Spring Security + JWT** – Secure authentication & authorization
- **Spring Data MongoDB** – Seamless NoSQL data layer
- **Apache Solr Integration** – Powerful job search with indexing
- **RESTful API Architecture** – Cleanly organized controller/service layers
- **Maven-based Build** – Easy to compile and package
- **Docker Support** – Fully containerized for local or cloud deployments

## 🌐 Related Repositories

| Layer        | Repository                                             |
|--------------|---------------------------------------------------------|
| Frontend     | [Angular UI](https://github.com/arunjimmanuel/angular-demo-app)                |
| Kubernetes   | [K8s Deployment](https://github.com/arunjimmanuel/angular-spring-kubernetes-deployment) |

## 📦 How to Run

### 💻 Run Locally with Maven

> 🛠️ **Note:** Before running locally, make sure to create a `.env` file in the root directory with the following properties:
> ```env
> JWT_SECRET_KEY="YOUR_JWT_SECRET"
> PBK_SECRET_KEY="YOUR_PBK_SECRET"
> ```
```bash
git clone https://github.com/arunjimmanuel/spring-boot-demo-app.git
cd spring-boot-demo-app
mvn clean install
java -jar target/spring-boot-demo-app.jar
```

### 🐳 Run with Docker
```bash
docker build -t job-tracker-backend .
docker run -p 8080:8080 job-tracker-backend
```

> The app will be available at `http://localhost:8080`

### 🔐 JWT Login/Signup Endpoints
- `POST /auth/signup` – Register a new user
- `POST /auth/login` – Get JWT token
- Use JWT token in `Authorization: Bearer <token>` for all protected routes

## 🧠 Sample Features
- `GET /jobs` – Get list of all jobs (supports Solr-based full-text search)
- `POST /jobs` – Add a new job (admin only)
- `PUT /jobs/{id}` – Update job status
- `GET /users/me` – Authenticated user profile

## ☁️ Deployment

✅ This backend service is tested for:
- **Local Docker environment**
- **Kubernetes on Ubuntu**
- **AWS Cloud (EKS, EC2)** via [K8s manifests](https://github.com/arunjimmanuel/angular-spring-kubernetes-deployment)

## 📈 SEO Keywords for Discovery
`Spring Boot 3.x`, `Java backend developer`, `RESTful API`, `JWT Auth`, `MongoDB NoSQL`, `Apache Solr Search`, `Kubernetes microservices`, `Spring Security`, `Dockerized Java app`, `cloud-native job portal`, `Spring Boot developer portfolio`, `secure spring boot application`

## 👤 Author
**Arun Jaya Immanuel**  
🔗 [LinkedIn](https://www.linkedin.com/in/arunimmanuel/)  
💼 Backend Developer | Spring Boot | Kubernetes | Java | Cloud | Open Source

---
If you find this project helpful, feel free to ⭐ it and explore the full-stack ecosystem!
