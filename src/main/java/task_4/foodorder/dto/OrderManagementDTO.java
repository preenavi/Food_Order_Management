package task_4.foodorder.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import task_4.foodorder.entity.OrderStatus;

/**
 * DTO for managing orders (admin operations)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderManagementDTO {
    private Long id;
    private Long userId;
    private String userName;
    private OrderStatus status;
    private Double totalAmount;
    private String createdAt;
    private String updatedAt;
}
