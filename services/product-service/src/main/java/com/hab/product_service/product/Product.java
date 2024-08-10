package com.hab.product_service.product;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Document
public class Product {

    @Id
    private String id;
    private String name;
    private String description;
    private String skuCode;
    private BigDecimal price;
}
