# 📌 ADMIN API EXAMPLES

## Base URL
```
http://localhost:8080/api/admin
```

---

## 🔐 Authentication
All endpoints require a valid JWT token in the Authorization header:
```
Authorization: Bearer <JWT_TOKEN>
```

**How to get JWT token:**
1. Register user: `POST /api/auth/register`
2. Login: `POST /api/auth/login`
3. Copy the `accessToken` from response
4. Use it in all admin endpoints

---

## 📊 User Management APIs

### 1️⃣ Get All Users
```bash
curl -X GET http://localhost:8080/api/admin/users \
  -H "Authorization: Bearer <JWT_TOKEN>" \
  -H "Content-Type: application/json"
```

**Query Parameters (Optional):**
- `role` - Filter by role (USER, ADMIN, DELIVERY)

**Request:**
```
GET /api/admin/users?role=USER
```

**Response (200 OK):**
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
    },
    {
      "id": 2,
      "name": "Jane Smith",
      "email": "jane@example.com",
      "role": "USER",
      "isActive": false,
      "createdAt": "2024-01-16T11:20:00",
      "updatedAt": "2024-01-21T14:30:00"
    }
  ],
  "timestamp": "2024-01-22T10:15:30"
}
```

---

### 2️⃣ Get User by ID
```bash
curl -X GET http://localhost:8080/api/admin/users/1 \
  -H "Authorization: Bearer <JWT_TOKEN>" \
  -H "Content-Type: application/json"
```

**Response (200 OK):**
```json
{
  "success": true,
  "message": "User retrieved successfully",
  "data": {
    "id": 1,
    "name": "John Doe",
    "email": "john@example.com",
    "role": "USER",
    "isActive": true,
    "createdAt": "2024-01-15T10:30:00",
    "updatedAt": "2024-01-20T15:45:00"
  }
}
```

**Response (404 Not Found):**
```json
{
  "success": false,
  "message": "User not found",
  "error": "User not found with ID: 999"
}
```

---

### 3️⃣ Get All Regular Users
```bash
curl -X GET http://localhost:8080/api/admin/users/role/regular \
  -H "Authorization: Bearer <JWT_TOKEN>" \
  -H "Content-Type: application/json"
