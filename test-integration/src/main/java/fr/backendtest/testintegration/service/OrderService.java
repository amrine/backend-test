package fr.backendtest.testintegration.service;

import fr.backendtest.testintegration.dto.OrderRequest;
import fr.backendtest.testintegration.dto.OrderResponse;

public interface OrderService {
    OrderResponse placeOrder(OrderRequest request);
}
