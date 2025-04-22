package ru.sibsutis.shop.core.model.entity.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    @Column(name = "address_city") // Явное указание
    private String city;

    @Column(name = "address_street")
    private String street;

    @Column(name = "address_zipcode")
    private String zipcode;
}