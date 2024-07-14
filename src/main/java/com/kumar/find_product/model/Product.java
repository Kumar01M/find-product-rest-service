package com.kumar.find_product.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class Product {
    @Schema(description = "Product ID", requiredMode = RequiredMode.REQUIRED)
    private Integer productID;

    @Schema(description = "Category ID", requiredMode = RequiredMode.REQUIRED)
    @NotBlank(message = "Category ID shouldn't be blank.")
    private Integer categoryID;

    @Schema(description = "Shop ID", requiredMode = RequiredMode.REQUIRED)
    @NotBlank(message = "Shop ID shouldn't be blank.")
    private Integer shopID;

    @Schema(description = "Product Name", example = "Tomato", requiredMode = RequiredMode.REQUIRED)
    @NotBlank(message = "Product name shouldn't be blank.")
    private String productName;

    @Schema(description = "Product price", example = "100", requiredMode = RequiredMode.REQUIRED)
    @NotBlank(message = "Price shouldn't be blank.")
    private BigDecimal price;

    @Schema(description = "Product quantity available", example = "2", requiredMode = RequiredMode.REQUIRED)
    @NotBlank(message = "Quantity shouldn't be blank.")
    private BigDecimal quantity;

    @Schema(description = "Quantity unit", example = "NOS", requiredMode = RequiredMode.REQUIRED)
    @NotBlank(message = "Quantity unit shouldn't be blank.")
    private String unit;

    @JsonIgnore
    @Schema(description = "Total quantity", example = "2", requiredMode = RequiredMode.REQUIRED)
    private BigDecimal totalQuantity;
}
