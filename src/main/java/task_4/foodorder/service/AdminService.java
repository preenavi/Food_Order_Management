package task_4.foodorder.service;

<<<<<<< HEAD
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import task_4.foodorder.dto.*;
import task_4.foodorder.entity.Order;
import task_4.foodorder.entity.OrderStatus;
import task_4.foodorder.entity.Role;
import task_4.foodorder.entity.User;
import task_4.foodorder.repository.UserRepository;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for administrative operations
 * Manages users, orders, restaurants, and system statistics
 */
@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;

    /**
     * Get all users with optional role filter
     */
    public List<UserManagementDTO> getAllUsers(Role roleFilter) {
        List<User> users = roleFilter != null
                ? userRepository.findByRole(roleFilter)
                : userRepository.findAll();
        
        return users.stream()
                .map(this::convertUserToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get user by ID
     */
    public UserManagementDTO getUserById(Long userId) {
        return userRepository.findById(userId)
                .map(this::convertUserToDTO)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
    }

    /**
     * Deactivate user account
     */
    public UserManagementDTO deactivateUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
        user.setIsActive(false);
        userRepository.save(user);
        return convertUserToDTO(user);
    }

    /**
     * Activate user account
     */
    public UserManagementDTO activateUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
        user.setIsActive(true);
        userRepository.save(user);
        return convertUserToDTO(user);
    }

    /**
     * Change user role (e.g., USER to DELIVERY)
     */
    public UserManagementDTO changeUserRole(Long userId, Role newRole) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
        
        // Prevent changing admin role
        if (user.getRole() == Role.ADMIN) {
            throw new RuntimeException("Cannot change role of admin user");
        }
        
        user.setRole(newRole);
        userRepository.save(user);
        return convertUserToDTO(user);
    }

    /**
     * Get all delivery personnel
     */
    public List<UserManagementDTO> getAllDeliveryPersonnel() {
        return getAllUsers(Role.DELIVERY);
    }

    /**
     * Get all regular users
     */
    public List<UserManagementDTO> getAllRegularUsers() {
        return getAllUsers(Role.USER);
    }

    /**
     * Delete user (soft delete - deactivate)
     */
    public void deleteUser(Long userId) {
        deactivateUser(userId);
    }

    /**
     * Get dashboard statistics
     */
    public AdminDashboardDTO getDashboardStats() {
        AdminDashboardDTO stats = new AdminDashboardDTO();
        
        // Count users by role
        stats.setTotalUsers(userRepository.count());
        
        // These would require repositories for Orders, Restaurants, Deliveries
        // For now, setting placeholder values
        stats.setTotalOrders(0L);
        stats.setTotalRestaurants(0L);
        stats.setActiveDeliveries(0L);
        stats.setTotalRevenue(0.0);
        stats.setPendingOrders(0L);
        stats.setCompletedOrders(0L);
        
        return stats;
    }

    /**
     * Convert User entity to UserManagementDTO
     */
    private UserManagementDTO convertUserToDTO(User user) {
        return new UserManagementDTO(
=======
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import task_4.foodorder.dto.OrderResponse;
import task_4.foodorder.dto.RestaurantResponse;
import task_4.foodorder.dto.UserResponse;
import task_4.foodorder.entity.Order;
import task_4.foodorder.entity.OrderStatus;
import task_4.foodorder.entity.Restaurant;
import task_4.foodorder.entity.Role;
import task_4.foodorder.entity.User;
import task_4.foodorder.repository.OrderRepository;
import task_4.foodorder.repository.RestaurantRepository;
import task_4.foodorder.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    // User management
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::mapToUserResponse)
                .collect(Collectors.toList());
    }

    public UserResponse updateUserRole(Long userId, Role role) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setRole(role);
        userRepository.save(user);
        return mapToUserResponse(user);
    }

    public UserResponse toggleUserStatus(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setIsActive(!user.getIsActive());
        userRepository.save(user);
        return mapToUserResponse(user);
    }

    // Order management
    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(this::mapToOrderResponse)
                .collect(Collectors.toList());
    }

    public OrderResponse updateOrderStatus(Long orderId, OrderStatus status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus(status);
        orderRepository.save(order);
        return mapToOrderResponse(order);
    }

    // Restaurant management
    public List<RestaurantResponse> getAllRestaurants() {
        return restaurantRepository.findAll().stream()
                .map(this::mapToRestaurantResponse)
                .collect(Collectors.toList());
    }

    public RestaurantResponse createRestaurant(String name, String address) {
        Restaurant restaurant = new Restaurant();
        restaurant.setName(name);
        restaurant.setAddress(address);
        restaurantRepository.save(restaurant);
        return mapToRestaurantResponse(restaurant);
    }

    public void deleteRestaurant(Long restaurantId) {
        restaurantRepository.deleteById(restaurantId);
    }

    // Mapping methods
    private UserResponse mapToUserResponse(User user) {
        return new UserResponse(
>>>>>>> 41f234519e1ba613ba8166f22963eb788cbea599
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole(),
                user.getIsActive(),
<<<<<<< HEAD
                user.getCreatedAt().toString(),
                user.getUpdatedAt().toString()
        );
    }
}
=======
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }

    private OrderResponse mapToOrderResponse(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getUser().getId(),
                order.getUser().getName(),
                order.getStatus(),
                order.getTotalAmount(),
                order.getCreatedAt()
        );
    }

    private RestaurantResponse mapToRestaurantResponse(Restaurant restaurant) {
        return new RestaurantResponse(
                restaurant.getId(),
                restaurant.getName(),
                restaurant.getAddress()
        );
    }
}
>>>>>>> 41f234519e1ba613ba8166f22963eb788cbea599
