package com.hab.inventory_service.inventory;

import com.hab.inventory_service.api_response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService service;

    @GetMapping
    public ResponseEntity<ApiResponse<Boolean>> isInStock(
            @RequestParam String skuCode,
            @RequestParam Integer quantity
    ) {
        var res = service.isInStock(skuCode,quantity);
        return ResponseEntity.ok(
                ApiResponse.<Boolean>builder()
                        .success(true)
                        .data(res)
                        .message("success")
                        .build()
        );
    }

    @GetMapping("available")
    ResponseEntity<ApiResponse<List<InventoryResponse>>> getAvailableInventory(){
        var response = service.getAvailableInventory();
        return ResponseEntity.ok(
                ApiResponse.<List<InventoryResponse>>builder()
                        .success(true)
                        .data(response)
                        .message("Available inventories")
                        .build()
        );
    }
}
