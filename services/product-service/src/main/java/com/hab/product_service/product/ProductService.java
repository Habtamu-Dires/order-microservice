package com.hab.product_service.product;

import com.hab.product_service.client.InventoryClient;
import com.hab.product_service.client.InventoryResponse;
import com.hab.product_service.dto.ProductRequest;
import com.hab.product_service.dto.ProductResponse;
import com.hab.product_service.exception.ServiceNotAvailableException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository repository;
    private final ProductMapper mapper;
    private final InventoryClient inventoryClient;


    public ProductResponse createProduct(ProductRequest request) {
        Product product = repository.save(mapper.toProduct(request));
        log.info("Product created successfully");
        return  mapper.toProductResponse(product);
    }

    public List<ProductResponse> getAllProducts() {

        return  repository.findAll().stream()
                .map(mapper::toProductResponse)
                .toList();
    }

    public List<ProductResponse> getAvailableProducts(){
        //call inventory here.
        var res = inventoryClient.getAvailableInventory();
        if(res.success()){
            List<InventoryResponse> inventoryResponseList =  res.data();

            var skuCodeList = inventoryResponseList.stream()
                    .map(InventoryResponse::skuCode)
                    .toList();

            return repository.findAll().stream()
                    .filter(product -> skuCodeList
                            .contains(product.getSkuCode()))
                    .map(product -> mapper.toProductResponse(
                                    product,
                                    getQuantityFroInventoryResponse(
                                            inventoryResponseList,
                                            product.getSkuCode()
                                    )
                            )
                    )
                    .toList();
        } else {
            System.out.println("message " + res.message());
            throw new ServiceNotAvailableException(res.message());
        }

    }

    public Integer getQuantityFroInventoryResponse(
            List<InventoryResponse> inventoryResponseList,
            String skuCode
    ){
        return inventoryResponseList.stream()
                .filter(inventory -> inventory.skuCode().equals(skuCode))
                .map(InventoryResponse::quantity)
                .findAny()
                .orElse(0);
    }

}
