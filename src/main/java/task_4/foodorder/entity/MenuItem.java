package task_4.foodorder.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class MenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private double price;

    // 🔥 RELATIONSHIP STARTS HERE
    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;
}