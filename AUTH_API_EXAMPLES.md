# Auth & Login Module - API Testing Examples

This file contains practical examples for testing the authentication and login module.

## ===== REGISTRATION EXAMPLE =====

### Using JavaScript/Fetch API
```javascript
async function register() {
  const response = await fetch('http://localhost:8080/api/auth/register', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({
      name: 'John Doe',
      email: 'john@example.com',
      password: 'password123',
      confirmPassword: 'password123'
    })
  });
  
  const data = await response.json();
  console.log('Registration Response:', data);
  
  if (data.success) {
    localStorage.setItem('accessToken', data.data.token);
    localStorage.setItem('refreshToken', data.data.refreshToken);
  }
}
```

### Using Python/Requests
```python
import requests
import json

def register_user():
    url = 'http://localhost:8080/api/auth/register'
    headers = {'Content-Type': 'application/json'}
    payload = {
        'name': 'John Doe',
        'email': 'john@example.com',
        'password': 'password123',
        'confirmPassword': 'password123'
    }
    
    response = requests.post(url, json=payload, headers=headers)
    print('Status Code:', response.status_code)
    print('Response:', response.json())
    
    if response.status_code == 201:
        token = response.json()['data']['token']
        return token
```

## ===== LOGIN EXAMPLE =====

### Using JavaScript/Fetch API
```javascript
async function login() {
  const response = await fetch('http://localhost:8080/api/auth/login', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({
      email: 'john@example.com',
      password: 'password123'
    })
  });
  
  const data = await response.json();
  console.log('Login Response:', data);
  
  if (data.success) {
    // Store tokens
    localStorage.setItem('accessToken', data.data.token);
    localStorage.setItem('refreshToken', data.data.refreshToken);
    console.log('Login successful! Token stored.');
  } else {
    console.error('Login failed:', data.message);
  }
}
```

### Using cURL
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "john@example.com",
    "password": "password123"
  }' | jq .
```

## ===== GET CURRENT USER EXAMPLE =====

### Using JavaScript/Fetch API
```javascript
async function getCurrentUser() {
  const token = localStorage.getItem('accessToken');
  
  const response = await fetch('http://localhost:8080/api/auth/me', {
    method: 'GET',
    headers: {
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    }
  });
  
  const data = await response.json();
  
  if (data.success) {
    console.log('Current User:', data.data);
  } else {
    console.error('Failed to get user info:', data.message);
  }
}
```

### Using cURL
```bash
curl -X GET http://localhost:8080/api/auth/me \
  -H "Authorization: Bearer YOUR_TOKEN_HERE" \
  -H "Content-Type: application/json" | jq .
```

## ===== REFRESH TOKEN EXAMPLE =====

### Using JavaScript/Fetch API
```javascript
async function refreshToken() {
  const refreshToken = localStorage.getItem('refreshToken');
  
  const response = await fetch('http://localhost:8080/api/auth/refresh', {
    method: 'POST',
    headers: {
      'Authorization': `Bearer ${refreshToken}`,
      'Content-Type': 'application/json'
    }
  });
  
  const data = await response.json();
  
  if (data.success) {
    // Update token
    localStorage.setItem('accessToken', data.data.token);
    console.log('Token refreshed successfully');
  } else {
    console.error('Token refresh failed:', data.message);
  }
}
```

## ===== LOGOUT EXAMPLE =====

### Using JavaScript/Fetch API
```javascript
async function logout() {
  const token = localStorage.getItem('accessToken');
  
  const response = await fetch('http://localhost:8080/api/auth/logout', {
    method: 'POST',
    headers: {
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    }
  });
  
  const data = await response.json();
  
  if (data.success) {
    // Clear tokens
    localStorage.removeItem('accessToken');
    localStorage.removeItem('refreshToken');
    console.log('Logged out successfully');
    // Redirect to login page
  }
}
```

## ===== COMPLETE AUTHENTICATION FLOW =====

```javascript
class AuthService {
  constructor() {
    this.baseUrl = 'http://localhost:8080/api/auth';
  }

