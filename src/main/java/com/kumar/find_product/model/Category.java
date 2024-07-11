package com.kumar.find_product.model;

import lombok.Data;

@Data
public class Category {
    private Integer categoryID;
    private Integer shopID;
    private String categoryName;
    private String categoryTags;
}
