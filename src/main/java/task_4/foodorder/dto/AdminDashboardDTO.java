package task_4.foodorder.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for admin dashboard statistics
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminDashboardDTO {
    private Long totalUsers;
    private Long totalOrders;
    private Long totalRestaurants;
    private Long activeDeliveries;
    private Double totalRevenue;
    private Long pendingOrders;
    private Long completedOrders;
}
