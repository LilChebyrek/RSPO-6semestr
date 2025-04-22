package ru.sibsutis.shop.core.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.sibsutis.shop.core.model.converter.WeightConverter;
import ru.sibsutis.shop.core.model.measurement.Weight;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Convert(converter = WeightConverter.class)
    private Weight shippingWeight;

    private String description;
}
