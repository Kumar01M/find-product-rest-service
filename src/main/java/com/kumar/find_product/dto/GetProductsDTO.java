package com.kumar.find_product.dto;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.kumar.find_product.model.Product;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetProductsDTO {

    private List<InnerProductDTO> productsList;
    private int filteredCount;
    private int totalCount;
    private Set<String> categories;

    @Data
    public static class InnerProductDTO {

        @JsonUnwrapped
        @JsonIgnoreProperties({"productID"})
        private Product product;
        
        // without using below props we can use Category type
        // but it can make conflicts with duplicate property name - TODO
        private String categoryName;
        private String categoryTags;
        private String shopName;
        private String shopAddress;
        private int totalCount;
    }

}   
