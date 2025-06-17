package fr.backendtest.goldentest.service;

import fr.backendtest.goldentest.dto.OrderRequest;
import fr.backendtest.goldentest.dto.OrderResponse;

public interface OrderService {
    OrderResponse placeOrder(OrderRequest request);
}
