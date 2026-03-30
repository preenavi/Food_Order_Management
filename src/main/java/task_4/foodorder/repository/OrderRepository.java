package task_4.foodorder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import task_4.foodorder.entity.Order;
import task_4.foodorder.entity.OrderStatus;
import task_4.foodorder.entity.User;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUser(User user);

    List<Order> findByStatus(OrderStatus status);

    List<Order> findByUserAndStatus(User user, OrderStatus status);
}