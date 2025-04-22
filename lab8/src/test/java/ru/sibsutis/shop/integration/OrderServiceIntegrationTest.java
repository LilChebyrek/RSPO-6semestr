package ru.sibsutis.shop.integration;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.sibsutis.shop.core.model.entity.user.Address;
import ru.sibsutis.shop.core.model.OrderSearchCriteria;
import ru.sibsutis.shop.core.model.entity.user.Customer;
import ru.sibsutis.shop.core.model.entity.Order;
import ru.sibsutis.shop.core.model.entity.payment.Cash;
import ru.sibsutis.shop.core.model.entity.payment.PaymentStatus;
import ru.sibsutis.shop.core.repository.UserRepository;
import ru.sibsutis.shop.core.repository.OrderRepository;
import ru.sibsutis.shop.core.repository.PaymentRepository;
import ru.sibsutis.shop.core.service.OrderService;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Testcontainers
@ActiveProfiles("test")
class OrderServiceIntegrationTest {

    @Container
    static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:17")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    private Customer testCustomer;
    private Cash testCashPayment;
    private Order testOrder;

    private void assertOrdersEqual(Order expected, Order actual) {
        assertThat(actual.getId()).isEqualTo(expected.getId());
        assertThat(actual.getStatus()).isEqualTo(expected.getStatus());
        assertThat(actual.getDate()).isEqualToIgnoringNanos(expected.getDate());
        assertThat(actual.getCustomer().getId()).isEqualTo(expected.getCustomer().getId());
        assertThat(actual.getPayment().getId()).isEqualTo(expected.getPayment().getId());
    }

    @BeforeEach
    @Transactional
    void setUp() throws Exception {
        orderRepository.deleteAll();
        userRepository.deleteAll();
        paymentRepository.deleteAll();

        testCustomer = new Customer();
        testCustomer.setName("Test Customer");
        Address address = new Address();
        address.setCity("Новосибирск");
        address.setStreet("Кирова");
        testCustomer.setAddress(address);
        testCustomer = userRepository.save(testCustomer);

        testCashPayment = new Cash();
        testCashPayment.setAmount(100.50);
        testCashPayment.setStatus(PaymentStatus.COMPLETED);
        testCashPayment.setCashTendered(100.50f);
//        Не нужно, так как CascadeType.ALL
//        testCashPayment = paymentRepository.save(testCashPayment);

        testOrder = new Order();
        testOrder.setCustomer(testCustomer);
        testOrder.setPayment(testCashPayment);
        testOrder.setDate(LocalDateTime.now());
        testOrder.setStatus("COMPLETED");
        testOrder = orderRepository.save(testOrder);
    }

    @Test
    void shouldFindOrdersByAddress() {
        Address searchAddress = new Address();
        searchAddress.setCity("Новосибирск");
        searchAddress.setStreet("Кирова");

        OrderSearchCriteria criteria = new OrderSearchCriteria();
        criteria.setAddress(searchAddress);

        List<Order> result = orderService.findOrdersByCriteria(criteria);

        assertThat(result).hasSize(1);
        assertOrdersEqual(testOrder, result.getFirst());
    }

    @Test
    void shouldFindOrdersByPaymentType() {
        OrderSearchCriteria criteria = new OrderSearchCriteria();
        criteria.setPayment(Cash.class);

        List<Order> result = orderService.findOrdersByCriteria(criteria);

        assertThat(result).hasSize(1);
        assertOrdersEqual(testOrder, result.getFirst());
    }

    @Test
    void shouldFindOrdersByTimeInterval() {
        OrderSearchCriteria criteria = new OrderSearchCriteria();
        criteria.setFromDate(LocalDateTime.now().minusDays(1));
        criteria.setToDate(LocalDateTime.now().plusDays(1));

        List<Order> result = orderService.findOrdersByCriteria(criteria);

        assertThat(result).hasSize(1);
        assertOrdersEqual(testOrder, result.getFirst());
    }

    @Test
    void shouldFindOrdersByCustomerName() {
        OrderSearchCriteria criteria = new OrderSearchCriteria();
        criteria.setCustomerName("Test Customer");

        List<Order> result = orderService.findOrdersByCriteria(criteria);

        assertThat(result).hasSize(1);
        assertOrdersEqual(testOrder, result.getFirst());
    }

    @Test
    void shouldFindOrdersByPaymentStatus() {
        OrderSearchCriteria criteria = new OrderSearchCriteria();
        criteria.setPaymentStatus(PaymentStatus.COMPLETED);

        List<Order> result = orderService.findOrdersByCriteria(criteria);

        assertThat(result).hasSize(1);
        assertOrdersEqual(testOrder, result.getFirst());
    }

    @Test
    void shouldFindOrdersByOrderStatus() {
        OrderSearchCriteria criteria = new OrderSearchCriteria();
        criteria.setOrderStatus("COMPLETED");

        List<Order> result = orderService.findOrdersByCriteria(criteria);

        assertThat(result).hasSize(1);
        assertOrdersEqual(testOrder, result.getFirst());
    }

    @Test
    void shouldNotFindOrdersWithWrongCriteria() {
        OrderSearchCriteria criteria = new OrderSearchCriteria();
        criteria.setOrderStatus("CANCELLED");

        List<Order> result = orderService.findOrdersByCriteria(criteria);

        assertThat(result).isEmpty();
    }

    @Test
    void shouldCombineMultipleCriteria() {
        OrderSearchCriteria criteria = new OrderSearchCriteria();
        criteria.setCustomerName("Test Customer");
        criteria.setOrderStatus("COMPLETED");
        criteria.setPaymentStatus(PaymentStatus.COMPLETED);

        List<Order> result = orderService.findOrdersByCriteria(criteria);

        assertThat(result).hasSize(1);
        assertOrdersEqual(testOrder, result.getFirst());
    }
}