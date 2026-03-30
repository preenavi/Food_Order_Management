# Food Order Management - Authentication & Login Module

## 📋 Overview

This document describes the comprehensive Authentication and Login module for the Food Order Management System. The module provides secure user registration, login, JWT token-based authentication, and access control features.

---

## 🏗️ Architecture

### Components

1. **Entity Layer** (`entity/`)
   - `User.java` - User entity with role-based access control

2. **Security Layer** (`security/`)
   - `JwtUtil.java` - JWT token generation and validation
   - `JwtFilter.java` - Request filter for JWT validation
   - `CustomUserDetails.java` - Spring Security UserDetails implementation
   - `CustomUserDetailsService.java` - Custom UserDetailsService

3. **Controller Layer** (`controller/`)
   - `AuthController.java` - REST endpoints for authentication

4. **Service Layer** (`service/`)
   - `AuthService.java` - Business logic for authentication

5. **Configuration** (`config/`)
   - `SecurityConfig.java` - Spring Security configuration

6. **DTO Layer** (`dto/`)
   - `AuthRequest.java` - Login request payload
   - `AuthResponse.java` - Authentication response with tokens
   - `RegisterRequest.java` - User registration payload
   - `ApiResponse.java` - Standard API response wrapper

---

## 🔐 Security Features

### 1. **Password Encryption**
   - Uses BCrypt with strength 12
   - Passwords are hashed before storage
   - Comparison done using `PasswordEncoder.matches()`

### 2. **JWT Tokens**
   - **Access Token**: 1 hour expiration
   - **Refresh Token**: 7 days expiration
   - HS256 signature algorithm
   - Subject contains user email

### 3. **Request Filtering**
   - JWT filter validates tokens on each request
   - Sets authentication context for authorized access
   - Clears security context for invalid tokens

### 4. **Session Management**
   - Stateless authentication (no session storage)
   - Suitable for microservices and mobile apps

### 5. **Exception Handling**
   - Global exception handler with consistent error responses
   - Detailed error codes for client-side handling

---

## 📡 API Endpoints

### Base URL: `/api/auth`

#### 1. **Register New User**
```
POST /api/auth/register
Content-Type: application/json

Request Body:
{
  "name": "John Doe",
  "email": "john@example.com",
  "password": "password123",
  "confirmPassword": "password123"
}

Success Response (201):
{
  "success": true,
  "message": "User registered successfully",
  "data": {
    "token": "eyJhbGc...",
    "refreshToken": "eyJhbGc...",
    "userId": 1,
    "email": "john@example.com",
    "name": "John Doe",
    "role": "USER"
  },
  "timestamp": "2026-03-30T10:30:00"
}

Error Response (400):
{
  "success": false,
  "message": "Email already registered",
  "errorCode": "REGISTRATION_ERROR",
  "timestamp": "2026-03-30T10:30:00"
}
```

#### 2. **Login User**
```
POST /api/auth/login
Content-Type: application/json

Request Body:
{
  "email": "john@example.com",
  "password": "password123"
}

Success Response (200):
{
  "success": true,
  "message": "Login successful",
  "data": {
    "token": "eyJhbGc...",
    "refreshToken": "eyJhbGc...",
    "userId": 1,
    "email": "john@example.com",
    "name": "John Doe",
    "role": "USER"
  }
}

Error Response (401):
{
  "success": false,
  "message": "Invalid credentials",
  "errorCode": "LOGIN_ERROR"
}
```

#### 3. **Refresh Access Token**
```
POST /api/auth/refresh
Authorization: Bearer <refresh_token>

Success Response (200):
{
  "success": true,
  "message": "Token refreshed successfully",
  "data": {
    "token": "eyJhbGc...",
    "refreshToken": "eyJhbGc...",
    "userId": 1,
    "email": "john@example.com",
    "name": "John Doe",
    "role": "USER"
  }
}
```

