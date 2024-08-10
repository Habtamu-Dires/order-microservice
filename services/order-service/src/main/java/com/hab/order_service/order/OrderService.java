package com.hab.order_service.order;

import com.hab.order_service.api_response.ApiResponse;
import com.hab.order_service.client.InventoryClient;
import com.hab.order_service.dto.OrderRequest;
import com.hab.order_service.dto.OrderResponse;
import com.hab.order_service.event.OrderPlacedEvent;
import com.hab.order_service.exception.ResourceNotFoundException;
import com.hab.order_service.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository repository;
    private final OrderMapper mapper;
    private final InventoryClient inventoryClient;
    private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;

    public OrderResponse placeOrder(OrderRequest request) {
        ApiResponse<?> apiResponse = inventoryClient.
                isInStock(request.skuCode(), request.quantity());

        if(apiResponse.success()){
            var order = repository.save(mapper.toOrder(request));

            OrderPlacedEvent orderPlacedEvent = new OrderPlacedEvent();
            orderPlacedEvent.setOrderNumber(order.getOrderNumber());
            orderPlacedEvent.setEmail(request.userDetails().email());
            orderPlacedEvent.setFirstname(request.userDetails().firstname());
            orderPlacedEvent.setLastname(request.userDetails().lastname());

            log.info("Start sending orderPlacedEvent {} to kafka topic", orderPlacedEvent);
            kafkaTemplate.send("order-placed",orderPlacedEvent);
            log.info("End sending orderPlacedEvent {} to kafka topic", orderPlacedEvent);


            return mapper.toOrderResponse(order);
        } else {
            System.out.println("the message" + apiResponse.message());
            throw new ResourceNotFoundException(
                    apiResponse.message()
            );
        }
    }
}