  async register(name, email, password, confirmPassword) {
    try {
      const response = await fetch(`${this.baseUrl}/register`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ name, email, password, confirmPassword })
      });
      
      const data = await response.json();
      if (data.success) {
        this.setTokens(data.data.token, data.data.refreshToken);
      }
      return data;
    } catch (error) {
      console.error('Registration error:', error);
      throw error;
    }
  }

  async login(email, password) {
    try {
      const response = await fetch(`${this.baseUrl}/login`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ email, password })
      });
      
      const data = await response.json();
      if (data.success) {
        this.setTokens(data.data.token, data.data.refreshToken);
      }
      return data;
    } catch (error) {
      console.error('Login error:', error);
      throw error;
    }
  }

  async getCurrentUser() {
    try {
      const token = this.getAccessToken();
      const response = await fetch(`${this.baseUrl}/me`, {
        method: 'GET',
        headers: {
          'Authorization': `Bearer ${token}`,
          'Content-Type': 'application/json'
        }
      });
      
      return await response.json();
    } catch (error) {
      console.error('Get user error:', error);
      throw error;
    }
  }

  async refreshToken() {
    try {
      const refreshToken = this.getRefreshToken();
      const response = await fetch(`${this.baseUrl}/refresh`, {
        method: 'POST',
        headers: {
          'Authorization': `Bearer ${refreshToken}`,
          'Content-Type': 'application/json'
        }
      });
      
      const data = await response.json();
      if (data.success) {
        this.setTokens(data.data.token, data.data.refreshToken);
      }
      return data;
    } catch (error) {
      console.error('Token refresh error:', error);
      throw error;
    }
  }

  async logout() {
    try {
      const token = this.getAccessToken();
      const response = await fetch(`${this.baseUrl}/logout`, {
        method: 'POST',
        headers: {
          'Authorization': `Bearer ${token}`,
          'Content-Type': 'application/json'
        }
      });
      
      if (response.ok) {
        this.clearTokens();
      }
      return await response.json();
    } catch (error) {
      console.error('Logout error:', error);
      this.clearTokens();
      throw error;
    }
  }

  setTokens(accessToken, refreshToken) {
    localStorage.setItem('accessToken', accessToken);
    localStorage.setItem('refreshToken', refreshToken);
  }

  getAccessToken() {
    return localStorage.getItem('accessToken');
  }

  getRefreshToken() {
    return localStorage.getItem('refreshToken');
  }

  clearTokens() {
    localStorage.removeItem('accessToken');
    localStorage.removeItem('refreshToken');
  }

  isAuthenticated() {
    return !!this.getAccessToken();
  }
}

// Usage
const auth = new AuthService();

// Register and login
await auth.register('John', 'john@example.com', 'pass123', 'pass123');
await auth.login('john@example.com', 'pass123');

// Get current user
const userResponse = await auth.getCurrentUser();
console.log(userResponse.data);

// Refresh token
await auth.refreshToken();

// Logout
await auth.logout();
```

## ===== ERROR HANDLING =====

```javascript
async function makeAuthenticatedRequest(url, options = {}) {
  let token = localStorage.getItem('accessToken');
  
  const config = {
    ...options,
    headers: {
      ...options.headers,
      'Authorization': `Bearer ${token}`
    }
  };

  let response = await fetch(url, config);

  // If token expired (401), try to refresh
  if (response.status === 401) {
    const refreshResult = await auth.refreshToken();
    
    if (refreshResult.success) {
      token = localStorage.getItem('accessToken');
      config.headers['Authorization'] = `Bearer ${token}`;
      response = await fetch(url, config);
    } else {
      // Refresh failed, logout and redirect to login
      auth.logout();
      window.location.href = '/login';
    }
  }

  return response;
}
```

## ===== POSTMAN TESTING =====

### Setup Postman Environment
1. Open Postman
2. Create new Environment
3. Add variables:
   - `baseUrl`: http://localhost:8080
   - `accessToken`: (will be set after login)
   - `refreshToken`: (will be set after login)

### Register Request
- Method: POST
- URL: {{baseUrl}}/api/auth/register
- Body (JSON):
```json
{
  "name": "John Doe",
  "email": "john@example.com",
  "password": "password123",
  "confirmPassword": "password123"
}
```
- Tests (to save token):
```javascript
var jsonData = pm.response.json();
pm.environment.set("accessToken", jsonData.data.token);
pm.environment.set("refreshToken", jsonData.data.refreshToken);
```

### Login Request
- Method: POST
- URL: {{baseUrl}}/api/auth/login
- Body (JSON):
```json
{
  "email": "john@example.com",
  "password": "password123"
}
```

### Get Current User
- Method: GET
- URL: {{baseUrl}}/api/auth/me
- Headers:
  - Authorization: Bearer {{accessToken}}

---

## ===== TESTING CHECKLIST =====

- [ ] Register new user successfully
- [ ] Try registering with duplicate email (should fail)
- [ ] Try registering with mismatched passwords (should fail)
- [ ] Try registering with short password (should fail)
- [ ] Login with correct credentials
- [ ] Login with incorrect password (should fail)
- [ ] Login with non-existent email (should fail)
- [ ] Access protected endpoint with valid token
- [ ] Access protected endpoint without token (should fail)
- [ ] Access protected endpoint with expired token (should fail)
- [ ] Refresh token successfully
- [ ] Refresh with invalid token (should fail)
- [ ] Get current user info
- [ ] Logout successfully
- [ ] Try accessing endpoint after logout (should fail)
