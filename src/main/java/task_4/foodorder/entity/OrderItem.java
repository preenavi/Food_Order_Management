package task_4.foodorder.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 🔥 ORDER RELATION
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    // 🔥 MENU ITEM RELATION
    @ManyToOne
    @JoinColumn(name = "menu_item_id")
    private MenuItem menuItem;

    private int quantity;
}