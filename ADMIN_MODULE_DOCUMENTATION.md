# 📋 ADMIN MODULE - DOCUMENTATION

## ✅ Admin Module Implementation Complete

A comprehensive administrative management system has been successfully created for the Food Order Management System.

---

## 📦 Components Created

### 1. **DTOs (Data Transfer Objects)**

#### `UserManagementDTO.java`
- Transfers user data for admin operations
- Fields: id, name, email, role, isActive, createdAt, updatedAt
- Used for all user-related admin responses

#### `AdminDashboardDTO.java`
- Provides dashboard statistics
- Fields: totalUsers, totalOrders, totalRestaurants, activeDeliveries, totalRevenue, pendingOrders, completedOrders
- For visual admin dashboard

#### `RestaurantManagementDTO.java`
- Manages restaurant information in admin panel
- Fields: id, name, location, phoneNumber, isActive, createdAt

#### `OrderManagementDTO.java`
- Manages orders in admin panel
- Fields: id, userId, userName, status, totalAmount, createdAt, updatedAt

---

### 2. **Service Layer**

#### `AdminService.java`
Business logic for administrative operations:

**User Management Methods:**
- `getAllUsers(Role roleFilter)` - Get all users, optionally filtered by role
- `getUserById(Long userId)` - Get specific user by ID
- `deactivateUser(Long userId)` - Deactivate user account
- `activateUser(Long userId)` - Activate user account
- `changeUserRole(Long userId, Role newRole)` - Change user's role (USER/DELIVERY)
- `deleteUser(Long userId)` - Soft delete user
- `getAllDeliveryPersonnel()` - Get all delivery staff
- `getAllRegularUsers()` - Get all regular users
- `getDashboardStats()` - Get system statistics

---

### 3. **Controller Layer**

#### `AdminController.java`
REST API endpoints for admin operations:

**User Management Endpoints:**

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/admin/users` | Get all users (with optional role filter) |
| GET | `/api/admin/users/{userId}` | Get specific user |
| GET | `/api/admin/users/role/regular` | Get all regular users |
| GET | `/api/admin/users/role/delivery` | Get all delivery personnel |
| PUT | `/api/admin/users/{userId}/activate` | Activate user account |
| PUT | `/api/admin/users/{userId}/deactivate` | Deactivate user account |
| PUT | `/api/admin/users/{userId}/role` | Change user role |
| DELETE | `/api/admin/users/{userId}` | Delete user (soft delete) |

**Dashboard Endpoints:**

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/admin/dashboard/stats` | Get admin dashboard statistics |
| GET | `/api/admin/health` | Health check endpoint |

---

## 🔒 Security Features

- **@PreAuthorize("hasRole('ADMIN')")** - All endpoints require ADMIN role
- JWT token validation via existing JwtFilter
- CORS enabled for cross-origin requests
- Proper HTTP status codes and error handling
- Non-admin users cannot access admin endpoints

---

## 🗄️ Database

The admin module uses existing database tables:
- `users` table - For user data
- Future: `orders`, `restaurants`, `deliveries` tables for full integration

---

## 📝 API Examples

### 1. Get All Users
```
GET /api/admin/users
Authorization: Bearer <JWT_TOKEN>
```

Response:
```json
{
  "success": true,
  "message": "Users retrieved successfully",
  "data": [
    {
      "id": 1,
      "name": "John Doe",
      "email": "john@example.com",
      "role": "USER",
      "isActive": true,
      "createdAt": "2024-01-15T10:30:00",
      "updatedAt": "2024-01-20T15:45:00"
    }
  ]
}
```

### 2. Get All Delivery Personnel
```
GET /api/admin/users/role/delivery
Authorization: Bearer <JWT_TOKEN>
```

### 3. Deactivate User
```
PUT /api/admin/users/1/deactivate
Authorization: Bearer <JWT_TOKEN>
```

### 4. Change User Role
```
PUT /api/admin/users/2/role?newRole=DELIVERY
Authorization: Bearer <JWT_TOKEN>
```

### 5. Get Dashboard Statistics
```
GET /api/admin/dashboard/stats
Authorization: Bearer <JWT_TOKEN>
```

Response:
```json
{
  "success": true,
  "message": "Dashboard statistics retrieved successfully",
  "data": {
    "totalUsers": 150,
    "totalOrders": 450,
    "totalRestaurants": 25,
    "activeDeliveries": 12,
    "totalRevenue": 45000.00,
    "pendingOrders": 23,
    "completedOrders": 425
  }
}
```

---

## 🔄 Integration Points

### Existing Integration:
- ✅ Uses existing `UserRepository` for database operations
- ✅ Leverages existing `JwtFilter` for authentication
- ✅ Uses existing `Role` enum (USER, ADMIN, DELIVERY)
- ✅ Uses existing `ApiResponse` DTO for consistent responses

### Future Integration (To be implemented):
- 🔄 `OrderRepository` - For order management
- 🔄 `RestaurantRepository` - For restaurant management
- 🔄 `DeliveryRepository` - For delivery tracking
- 🔄 Admin dashboard frontend components

---

## 🛠️ Configuration

No additional configuration required. The admin module works with existing:
- Application properties
- Security configuration
- Database connection
- JWT configuration

---

## ✨ Features

✅ User management (activate/deactivate/delete)
✅ Role-based user filtering
✅ Dashboard statistics
✅ Role assignment for users
✅ Comprehensive error handling
✅ Swagger/OpenAPI documentation
✅ Secure endpoints (ADMIN role required)
✅ Consistent API responses

---

## 📚 Swagger Documentation

Access Swagger UI to test admin endpoints:
```
http://localhost:8080/swagger-ui.html
```

All admin endpoints are documented with:
- Operation summaries
- Parameter descriptions
- Response examples
- Security requirements

---

## 🚀 Usage Instructions

1. **Start the application**
   ```
   mvn spring-boot:run
   ```

2. **Register a new user** (via existing auth module)
   ```
   POST /api/auth/register
   ```

3. **Admin promotes self to ADMIN role** (manually in database or via admin endpoint)
   ```
   UPDATE users SET role = 'ADMIN' WHERE id = 1;
   ```

4. **Login and get JWT token**
   ```
   POST /api/auth/login
   ```

5. **Use JWT token to access admin endpoints**
   ```
   GET /api/admin/users
   Header: Authorization: Bearer <JWT_TOKEN>
   ```

---

## 📊 Repository Methods

The `AdminRepository` provides additional custom queries:
- `findByRole(Role role)` - Filter users by role
- `findByIsActiveTrue()` - Get all active users
- `findByIsActiveFalse()` - Get all inactive users
- `countByRole(Role role)` - Count users by role
- `findByRoleAndActiveStatus()` - Complex filtering
- `countNonAdminUsers()` - Count non-admin users

---

## 🔐 Security Considerations

1. ✅ Only ADMIN role can access admin endpoints
2. ✅ Cannot modify admin user roles
3. ✅ All operations are JWT-authenticated
4. ✅ Soft delete (deactivation) preserves data
5. ✅ CORS enabled but can be restricted in production

---

## ⚠️ Notes

- Admin user role cannot be changed by other admins (for safety)
- Deletions are soft deletes (users are deactivated, not removed)
- Future expansion planned for order and restaurant management
- Dashboard stats currently use placeholder values (integrate with OrderRepository, RestaurantRepository, etc.)

