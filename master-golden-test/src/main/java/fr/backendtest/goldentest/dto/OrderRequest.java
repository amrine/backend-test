package fr.backendtest.goldentest.dto;

import java.util.List;

public record OrderRequest(Long customerId, List<Long> itemIds) { }