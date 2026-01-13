# Store Management API

Backend-only REST API for managing store products.

## Used Tech Stack
- Java
- Spring Boot
- Maven
- Spring Security (Basic Auth)
- JPA + H2
- JUnit + Mockito

## Authentication
Two in-memory users are configured:

- **apin**
    - Password: `Password1`
    - Roles: `ADMIN`, `USER`
    - Can create, update, delete products and also read them

- **john**
    - Password: `Password2`
    - Roles: `USER`
    - Can only read products


## REST Endpoints
- POST `/api/products` (ADMIN)
- GET `/api/products/{id}` (ADMIN, USER)
- PUT `/api/products/{id}/price` (ADMIN)
- DELETE `/api/products/{id}` (ADMIN)