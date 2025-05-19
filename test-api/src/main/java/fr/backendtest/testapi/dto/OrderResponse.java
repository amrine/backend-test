package fr.backendtest.testapi.dto;

import java.util.List;

public record OrderResponse(Long id, Double total, Long customerId, List<Long> itemIds) {
}
