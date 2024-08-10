package com.hab.inventory_service.inventory;

import com.hab.inventory_service.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {

    private final InventoryRepository repository;
    private final InventoryMapper mapper;

    public boolean isInStock(String skuCode, Integer quantity){
        log.info("Requesting for stock {} with quantity {}", skuCode,quantity);
        return repository.findBySkuCode(skuCode)
              .map(inventory -> {
                  if(inventory.getQuantity() >= quantity){
                      var newQuantity = inventory.getQuantity() - quantity;
                      inventory.setQuantity(newQuantity);
                      repository.save(inventory);
                      return true;
                  } else {
                      throw new ResourceNotFoundException(
                        "Insufficient quantity for skuCode " + skuCode
                      );
                  }
              })
              .orElseThrow(() -> new ResourceNotFoundException(
                              "Product with skuCode " + skuCode + " not found")
              );
    }

    //avaialble stocks
    public List<InventoryResponse> getAvailableInventory(){
        return repository.findAll().stream()
                .filter(inventory -> inventory.getQuantity() >= 1)
                .map(mapper::toInventoryResponse)
                .toList();
    }
}
