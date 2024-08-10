package com.hab.product_service.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ProductRequest(
    @NotNull(message = "Product name should not be null")
    String name,
    String skuCode,
    String description,
    @NotNull(message = "Price should not be null")
    @Positive(message = "The price should be positive")
    BigDecimal price
) {
}
