package com.hab.product_service.product;

import com.hab.product_service.dto.ProductRequest;
import com.hab.product_service.dto.ProductResponse;
import org.springframework.stereotype.Service;

@Service
public class ProductMapper {

    public Product toProduct(ProductRequest request) {
        if(request == null){
            return null;
        }
        return Product.builder()
                .name(request.name())
                .skuCode(request.skuCode())
                .description(request.description())
                .price(request.price())
                .build();
    }

    public ProductResponse toProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .skuCode(product.getSkuCode())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }

    public ProductResponse toProductResponse(Product product, Integer quantity) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .skuCode(product.getSkuCode())
                .description(product.getDescription())
                .price(product.getPrice())
                .quantity(quantity)
                .build();
    }
}
