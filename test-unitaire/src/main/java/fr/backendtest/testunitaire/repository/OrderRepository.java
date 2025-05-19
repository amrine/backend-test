package fr.backendtest.testunitaire.repository;


import fr.backendtest.testunitaire.entity.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderRepository {

    private List<Order> orders = new ArrayList<>();


    public Order save(Order order) {
        order.setId(orders.size() + 1L);
        orders.add(order);
        return order;
    }
}
