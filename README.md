# TestWheel Microservices Stack

This project implements a microservices architecture with **Spring Boot**, **Spring Security**, **Spring Cloud Gateway**, **Eureka Service Registry**, **Postgres**, **Kafka**, and supporting tools. It also includes a front‑end rendered with Thymeleaf templates for authentication and dashboard flows.

---
Start Here http://localhost:28765/wheel/
   `/` route to test-wheel service  


## 📘 Services

- **service-registry**  
  Eureka server for service discovery (port `8761`).

- **api-gateway**  
  Spring Cloud Gateway routing requests to microservices (port `8765`).  mapped to `55432` host).
  Provides a `/home` endpoint for quick health checks.

- **test-wheel (spring-security)**  
  Authentication and security service (port `9098`).
    - Handles login (`/login`), signup (`/signup`), dashboard (`/dashboard`), and logout (`/perform_logout`).
    - Integrates JWT tokens(not implemented only class), cookie management, and OAuth2 login.
    - Uses Thymeleaf templates (`tw-login.html`, `tw-signup.html`, `dashboard.html`, etc.).

- **test-service**  
  Test management microservice (port `9097`).
    - Endpoints for creating and listing tests.
    - Connected to Postgres and Kafka.

- **postgres**  
  PostgreSQL database (port `5432` mapped to `55432` host).  
  Initializes schema via `init-postgres.sql`.

- **zookeeper**  
  Required for Kafka broker coordination (port `2181`).

- **kafka**  
  Kafka broker (port `9092` mapped to `29092` host).  
  Used for messaging between services.

- **pgadmin**  
  Web UI for Postgres administration (port `8081`).  
  Default login: `admin@admin.com` / `admin`.

---

## ⚙️ Startup Order

1. **service-registry** → must be healthy before gateway starts.
2. **api-gateway** → depends on registry.
3. **postgres** → required for test-wheel and test-service.
4. **zookeeper** → required before Kafka.
5. **kafka** → required before test-wheel and test-service.
6. **test-wheel** → depends on registry, postgres, kafka.
7. **test-service** → depends on registry, postgres, kafka.
8. **pgadmin** → depends on postgres.

---

## 🚀 Usage

### Build images
```bash
docker compose build
