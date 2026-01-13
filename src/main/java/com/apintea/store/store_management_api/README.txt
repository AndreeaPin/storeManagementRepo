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
| apin | Password1 | ADMIN | USER |
| john  | Password2  | USER  |

## Endpoints
- POST `/api/products` (ADMIN)
- GET `/api/products/{id}` (ADMIN, USER)
- PUT `/api/products/{id}/price` (ADMIN)