#### 4. **Get Current User**
```
GET /api/auth/me
Authorization: Bearer <access_token>

Success Response (200):
{
  "success": true,
  "message": "User retrieved successfully",
  "data": {
    "id": 1,
    "name": "John Doe",
    "email": "john@example.com",
    "role": "USER",
    "isActive": true,
    "createdAt": "2026-03-30T10:20:00",
    "updatedAt": "2026-03-30T10:20:00"
  }
}
```

#### 5. **Logout**
```
POST /api/auth/logout
Authorization: Bearer <access_token>

Success Response (200):
{
  "success": true,
  "message": "Logged out successfully"
}
```

---

## 🛠️ Configuration

### JWT Settings (`application.properties`)
```properties
jwt.secret=mysecretkeymysecretkeymysecretkey123456789
jwt.expiration=3600000          # 1 hour in milliseconds
jwt.refresh-expiration=604800000 # 7 days in milliseconds
```

### Database Requirements
```sql
CREATE DATABASE foodorder_db;

-- User table structure (auto-created by JPA):
CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL,
    is_active BOOLEAN DEFAULT true,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
```

---

## 📝 Request/Response Models

### AuthRequest
```java
{
  "email": "user@example.com",
  "password": "password123"
}
```

### RegisterRequest
```java
{
  "name": "John Doe",
  "email": "john@example.com",
  "password": "password123",
  "confirmPassword": "password123"
}
```

### AuthResponse
```java
{
  "token": "string",
  "refreshToken": "string",
  "message": "string",
  "success": boolean,
  "userId": long,
  "email": "string",
  "name": "string",
  "role": "string"
}
```

### ApiResponse
```java
{
  "success": boolean,
  "message": "string",
  "data": "object",
  "timestamp": "LocalDateTime",
  "errorCode": "string"
}
```

---

## 🔄 Authentication Flow

### Registration Flow
```
1. POST /api/auth/register
2. Validate input (password match, strength, email uniqueness)
3. Encode password using BCrypt
4. Create User entity
5. Save to database
6. Generate JWT access token
7. Generate refresh token
8. Return tokens with user info
```

### Login Flow
```
1. POST /api/auth/login
2. Find user by email
3. Verify user is active
4. Compare password using PasswordEncoder.matches()
5. If valid: Generate JWT access token and refresh token
6. Return tokens with user info
7. If invalid: Return 401 Unauthorized
```

### Request Authorization Flow
```
1. Client sends request with Authorization header: "Bearer <token>"
2. JwtFilter intercepts request
3. Extract token from Authorization header
4. Validate token using JwtUtil.isTokenValid()
5. Extract email from token
6. Load user details using CustomUserDetailsService
7. Create authentication token
8. Set in SecurityContextHolder
9. Allow request to proceed
10. If token invalid: Clear security context, allow request (403 on endpoint)
```

### Token Refresh Flow
```
1. POST /api/auth/refresh with Authorization: Bearer <refresh_token>
2. Validate refresh token
3. Extract email from refresh token
4. Generate new access token (using same email)
5. Return new token with existing refresh token
```

---

## 🔒 Role-Based Access Control

### User Roles
- **USER**: Default role for regular users (can access order endpoints)
- **ADMIN**: Admin role (can access admin endpoints)
- **DELIVERY_PARTNER**: Delivery partner role (can update delivery status)
- **RESTAURANT**: Restaurant role (can manage menu items)

### Access Control Rules
```
Public Endpoints:
- POST /api/auth/register
- POST /api/auth/login

Protected Endpoints (require valid JWT):
- GET /api/auth/me
- POST /api/auth/logout
- POST /api/auth/refresh
- All protected business endpoints
```

---

## 🧪 Testing the Module

### Using cURL

#### Register User
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Doe",
    "email": "john@example.com",
    "password": "password123",
    "confirmPassword": "password123"
  }'
```

#### Login User
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "john@example.com",
    "password": "password123"
  }'
```

#### Access Protected Endpoint
```bash
curl -X GET http://localhost:8080/api/auth/me \
  -H "Authorization: Bearer eyJhbGc..."
```

### Using Postman

