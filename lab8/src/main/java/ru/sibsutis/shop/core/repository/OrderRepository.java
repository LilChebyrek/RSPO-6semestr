package ru.sibsutis.shop.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.sibsutis.shop.core.model.entity.Order;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {
    List<Order> findAllByCustomerId(Long customerId);
}