package task_4.foodorder.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import task_4.foodorder.dto.ApiResponse;
import task_4.foodorder.dto.FoodRequest;
import task_4.foodorder.dto.FoodResponse;
import task_4.foodorder.entity.FoodType;
import task_4.foodorder.service.FoodService;

import java.util.List;

@RestController
@RequestMapping("/api/food")
public class FoodController {

    @Autowired
    private FoodService foodService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','STAFF')")
    public ResponseEntity<ApiResponse<FoodResponse>> createFood(@Valid @RequestBody FoodRequest request) {
        FoodResponse response = foodService.createFood(request);
        return ResponseEntity.ok(new ApiResponse<>(true, "Food created", response));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','STAFF')")
    public ResponseEntity<ApiResponse<FoodResponse>> updateFood(@PathVariable Long id,
                                                                @Valid @RequestBody FoodRequest request) {
        FoodResponse response = foodService.updateFood(id, request);
        return ResponseEntity.ok(new ApiResponse<>(true, "Food updated", response));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','STAFF')")
    public ResponseEntity<ApiResponse<Object>> deleteFood(@PathVariable Long id) {
        foodService.softDeleteFood(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Food deleted", null));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<FoodResponse>>> listFoods(@RequestParam(required = false) FoodType type,
                                                                     @RequestParam(required = false) Boolean availability) {
        List<FoodResponse> response = foodService.listFoods(type, availability);
        return ResponseEntity.ok(new ApiResponse<>(true, "Food list", response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<FoodResponse>> getFood(@PathVariable Long id) {
        FoodResponse response = foodService.getFood(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Food details", response));
    }
}
