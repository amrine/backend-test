package fr.backendtest.goldentest.dto;

import java.util.List;

public record OrderResponse(Long id, Double total, Long customerId, List<Long> itemIds) {
}
