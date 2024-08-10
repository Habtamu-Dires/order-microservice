package com.hab.inventory_service.inventory;

import org.springframework.stereotype.Service;

@Service
public class InventoryMapper {

    public InventoryResponse toInventoryResponse(ProductInventory inventory){
        return InventoryResponse.builder()
                .skuCode(inventory.getSkuCode())
                .quantity(inventory.getQuantity())
                .build();
    }
}
