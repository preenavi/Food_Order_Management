# 🚀 Auth & Login Module - Quick Start Guide

## Prerequisites

- Java 21+
- Maven 3.6+
- MySQL 8.0+
- IDE (VS Code, IntelliJ, Eclipse)

## ⚡ Quick Setup (5 Minutes)

### Step 1: Database Setup

```sql
-- Create database
CREATE DATABASE foodorder_db;

-- Tables are auto-created by JPA (spring.jpa.hibernate.ddl-auto=update)
```

### Step 2: Update Database Configuration

Edit `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/foodorder_db
spring.datasource.username=root
spring.datasource.password=your_password
```

### Step 3: Start the Application

```bash
# Using Maven
mvn clean install
mvn spring-boot:run

# Using IDE
- Right-click FoodOrderApplication.java → Run As → Java Application
- Or use keyboard shortcut: Ctrl+Shift+F11 (Eclipse) or Shift+F10 (IntelliJ)
```

### Step 4: Test the Auth Module

```bash
# Register
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Test User",
    "email": "test@example.com",
    "password": "test123",
    "confirmPassword": "test123"
  }'

# Login
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "test@example.com",
    "password": "test123"
  }'
```

## 📁 Project Structure Overview

```
FoodOrder/
├── src/main/java/task_4/foodorder/
│   ├── FoodOrderApplication.java          # Main application entry point
│   ├── config/
│   │   └── SecurityConfig.java            # Spring Security configuration
│   ├── controller/
│   │   └── AuthController.java            # Auth REST endpoints
│   ├── dto/
│   │   ├── AuthRequest.java               # Login request
│   │   ├── AuthResponse.java              # Auth response with tokens
│   │   ├── RegisterRequest.java           # Registration request
│   │   └── ApiResponse.java               # Standard API response
│   ├── entity/
│   │   ├── User.java                      # User entity with role
│   │   ├── Role.java                      # Role enum
│   │   └── ... other entities
│   ├── exception/
│   │   └── GlobalExceptionHandler.java    # Global error handler
│   ├── repository/
│   │   └── UserRepository.java            # User database queries
│   ├── security/
│   │   ├── JwtUtil.java                   # JWT token handling
│   │   ├── JwtFilter.java                 # JWT validation filter
│   │   ├── CustomUserDetails.java         # Spring Security user details
│   │   └── CustomUserDetailsService.java  # User details service
│   └── service/
│       └── AuthService.java               # Auth business logic
├── src/main/resources/
│   └── application.properties             # Configuration
├── pom.xml                                # Maven dependencies
├── AUTH_MODULE_DOCUMENTATION.md           # Full documentation
├── AUTH_API_EXAMPLES.md                   # API testing examples
└── QUICK_START.md                         # This file
```

## 🔑 Key Features Implemented

✅ **User Registration**
- Email uniqueness validation
- Password strength validation
- Password encoding with BCrypt
- Automatic role assignment

✅ **User Login**
- Email and password validation
- User activation status check
- JWT token generation
- Refresh token support

✅ **JWT Authentication**
- Access tokens (1 hour expiration)
- Refresh tokens (7 days expiration)
- Token validation on each request
- Automatic token extraction from Authorization header

✅ **Security**
- Password hashing with BCrypt (strength 12)
- CSRF protection disabled for API
- Stateless session management
- Exception handling and error responses

✅ **Protected Endpoints**
- `/api/auth/me` - Get current user
- `/api/auth/refresh` - Refresh access token
- `/api/auth/logout` - Logout

✅ **Public Endpoints**
- `/api/auth/register` - Register new user
- `/api/auth/login` - Login user

## 🧪 Testing with Different Tools

### Option 1: Using cURL (Command Line)
```bash
# All examples in AUTH_API_EXAMPLES.md
```

### Option 2: Using Postman
1. Import API collection from AUTH_API_EXAMPLES.md
2. Set variables in environment
3. Test all endpoints

### Option 3: Using VS Code REST Client Extension
1. Create `requests.http` file
2. Use examples from AUTH_API_EXAMPLES.md
3. Send requests directly from editor

### Option 4: Using JavaScript/Frontend
```javascript
// Use examples from AUTH_API_EXAMPLES.md
// AuthService class provided for easy integration
```

## 🔐 Security Considerations

### Implemented ✅
- Password hashing using BCrypt
- JWT token expiration
- Secure token validation
- Exception handling without exposing sensitive info
- Role-based access control

### Recommended for Production ⚠️
- Use HTTPS for all endpoints
- Implement rate limiting on login endpoint
- Add email verification for registration
- Implement account lockout after failed attempts
- Add refresh token rotation
- Use environment variables for secrets
- Regular security audits
- Keep dependencies updated

## 📝 Configuration Options

### JWT Settings
```properties
jwt.secret=your-secret-key-here              # Min 32 chars
jwt.expiration=3600000                       # Access token expiration (ms)
jwt.refresh-expiration=604800000             # Refresh token expiration (ms)
```

### Database Settings
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/foodorder_db
spring.datasource.username=root
spring.datasource.password=password
spring.jpa.hibernate.ddl-auto=update
```

### Server Settings
```properties
server.port=8080
server.servlet.context-path=/
```

## 🐛 Common Issues & Solutions

### Issue: Database Connection Failed
**Solution**: 
- Ensure MySQL is running
- Check connection string in application.properties
- Verify database exists: `CREATE DATABASE foodorder_db;`

### Issue: JWT Token Validation Fails
**Solution**:
- Verify JWT secret in application.properties
- Check token hasn't expired
- Ensure Authorization header format: `Bearer <token>`

### Issue: User Not Found
**Solution**:
- Verify user exists in database
- Check email spelling
- Ensure database tables were created

### Issue: Password Mismatch on Login
**Solution**:
- Verify uppercase/lowercase sensitivity
- Check password wasn't changed
- Ensure BCrypt encoding is working

### Issue: Cannot Access Protected Endpoints
**Solution**:
- Send valid Bearer token in Authorization header
- Check token expiration time
- Verify user role has access to endpoint

## 📚 Documentation

- **AUTH_MODULE_DOCUMENTATION.md** - Complete technical documentation
- **AUTH_API_EXAMPLES.md** - API testing examples in multiple languages
- **QUICK_START.md** - This quick start guide

## 🎯 Next Steps

1. ✅ Setup database and start application
2. ✅ Test registration endpoint
3. ✅ Test login endpoint
4. ✅ Get access token and test protected endpoints
5. ✅ Implement additional business endpoints
6. ✅ Add role-based access control to business endpoints
7. ✅ Deploy to production with HTTPS

## 💡 Example: Adding Role-Based Protected Endpoint

```java
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody OrderRequest request) {
        // Only users can create orders
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<?> getAllOrders() {
        // Only admins can see all orders
    }
}
```

## 📞 Support

If you encounter issues:
1. Check AUTH_MODULE_DOCUMENTATION.md for detailed info
2. Review error messages in console
3. Verify database configuration
4. Check JWT token validity
5. Review REST API examples in AUTH_API_EXAMPLES.md

## 🎉 You're Ready!

Your Food Order Management system now has a complete, production-ready authentication and login module. 

Happy coding! 🚀
