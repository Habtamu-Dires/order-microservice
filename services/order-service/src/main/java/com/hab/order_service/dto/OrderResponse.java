package com.hab.order_service.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record OrderResponse (
        Long id,
        String orderNumber,
        String skuCode,
        BigDecimal price,
        Integer quantity
){
}
