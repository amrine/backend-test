package fr.backendtest.testunitaire.service;

import fr.backendtest.testunitaire.dto.OrderRequest;
import fr.backendtest.testunitaire.dto.OrderResponse;
import fr.backendtest.testunitaire.entity.Customer;
import fr.backendtest.testunitaire.entity.Item;
import fr.backendtest.testunitaire.entity.Order;
import fr.backendtest.testunitaire.exception.CustomerNotFoundException;
import fr.backendtest.testunitaire.exception.ItemNotFoundException;
import fr.backendtest.testunitaire.repository.CustomerRepository;
import fr.backendtest.testunitaire.repository.ItemRepository;
import fr.backendtest.testunitaire.repository.OrderRepository;
import fr.backendtest.testunitaire.serice.impl.OrderServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    void shouldPlaceOrderWithDiscount() {
        // GIVEN
        Long customerId = 1L;
        List<Long> itemIds = List.of(101L, 102L);
        OrderRequest request = new OrderRequest(customerId, itemIds);

        Customer customer = new Customer(customerId, "Alice", true);
        List<Item> items = List.of(
                new Item(101L, "Laptop", 1000.0),
                new Item(102L, "Mouse", 50.0)
        );

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        when(itemRepository.findAllById(itemIds)).thenReturn(items);
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> {
            Order saved = invocation.getArgument(0);
            saved.setId(10L);
            return saved;
        });

        // WHEN
        OrderResponse response = orderService.placeOrder(request);

        // THEN
        assertEquals(10L, response.orderId());
        // Capture to check total and customer
        ArgumentCaptor<Order> captor = ArgumentCaptor.forClass(Order.class);
        verify(orderRepository).save(captor.capture());
        Order savedOrder = captor.getValue();

        assertEquals(customer, savedOrder.getCustomer());
        assertEquals(2, savedOrder.getItems().size());
        assertEquals(945.0, savedOrder.getTotal()); // 10% discount
    }

    @Test
    void shouldPlaceOrderWithoutDiscount() {
        // GIVEN
        Long customerId = 1L;
        List<Long> itemIds = List.of(101L, 102L);
        OrderRequest request = new OrderRequest(customerId, itemIds);

        Customer customer = new Customer(customerId, "Alice", false);
        List<Item> items = List.of(
                new Item(101L, "Laptop", 1000.0),
                new Item(102L, "Mouse", 50.0)
        );

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        when(itemRepository.findAllById(itemIds)).thenReturn(items);
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> {
            Order saved = invocation.getArgument(0);
            saved.setId(10L);
            return saved;
        });

        // WHEN
        OrderResponse response = orderService.placeOrder(request);

        // THEN
        assertEquals(10L, response.orderId());
        // Capture to check total and customer
        ArgumentCaptor<Order> captor = ArgumentCaptor.forClass(Order.class);
        verify(orderRepository).save(captor.capture());
        Order savedOrder = captor.getValue();

        assertEquals(customer, savedOrder.getCustomer());
        assertEquals(2, savedOrder.getItems().size());
        assertEquals(1050, savedOrder.getTotal()); // 10% discount
    }

    @Test
    void shouldThrowExceptionWhenItemsAreMissing() {
        // GIVEN
        Long customerId = 1L;
        List<Long> itemIds = List.of(201L, 202L);
        OrderRequest request = new OrderRequest(customerId, itemIds);

        Customer customer = new Customer(customerId, "Bob", false);

        // Simulate only one item returned
        List<Item> partialItems = List.of(new Item(201L, "Keyboard", 80.0));

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        when(itemRepository.findAllById(itemIds)).thenReturn(partialItems);

        // WHEN && THEN
        ItemNotFoundException exception = assertThrows(ItemNotFoundException.class, () ->
                orderService.placeOrder(request)
        );
        assertTrue(exception.getMessage().contains("Missing item(s): [202]"));
        verify(orderRepository, never()).save(any());
    }

    @Test
    void shouldThrowCustomerNotFoundException() {
        // GIVEN
        Long unknownCustomerId = 999L;
        List<Long> itemIds = List.of(1L);
        OrderRequest request = new OrderRequest(unknownCustomerId, itemIds);

        when(customerRepository.findById(unknownCustomerId)).thenReturn(Optional.empty());

        // WHEN && THEN
        CustomerNotFoundException exception = assertThrows(CustomerNotFoundException.class, () -> {
            orderService.placeOrder(request);
        });
        assertTrue(exception.getMessage().contains("Customer not found"));
        verifyNoInteractions(itemRepository, orderRepository);
    }
}
