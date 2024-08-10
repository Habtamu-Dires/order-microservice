package com.hab.order_service.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record OrderRequest(
        String skuCode,
        @Positive(message = "Price should be positive")
        BigDecimal price,
        @Positive(message = "Quantity should be positive")
        Integer quantity,
        @Valid
        @NotNull
        UserDetails userDetails
) {
}
