package com.kumar.find_product.model;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.AllArgsConstructor;
import lombok.Data;

@Schema
@Data
@AllArgsConstructor
public class Response<T> {
    // private String status;
    @Schema(description = "Message based on response.", requiredMode = RequiredMode.REQUIRED)
    private String message;
    @Schema(description = "Response Data", requiredMode = RequiredMode.REQUIRED)
    private T data;
}
