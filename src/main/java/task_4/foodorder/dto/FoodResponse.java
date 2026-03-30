package task_4.foodorder.dto;

import lombok.Data;
import task_4.foodorder.entity.FoodType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class FoodResponse {

    private Long id;
    private String name;
    private FoodType type;
    private BigDecimal price;
    private boolean availability;
    private boolean isDeleted;
    private LocalDateTime createdAt;
}
