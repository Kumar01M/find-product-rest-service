package com.kumar.find_product.model;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class Category {
    @Schema(description = "Category ID", requiredMode = RequiredMode.REQUIRED)
    private Integer categoryID;

    @NotBlank(message = "Shop ID cannot be blank.")
    @Schema(description = "Shop ID")
    private Integer shopID;

    @NotBlank(message = "Category name cannot be blank.")
    @Schema(description = "Category Name", example = "Groceries")
    private String categoryName;

    @NotNull(message = "Category Tags can be empty but not null")
    @Schema(description = "Comma separated **tags** for categories i.e., keywords to search", example = "fruits, vegetables")
    private String categoryTags;
}
