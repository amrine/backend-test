package fr.backendtest.testunitaire.service;

import fr.backendtest.testunitaire.dto.OrderRequest;
import fr.backendtest.testunitaire.dto.OrderResponse;

public interface OrderService {
    OrderResponse placeOrder(OrderRequest request);
}
