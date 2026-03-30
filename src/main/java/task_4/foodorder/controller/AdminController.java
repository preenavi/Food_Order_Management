package task_4.foodorder.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import task_4.foodorder.dto.ApiResponse;
import task_4.foodorder.dto.OrderResponse;
import task_4.foodorder.dto.RestaurantResponse;
import task_4.foodorder.dto.UserResponse;
import task_4.foodorder.entity.OrderStatus;
import task_4.foodorder.entity.Role;
import task_4.foodorder.service.AdminService;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private AdminService adminService;

    // User management endpoints
    @GetMapping("/users")
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUsers() {
        List<UserResponse> users = adminService.getAllUsers();
        return ResponseEntity.ok(new ApiResponse<>(true, "Users retrieved successfully", users));
    }

    @PutMapping("/users/{userId}/role")
    public ResponseEntity<ApiResponse<UserResponse>> updateUserRole(@PathVariable Long userId, @RequestParam Role role) {
        UserResponse user = adminService.updateUserRole(userId, role);
        return ResponseEntity.ok(new ApiResponse<>(true, "User role updated successfully", user));
    }

    @PutMapping("/users/{userId}/status")
    public ResponseEntity<ApiResponse<UserResponse>> toggleUserStatus(@PathVariable Long userId) {
        UserResponse user = adminService.toggleUserStatus(userId);
        return ResponseEntity.ok(new ApiResponse<>(true, "User status updated successfully", user));
    }

    // Order management endpoints
    @GetMapping("/orders")
    public ResponseEntity<ApiResponse<List<OrderResponse>>> getAllOrders() {
        List<OrderResponse> orders = adminService.getAllOrders();
        return ResponseEntity.ok(new ApiResponse<>(true, "Orders retrieved successfully", orders));
    }

    @PutMapping("/orders/{orderId}/status")
    public ResponseEntity<ApiResponse<OrderResponse>> updateOrderStatus(@PathVariable Long orderId, @RequestParam OrderStatus status) {
        OrderResponse order = adminService.updateOrderStatus(orderId, status);
        return ResponseEntity.ok(new ApiResponse<>(true, "Order status updated successfully", order));
    }

    // Restaurant management endpoints
    @GetMapping("/restaurants")
    public ResponseEntity<ApiResponse<List<RestaurantResponse>>> getAllRestaurants() {
        List<RestaurantResponse> restaurants = adminService.getAllRestaurants();
        return ResponseEntity.ok(new ApiResponse<>(true, "Restaurants retrieved successfully", restaurants));
    }

    @PostMapping("/restaurants")
    public ResponseEntity<ApiResponse<RestaurantResponse>> createRestaurant(@RequestParam String name, @RequestParam String address) {
        RestaurantResponse restaurant = adminService.createRestaurant(name, address);
        return ResponseEntity.ok(new ApiResponse<>(true, "Restaurant created successfully", restaurant));
    }

    @DeleteMapping("/restaurants/{restaurantId}")
    public ResponseEntity<ApiResponse<Object>> deleteRestaurant(@PathVariable Long restaurantId) {
        adminService.deleteRestaurant(restaurantId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Restaurant deleted successfully", null));
    }
}