package com.hab.order_service.order;

import com.hab.order_service.api_response.ApiResponse;
import com.hab.order_service.dto.OrderRequest;
import com.hab.order_service.dto.OrderResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService service;

    @PostMapping
    public ResponseEntity<ApiResponse<OrderResponse>> placeOrder(
            @RequestBody @Valid OrderRequest request
            ){

        OrderResponse response = service.placeOrder(request);
        return  ResponseEntity.ok(
                ApiResponse.<OrderResponse>builder()
                        .success(true)
                        .data(response)
                        .message("Order successfully placed")
                        .build()
        );
    }
}
