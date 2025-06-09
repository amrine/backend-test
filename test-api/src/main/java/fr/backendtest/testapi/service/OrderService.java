package fr.backendtest.testapi.service;

import fr.backendtest.testapi.dto.OrderRequest;
import fr.backendtest.testapi.dto.OrderResponse;

public interface OrderService {
    OrderResponse placeOrder(OrderRequest request);
}
