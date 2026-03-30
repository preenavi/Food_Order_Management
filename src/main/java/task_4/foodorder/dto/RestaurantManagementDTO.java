package task_4.foodorder.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for managing restaurants (admin operations)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantManagementDTO {
    private Long id;
    private String name;
    private String location;
    private String phoneNumber;
    private Boolean isActive;
    private String createdAt;
}
