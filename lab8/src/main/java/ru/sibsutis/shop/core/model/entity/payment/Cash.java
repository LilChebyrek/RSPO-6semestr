package ru.sibsutis.shop.core.model.entity.payment;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "cash_payment")
@PrimaryKeyJoinColumn(name = "payment_id", referencedColumnName = "id")
@DiscriminatorValue("CASH")
public class Cash extends Payment {
    private float cashTendered;
}
