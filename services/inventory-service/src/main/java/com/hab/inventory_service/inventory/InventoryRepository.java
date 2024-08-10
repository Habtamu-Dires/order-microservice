package com.hab.inventory_service.inventory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface InventoryRepository extends JpaRepository<ProductInventory,Long> {

    @Query("SELECT pi FROM ProductInventory pi WHERE pi.skuCode=:skuCode")
    Optional<ProductInventory> findBySkuCode(String skuCode);
}