```

**Response (200 OK):**
```json
{
  "success": true,
  "message": "Regular users retrieved successfully",
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

---

### 4️⃣ Get All Delivery Personnel
```bash
curl -X GET http://localhost:8080/api/admin/users/role/delivery \
  -H "Authorization: Bearer <JWT_TOKEN>" \
  -H "Content-Type: application/json"
```

**Response (200 OK):**
```json
{
  "success": true,
  "message": "Delivery personnel retrieved successfully",
  "data": [
    {
      "id": 3,
      "name": "Mike Johnson",
      "email": "mike@example.com",
      "role": "DELIVERY",
      "isActive": true,
      "createdAt": "2024-01-17T09:15:00",
      "updatedAt": "2024-01-22T16:45:00"
    }
  ]
}
```

---

## 🎯 User Action APIs

### 5️⃣ Activate User Account
```bash
curl -X PUT http://localhost:8080/api/admin/users/2/activate \
  -H "Authorization: Bearer <JWT_TOKEN>" \
  -H "Content-Type: application/json"
```

**Response (200 OK):**
```json
{
  "success": true,
  "message": "User activated successfully",
  "data": {
    "id": 2,
    "name": "Jane Smith",
    "email": "jane@example.com",
    "role": "USER",
    "isActive": true,
    "createdAt": "2024-01-16T11:20:00",
    "updatedAt": "2024-01-22T10:30:00"
  }
}
```

---

### 6️⃣ Deactivate User Account
```bash
curl -X PUT http://localhost:8080/api/admin/users/2/deactivate \
  -H "Authorization: Bearer <JWT_TOKEN>" \
  -H "Content-Type: application/json"
```

**Response (200 OK):**
```json
{
  "success": true,
  "message": "User deactivated successfully",
  "data": {
    "id": 2,
    "name": "Jane Smith",
    "email": "jane@example.com",
    "role": "USER",
    "isActive": false,
    "createdAt": "2024-01-16T11:20:00",
    "updatedAt": "2024-01-22T10:35:00"
  }
}
```

---

### 7️⃣ Change User Role
```bash
curl -X PUT "http://localhost:8080/api/admin/users/1/role?newRole=DELIVERY" \
  -H "Authorization: Bearer <JWT_TOKEN>" \
  -H "Content-Type: application/json"
```

**Query Parameter:**
- `newRole` - Target role (USER, DELIVERY) *cannot change to ADMIN*

**Response (200 OK):**
```json
{
  "success": true,
  "message": "User role changed successfully",
  "data": {
    "id": 1,
    "name": "John Doe",
    "email": "john@example.com",
    "role": "DELIVERY",
    "isActive": true,
    "createdAt": "2024-01-15T10:30:00",
    "updatedAt": "2024-01-22T10:40:00"
  }
}
```

**Response (400 Bad Request - Cannot change admin role):**
```json
{
  "success": false,
  "message": "Error changing user role",
  "error": "Cannot change role of admin user"
}
```

---

### 8️⃣ Delete User (Soft Delete)
```bash
curl -X DELETE http://localhost:8080/api/admin/users/2 \
  -H "Authorization: Bearer <JWT_TOKEN>" \
  -H "Content-Type: application/json"
```

**Response (200 OK):**
```json
{
  "success": true,
  "message": "User deleted successfully",
  "data": null
}
```

---

## 📈 Dashboard APIs

### 9️⃣ Get Dashboard Statistics
```bash
curl -X GET http://localhost:8080/api/admin/dashboard/stats \
  -H "Authorization: Bearer <JWT_TOKEN>" \
  -H "Content-Type: application/json"
```

**Response (200 OK):**
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

### 🔟 Health Check
```bash
curl -X GET http://localhost:8080/api/admin/health \
  -H "Authorization: Bearer <JWT_TOKEN>" \
  -H "Content-Type: application/json"
```

**Response (200 OK):**
```json
{
  "success": true,
  "message": "Admin API is running",
  "data": null
}
```

---

## ❌ Error Responses

### 401 Unauthorized (Invalid/Missing Token)
```json
{
  "success": false,
  "message": "Unauthorized",
  "error": "JWT token is invalid or expired"
}
```

### 403 Forbidden (Non-Admin User)
```json
{
  "success": false,
  "message": "Access Denied",
  "error": "You don't have admin privileges"
}
```

### 404 Not Found
```json
{
  "success": false,
  "message": "User not found",
  "error": "User not found with ID: 999"
}
```

### 500 Internal Server Error
```json
{
  "success": false,
  "message": "Error performing operation",
  "error": "Internal server error details"
}
```

---

## 🧪 Testing with Postman

1. **Create a new collection**: Admin APIs
2. **Set Authorization header in collection**:
   - Type: Bearer Token
   - Token: `<paste_jwt_token_here>`
3. **Create requests for each endpoint**

### Postman Environment Variables
```json
{
  "base_url": "http://localhost:8080",
  "api_path": "/api/admin",
  "jwt_token": "<your_jwt_token>"
}
```

---

## 🔄 Complete Admin Workflow

1. **Register User**
   ```
   POST /api/auth/register
   Body: { "name": "Admin User", "email": "admin@example.com", "password": "..." }
   ```

2. **Update Database to make user ADMIN**
   ```sql
   UPDATE users SET role = 'ADMIN' WHERE email = 'admin@example.com';
   ```

3. **Login**
   ```
   POST /api/auth/login
   Body: { "email": "admin@example.com", "password": "..." }
   Response: { "accessToken": "eyJhbGc..." }
   ```

4. **Use Token for Admin Operations**
   ```
   GET /api/admin/users
   Header: Authorization: Bearer eyJhbGc...
   ```

5. **Manage Users**
   - Activate/Deactivate accounts
   - Change roles
   - View statistics
   - Delete users (soft)

---

## 📝 Notes

- All timestamps are in ISO 8601 format
- JWT tokens expire after 1 hour
- Refresh tokens available for 7 days
- Soft deletes preserve user data (deactivation)
- Admin role cannot be changed for security
- CORS enabled but restrict in production

