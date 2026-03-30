package task_4.foodorder.service;

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
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole(),
                user.getIsActive(),
                user.getCreatedAt().toString(),
                user.getUpdatedAt().toString()
        );
    }
}
