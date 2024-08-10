package com.hab.product_service.product;

import com.hab.product_service.api_response.ApiResponse;
import com.hab.product_service.dto.ProductRequest;
import com.hab.product_service.dto.ProductResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    @PostMapping
    public ResponseEntity<ApiResponse<ProductResponse>> createProduct(
            @RequestBody @Valid ProductRequest request
    ){
        ProductResponse response =  service.createProduct(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<ProductResponse>builder()
                    .success(true)
                    .data(response)
                    .message("successfully created a product")
                    .build()
                );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductResponse>>> getAllProducts(){
        var response = service.getAllProducts();

        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<ProductResponse>>builder()
                        .success(true)
                        .data(response)
                        .message("successfully fetch all registered products")
                        .build()
                );
    }

    @GetMapping("/available")
    public ResponseEntity<ApiResponse<List<ProductResponse>>> getAvailableProducts(

    ){

        var response = service.getAvailableProducts();

        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<ProductResponse>>builder()
                        .success(true)
                        .data(response)
                        .message("successfully fetch available  products")
                        .build()
                );
    }

//    @GetMapping("/token")
//    public String getToken(){
//        return service.getToken();
//    }

}
