package task_4.foodorder.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 🔥 ONE ORDER → ONE DELIVERY
    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;

    // 🔥 DELIVERY PERSON (USER with DELIVERY role)
    @ManyToOne
    @JoinColumn(name = "delivery_person_id")
    private User deliveryPerson;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;
}