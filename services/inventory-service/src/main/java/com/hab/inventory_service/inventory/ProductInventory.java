package com.hab.inventory_service.inventory;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "t_inventory")
public class ProductInventory {
    @Id
    @SequenceGenerator(
            name = "inventory_seq",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
       generator = "inventory_seq"
    )
    private Long id;
    private String skuCode;
    private Integer quantity;
}
