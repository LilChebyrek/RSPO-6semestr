package ru.sibsutis.shop.core.model.entity.payment;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "credit_payment")
@PrimaryKeyJoinColumn(name = "payment_id", referencedColumnName = "id")
@DiscriminatorValue("CREDIT")
public class Credit extends Payment {
    private String number;
    private String type;
    private LocalDateTime expDate;
}