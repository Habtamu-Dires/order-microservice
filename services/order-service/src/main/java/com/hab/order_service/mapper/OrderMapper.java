package com.hab.order_service.mapper;

import com.hab.order_service.dto.OrderRequest;
import com.hab.order_service.dto.OrderResponse;
import com.hab.order_service.order.Order;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrderMapper {

    public Order toOrder(OrderRequest request) {
        return Order.builder()
                .orderNumber(UUID.randomUUID().toString())
                .skuCode(request.skuCode())
                .price(request.price())
                .quantity(request.quantity())
                .build();
    }

    public OrderResponse toOrderResponse(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .orderNumber(order.getOrderNumber())
                .skuCode(order.getSkuCode())
                .price(order.getPrice())
                .quantity(order.getQuantity())
                .build();
    }
}
