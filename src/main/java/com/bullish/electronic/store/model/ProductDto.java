package com.bullish.electronic.store.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductDto {

    private String name;
    private Double price;
    private String info;
    private int stockQuantity;
}
