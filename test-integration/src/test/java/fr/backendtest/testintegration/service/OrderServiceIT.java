package fr.backendtest.testintegration.service;

import fr.backendtest.testintegration.dto.OrderRequest;
import fr.backendtest.testintegration.dto.OrderResponse;
import fr.backendtest.testintegration.entity.Item;
import fr.backendtest.testintegration.entity.Order;
import fr.backendtest.testintegration.exception.CustomerNotFoundException;
import fr.backendtest.testintegration.exception.ItemNotFoundException;
import fr.backendtest.testintegration.repository.OrderRepository;
import fr.backendtest.testintegration.service.impl.OrderServiceImpl;
import fr.backendtest.testutils.testcontainer.AbstractIntegrationTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Sql(scripts = {
    "classpath:sql/clean_db.sql",
    "classpath:sql/customer.sql",
    "classpath:sql/item.sql"
})
public class OrderServiceIT extends AbstractIntegrationTest {

    @Inject
    private OrderRepository orderRepository;

    @Inject
    private OrderServiceImpl orderService;

    @BeforeEach
    void setUp() {
        orderRepository.deleteAll();
    }

    @Test
    void shouldPlaceOrderWithDiscount() {
        // GIVEN
        Long customerId = 1L;
        List<Long> itemIds = List.of(101L, 102L);
        OrderRequest request = new OrderRequest(customerId, itemIds);

        // WHEN
        OrderResponse response = orderService.placeOrder(request);

        // THEN
        assertEquals(1L, response.orderId());
        Optional<Order> savedOrder = orderRepository.findById(1L);
        assertTrue(savedOrder.isPresent());
        Order order = savedOrder.get();
        assertEquals(customerId, order.getCustomer().getId());
        assertIterableEquals(itemIds, order.getItems().stream().map(Item::getId).toList());
        assertEquals(945.0, order.getTotal()); // 10% discount
    }

    @Test
    void shouldPlaceOrderWithoutDiscount() {
        // GIVEN
        Long customerId = 2L;
        List<Long> itemIds = List.of(101L, 102L);
        OrderRequest request = new OrderRequest(customerId, itemIds);

        // WHEN
        OrderResponse response = orderService.placeOrder(request);

        // THEN
        assertEquals(1L, response.orderId());
        Optional<Order> savedOrder = orderRepository.findById(1L);
        assertTrue(savedOrder.isPresent());
        Order order = savedOrder.get();
        assertEquals(customerId, order.getCustomer().getId());
        assertIterableEquals(itemIds, order.getItems().stream().map(Item::getId).toList());
        assertEquals(1050, order.getTotal());
    }

    @Test
    void shouldThrowExceptionWhenItemsAreMissing() {
        // GIVEN
        Long customerId = 1L;
        List<Long> itemIds = List.of(101L, 202L);
        OrderRequest request = new OrderRequest(customerId, itemIds);

        // WHEN && THEN
        ItemNotFoundException exception = assertThrows(
            ItemNotFoundException.class,
            () -> orderService.placeOrder(request)
        );
        assertTrue(exception.getMessage().contains("Missing item(s): [202]"));
    }

    @Test
    void shouldThrowCustomerNotFoundException() {
        // GIVEN
        Long unknownCustomerId = 999L;
        List<Long> itemIds = List.of(1L);
        OrderRequest request = new OrderRequest(unknownCustomerId, itemIds);

        // WHEN && THEN
        CustomerNotFoundException exception = assertThrows(
            CustomerNotFoundException.class,
            () -> orderService.placeOrder(request)
        );
        assertTrue(exception.getMessage().contains("Customer not found"));
    }
}
