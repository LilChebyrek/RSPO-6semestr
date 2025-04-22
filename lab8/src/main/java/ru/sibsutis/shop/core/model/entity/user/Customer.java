package ru.sibsutis.shop.core.model.entity.user;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import ru.sibsutis.shop.core.model.entity.Order;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id")
public class Customer extends User {

    private String name;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Order> orders;
}
