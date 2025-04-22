package ru.sibsutis.shop.core.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sibsutis.shop.api.dto.OrderRequestDto;
import ru.sibsutis.shop.api.dto.OrderResponseDto;
import ru.sibsutis.shop.api.mapper.OrderMapper;
import ru.sibsutis.shop.core.model.OrderSearchCriteria;
import ru.sibsutis.shop.core.model.entity.Item;
import ru.sibsutis.shop.core.model.entity.OrderDetail;
import ru.sibsutis.shop.core.model.entity.user.Customer;
import ru.sibsutis.shop.core.model.entity.Order;
import ru.sibsutis.shop.core.model.entity.payment.Payment;
import ru.sibsutis.shop.core.repository.*;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final PaymentRepository paymentRepository;
    private final ItemRepository itemRepository;
    private final OrderMapper orderMapper;

    @Transactional
    public OrderResponseDto createOrder(Long userId, OrderRequestDto orderRequestDto) {
        // Найдем заказчика
        Customer customer = customerRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));

        // Найдем платеж
        Payment payment = paymentRepository.findById(orderRequestDto.getPaymentId())
                .orElseThrow(() -> new EntityNotFoundException("Payment not found"));

        // Преобразуем DTO -> Order
        Order order = orderMapper.toEntity(orderRequestDto);
        order.setCustomer(customer);
        order.setPayment(payment);

        // Установим связь для orderDetails вручную (нужно, чтобы Hibernate сохранил их правильно)
        for (OrderDetail detail : order.getOrderDetails()) {
            detail.setOrder(order);

            Long itemId = detail.getItem().getId();
            Item item = itemRepository.findById(itemId)
                    .orElseThrow(() -> new EntityNotFoundException("Item with id " + itemId + " not found"));

            detail.setItem(item);
        }

        Order savedOrder = orderRepository.save(order);
        return orderMapper.toDto(savedOrder);
    }

    public List<OrderResponseDto> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orderMapper.toDto(orders);
    }

    public List<OrderResponseDto> getAllOrders(Long customerId) {
        List<Order> orders = orderRepository.findAllByCustomerId(customerId);
        return orderMapper.toDto(orders);
    }

    public List<Order> findOrdersByCriteria(OrderSearchCriteria criteria) {
        return orderRepository.findAll((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (criteria.getAddress() != null) {

                // Сначала соединяем Order с Customer
                Join<Order, Customer> customerJoin = root.join("customer");

                // Доступ к полям Address напрямую (они в той же таблице)
                predicates.add(cb.equal(customerJoin.get("address").get("city"), criteria.getAddress().getCity()));
                predicates.add(cb.equal(customerJoin.get("address").get("street"), criteria.getAddress().getStreet()));
            }

            if (criteria.getFromDate() != null && criteria.getToDate() != null) {
                predicates.add(cb.between(root.get("date"), criteria.getFromDate(), criteria.getToDate()));
            }

            if (criteria.getPayment() != null) {
                Join<Order, Payment> paymentJoin = root.join("payment");
                predicates.add(cb.equal(paymentJoin.type(), criteria.getPayment()));
            }

            if (criteria.getCustomerName() != null) {
                Join<Order, Customer> customerJoin = root.join("customer");
                predicates.add(cb.equal(customerJoin.get("name"), criteria.getCustomerName()));
            }

            if (criteria.getPaymentStatus() != null) {
                Join<Order, Payment> paymentJoin = root.join("payment");
                predicates.add(cb.equal(paymentJoin.get("status"), criteria.getPaymentStatus()));
            }

            if (criteria.getOrderStatus() != null) {
                predicates.add(cb.equal(root.get("status"), criteria.getOrderStatus()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        });
    }
}