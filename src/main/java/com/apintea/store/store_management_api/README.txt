# Store Management API

Backend-only REST API for managing store products.

## Used Tech Stack
- Java
- Spring Boot
- Maven
- Spring Security (Basic Auth)
- JPA + H2
- JUnit

## Authentication
| User  | Password | Role  |
|------|----------|-------|
| admin | admin123 | ADMIN |
| user  | user123  | USER  |

## Endpoints
- POST `/api/products` (ADMIN)
- GET `/api/products/{id}` (ADMIN, USER)
- PUT `/api/products/{id}/price` (ADMIN)
