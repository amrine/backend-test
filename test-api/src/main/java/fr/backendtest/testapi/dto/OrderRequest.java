package fr.backendtest.testapi.dto;

import java.util.List;

public record OrderRequest(Long customerId, List<Long> itemIds) { }