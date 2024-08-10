package com.hab.inventory_service.inventory;

import lombok.Builder;

@Builder
public record InventoryResponse(
        String skuCode,
        Integer quantity
) {
}
