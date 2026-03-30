package task_4.foodorder.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import task_4.foodorder.entity.FoodType;

import java.math.BigDecimal;

@Data
public class FoodRequest {

    @NotBlank(message = "name is required")
    private String name;

    @NotNull(message = "type is required")
    private FoodType type;

    @NotNull(message = "price is required")
    @Positive(message = "price must be greater than 0")
    private BigDecimal price;

    @NotNull(message = "availability is required")
    private Boolean availability;
}
