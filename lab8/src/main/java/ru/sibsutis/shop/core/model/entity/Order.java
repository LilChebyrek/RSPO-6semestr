package ru.sibsutis.shop.core.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.sibsutis.shop.core.model.entity.payment.Payment;
import ru.sibsutis.shop.core.model.entity.user.Customer;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "\"order\"")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime date;
    private String status;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderDetail> orderDetails;

    @OneToOne(cascade = CascadeType.ALL)
    private Payment payment;
}