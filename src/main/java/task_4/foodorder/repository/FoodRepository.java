package task_4.foodorder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import task_4.foodorder.entity.Food;
import task_4.foodorder.entity.FoodType;

import java.util.List;
import java.util.Optional;

public interface FoodRepository extends JpaRepository<Food, Long> {

    Optional<Food> findByIdAndIsDeletedFalse(Long id);

    List<Food> findAllByIsDeletedFalse();

    List<Food> findAllByIsDeletedFalseAndType(FoodType type);

    List<Food> findAllByIsDeletedFalseAndAvailability(boolean availability);

    List<Food> findAllByIsDeletedFalseAndTypeAndAvailability(FoodType type, boolean availability);
}
