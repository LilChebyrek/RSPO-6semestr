package ru.sibsutis.shop.core.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.sibsutis.shop.core.model.converter.QuantityConverter;
import ru.sibsutis.shop.core.model.measurement.Quantity;

@Entity
@Getter
@Setter
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Convert(converter = QuantityConverter.class)
    private Quantity quantity;

    private String taxStatus;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;
}