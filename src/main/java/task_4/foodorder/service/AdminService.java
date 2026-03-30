package task_4.foodorder.service;

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
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole(),
                user.getIsActive(),
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