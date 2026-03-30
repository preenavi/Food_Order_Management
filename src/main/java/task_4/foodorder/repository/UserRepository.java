package task_4.foodorder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import task_4.foodorder.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}