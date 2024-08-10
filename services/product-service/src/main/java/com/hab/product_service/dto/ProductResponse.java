package com.hab.product_service.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ProductResponse(
        String id,
        String name,
        String skuCode,
        String description,
        BigDecimal price,
        Integer quantity
) {
}
