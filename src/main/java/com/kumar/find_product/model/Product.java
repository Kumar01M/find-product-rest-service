package com.kumar.find_product.model;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class Product {
    private Integer productID;
    private Integer categoryID;
    private Integer shopID;
    private String productName;
    private BigDecimal price;
    private BigDecimal quantity;
    private String unit;
    private BigDecimal totalQuantity;
}
