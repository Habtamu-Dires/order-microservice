package com.hab.product_service.client;

public record InventoryResponse(
        String skuCode,
        Integer quantity
) {
}
