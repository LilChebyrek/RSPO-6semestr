package ru.sibsutis.shop.core.model;

import lombok.Data;
import ru.sibsutis.shop.core.model.entity.payment.Payment;
import ru.sibsutis.shop.core.model.entity.payment.PaymentStatus;
import ru.sibsutis.shop.core.model.entity.user.Address;

import java.time.LocalDateTime;

// Критерии для поиска
@Data
public class OrderSearchCriteria {
    private Address address;
    private LocalDateTime fromDate;
    private LocalDateTime toDate;
    private Class<? extends Payment> payment;
    private String customerName;
    private PaymentStatus paymentStatus;
    private String orderStatus;
}
