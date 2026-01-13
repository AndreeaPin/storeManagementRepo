package com.apintea.store.store_management_api.model;

import lombok.*;

import java.util.UUID;

@Builder
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private UUID id;

    private String name;

    private Double price;

    private Integer quantity;
}
