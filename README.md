# SecureRBAC - Authentication, Authorization, and Role-Based Access Control

## Project Overview

SecureRBAC is a Spring Boot application demonstrating the implementation of Authentication, Authorization, and Role-Based Access Control (RBAC). It uses Spring boot, Spring Security 6, JWT (JSON Web Tokens), and MySQL to create a secure backend for user management. Users are authenticated and authorized based on their roles, ensuring secure access to APIs.



1. **Authentication**: Secure login using JWT tokens.
2. **Authorization**: Access control based on user roles (e.g., `USER`, `ADMIN`).
3. **Role-Based Access Control (RBAC)**: Specific endpoints are accessible only to users with the required roles.
4. **JWT Token Integration**: Tokens are generated upon successful login and must be included in API requests for protected resources.

## Technology Stack

- **Spring Boot**
- **Spring Security 6**
- **JWT**
- **MySQL**
- **Hibernate/JPA**


## Setup Instructions

1. **Clone the Repository**:

```bash
git clone https://github.com/your-repo/SecureRBAC.git
cd SecureRBAC
```


2. **Configure the Database**: Update application.properties with your MySQL database credentials.

```bash
spring.datasource.url=jdbc:mysql://localhost:3306/secure_rbac
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```
3. **Build and Run the spring boot Application**:

4. **Access the APIs via Postman or any HTTP client.**:

## API Endpoints

### 1. Authentication APIs
 ### Register
- **Endpoint**:POST /api/auth/register
- **Description**:Register a new user.
- **Request Body**:

```bash
{
  "username": "john_doe",
  "password": "password123",
}
```
- **Response**:

```bash
{
  "message": "User registered successfully"
}
```
 ### Login
- **Endpoint**:POST /api/auth/login
- **Description**: Login with valid credentials to obtain a JWT token.
- **Request Body**:

```bash
{
  "username": "john_doe",
  "password": "password123",
}
```
- **Response**:

```bash
{
  "message": "User Login successfully"
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

### 2. Role-Based APIs
 ### Get User Details (Role: USER)
- **Endpoint**:GET /api/auth/me
- **Description**: Fetch details of the currently authenticated user.
- **Headers**:

```bash
{
  "Authorization": "Bearer <your_jwt_token>"
}
```
- **Response**:

```bash
{
  "id": 1,
  "username": "john_doe",
  "roles": ["USER"]
}
```
### Get All Users (Role: ADMIN, MODERATOR)
- **Endpoint**:GET /api/admin/users
- **Description**: Retrieve a list of all users. Accessible by ADMIN and MODERATOR.
- **Headers**:

```bash
{
  "Authorization": "Bearer <your_jwt_token>"
}
```
- **Response**:

```bash
[
  {
    "id": 1,
    "username": "john_doe",
    "roles": ["USER"]
  },
  {
    "id": 2,
    "username": "jane_admin",
    "roles": ["ADMIN"]
  }
{
    "id": 3,
    "username": "jane_Moderator",
    "roles": ["MODERATOR"]
  }
]
```
### Delete a User (Role: MODERATOR, ADMIN)
- **Endpoint**:DELETE /api/moderator/{id}
- **Description**: Delete a user by their ID. Accessible by MODERATOR and ADMIN
- **Headers**:

```bash
{
  "Authorization": "Bearer <your_jwt_token>"
}
```
- **Response**:

```bash
{
  "message": "User with ID 3 has been deleted successfully"
}
```
### Approve Content (Role: MODERATOR)
- **Endpoint**:POST /api/moderator/content/approve
- **Description**: Delete a user by their ID. Accessible by MODERATOR and ADMIN.
- **Headers**:

```bash
{
  "Authorization": "Bearer <your_jwt_token>"
}
```
- **Request Body**:

```bash
{
  "contentId": 101,
  "approvalStatus": true
}
```
- **Response**:

```bash
{
  "message": "Content with ID 101 has been approved."
} 
```

### Get Content Details (Role: USER, MODERATOR, ADMIN)
- **Endpoint**: GET /api/content/{id}
- **Description**: Retrieve details of a specific content item.
- **Headers**:

```bash
{
  "Authorization": "Bearer <your_jwt_token>"
}
```
- **Request Body**:

```bash
{
  "id": 101,
  "title": "Sample Content",
  "description": "This is a sample content description.",
  "status": "Approved"
}
```
- **Response**:

```bash
{
  "message": "Content with ID 101 has been approved."
}
```
 ### Admin-Only Resource
- **Endpoint**:GET /api/admin/dashboard
- **Description**: Access restricted to ADMIN role.
- **Headers**:

```bash
{
  "Authorization": "Bearer <your_jwt_token>"
}
```
- **Response**:

```bash
{
  "message": "Welcome, Admin"
}
```
### Error Handling
- **401 Unauthorized**: Token is missing or invalid.
- **403 Forbidden**: User does not have the required role to access the resource.