1. **Register**: POST request to `http://localhost:8080/api/auth/register`
2. **Login**: POST request to `http://localhost:8080/api/auth/login`
3. **Add Authorization Tab**:
   - Type: Bearer Token
   - Token: `<paste_token_from_response>`
4. **Access Protected Endpoints**: Copy token and use in Authorization

---

## 📋 File Structure

```
src/main/java/task_4/foodorder/
├── config/
│   └── SecurityConfig.java
├── controller/
│   └── AuthController.java
├── dto/
│   ├── ApiResponse.java
│   ├── AuthRequest.java
│   ├── AuthResponse.java
│   └── RegisterRequest.java
├── entity/
│   ├── User.java
│   ├── Role.java
│   └── ... other entities
├── exception/
│   └── GlobalExceptionHandler.java
├── repository/
│   └── UserRepository.java
├── security/
│   ├── CustomUserDetails.java
│   ├── CustomUserDetailsService.java
│   ├── JwtFilter.java
│   └── JwtUtil.java
└── service/
    └── AuthService.java
```

---

## ✅ Validation Rules

### Registration Validation
- Name: Required, non-empty
- Email: Required, unique, valid email format
- Password: Minimum 6 characters
- Confirm Password: Must match password
- No duplicate email in database

### Login Validation
- Email: Required, must exist in database
- Password: Required, must match stored (encoded) password
- User must be active (isActive = true)

---

## 🚀 Usage in Other Endpoints

### Making Protected Requests

```java
@GetMapping("/some-protected-endpoint")
public ResponseEntity<?> protectedEndpoint() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String userEmail = auth.getName();
    // Your logic here
}
```

### Adding Role-Based Access

```java
@PreAuthorize("hasRole('ADMIN')")
@GetMapping("/admin-only")
public ResponseEntity<?> adminOnly() {
    // Only admins can access this
}

@PreAuthorize("hasRole('USER')")
@GetMapping("/user-access")
public ResponseEntity<?> userAccess() {
    // Only users or higher roles can access
}
```

---

## 🐛 Troubleshooting

### Issue: JWT Token Validation Fails
- **Check**: JWT secret matches in configuration
- **Check**: Token hasn't expired
- **Check**: Token format is correct (Bearer <token>)

### Issue: User Not Found
- **Check**: User email is registered
- **Check**: Email in request matches registered email
- **Check**: Check database for user record

### Issue: Password Mismatch
- **Check**: Password is stored as BCrypt hash
- **Check**: Using PasswordEncoder.matches() for comparison
- **Check**: Correct password provided during login

### Issue: Access Denied on Protected Endpoint
- **Check**: Token is valid and not expired
- **Check**: User role has permission for endpoint
- **Check**: Authorization header is properly formatted

---

## 🔐 Security Best Practices

✅ **Implemented:**
- Password hashing using BCrypt
- JWT token expiration
- Stateless authentication
- CSRF protection disabled for API
- Secure password validation
- Exception handling without sensitive info

⚠️ **Recommendations:**
- Use HTTPS in production
- Implement rate limiting on login endpoint
- Add email verification for registration
- Implement account lockout after failed attempts
- Use HttpOnly cookies for token storage (client-side)
- Implement refresh token rotation
- Add API key management for service-to-service communication
- Regular security audits and dependency updates

---

## 📦 Dependencies

```xml
<!-- Spring Boot Security -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>

<!-- JWT -->
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-api</artifactId>
    <version>0.11.5</version>
</dependency>

<!-- Spring Data JPA -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>

<!-- MySQL -->
<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
</dependency>
```

---

## 📞 Support & Questions

For questions or issues with the authentication module, please refer to:
- JWT Documentation: https://jwt.io/
- Spring Security: https://spring.io/projects/spring-security
- JJWT Library: https://github.com/jwtk/jjwt

---

## 📄 Version History

| Version | Date | Changes |
|---------|------|---------|
| 1.0 | 2026-03-30 | Initial comprehensive auth module with JWT, BCrypt, and role-based access control |

---

**Last Updated**: March 30, 2026  
**Status**: ✅ Production Ready
