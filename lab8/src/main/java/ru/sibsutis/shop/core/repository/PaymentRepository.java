package ru.sibsutis.shop.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.sibsutis.shop.core.model.entity.payment.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long>, JpaSpecificationExecutor<Payment> {
}