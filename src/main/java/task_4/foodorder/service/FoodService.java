package task_4.foodorder.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import task_4.foodorder.dto.FoodRequest;
import task_4.foodorder.dto.FoodResponse;
import task_4.foodorder.entity.Food;
import task_4.foodorder.entity.FoodType;
import task_4.foodorder.exception.ResourceNotFoundException;
import task_4.foodorder.repository.FoodRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FoodService {

    @Autowired
    private FoodRepository foodRepository;

    public FoodResponse createFood(FoodRequest request) {
        Food food = new Food();
        applyRequest(food, request);
        Food saved = foodRepository.save(food);
        return toResponse(saved);
    }

    public FoodResponse updateFood(Long id, FoodRequest request) {
        Food food = foodRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Food not found"));
        applyRequest(food, request);
        Food saved = foodRepository.save(food);
        return toResponse(saved);
    }

    public void softDeleteFood(Long id) {
        Food food = foodRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Food not found"));
        food.setDeleted(true);
        foodRepository.save(food);
    }

    public FoodResponse getFood(Long id) {
        Food food = foodRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Food not found"));
        return toResponse(food);
    }

    public List<FoodResponse> listFoods(FoodType type, Boolean availability) {
        List<Food> foods;
        if (type != null && availability != null) {
            foods = foodRepository.findAllByIsDeletedFalseAndTypeAndAvailability(type, availability);
        } else if (type != null) {
            foods = foodRepository.findAllByIsDeletedFalseAndType(type);
        } else if (availability != null) {
            foods = foodRepository.findAllByIsDeletedFalseAndAvailability(availability);
        } else {
            foods = foodRepository.findAllByIsDeletedFalse();
        }

        return foods.stream().map(this::toResponse).collect(Collectors.toList());
    }

    private void applyRequest(Food food, FoodRequest request) {
        food.setName(request.getName());
        food.setType(request.getType());
        food.setPrice(request.getPrice());
        food.setAvailability(Boolean.TRUE.equals(request.getAvailability()));
    }

    private FoodResponse toResponse(Food food) {
        FoodResponse response = new FoodResponse();
        response.setId(food.getId());
        response.setName(food.getName());
        response.setType(food.getType());
        response.setPrice(food.getPrice());
        response.setAvailability(food.isAvailability());
        response.setDeleted(food.isDeleted());
        response.setCreatedAt(food.getCreatedAt());
        return response;
    }
}
