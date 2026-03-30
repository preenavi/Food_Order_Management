package task_4.foodorder.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import task_4.foodorder.entity.Role;

/**
 * DTO for managing users (admin operations)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserManagementDTO {
    private Long id;
    private String name;
    private String email;
    private Role role;
    private Boolean isActive;
    private String createdAt;
    private String updatedAt;
}
