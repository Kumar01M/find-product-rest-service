package com.kumar.find_product.model;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class Shop {
    @Schema(description = "Shop ID", requiredMode = RequiredMode.REQUIRED)
    private Integer shopID;

    @NotBlank(message = "Owner Email shouldn't be blank")
    @Email(message = "Email should be valid")
    @Schema(description = "Shop owner E-Mail", example = "john.doe@gmail.com")
    private String ownerEmail;

    @NotBlank(message = "Shop name shouldn't be blank")
    @Schema(description = "Shop name", example = "John mobiles")
    private String shopName;

    @NotBlank(message = "Shop address shouldn't be blank")
    @Schema(description = "Shop Address", example = "NO:7, T Main Road, Chennai-22")
    private String shopAdress;

    @NotBlank(message = "Shop Info shouldn't be blank")
    @Schema(description = "Shop Information", example = "Mobile accessories available here")
    private String shopInfo;

    @NotBlank(message = "Owner name shouldn't be blank")
    @Schema(description = "Shop owner Name", example = "John Doe")
    private String ownerName;

    @NotBlank(message = "Owner password shouldn't be blank")
    @Schema(description = "Password")
    private String ownerPassword;

    @Schema(description = "Shop owner profile", example = "")
    private String ownerProfile;

    @NotBlank(message = "Owner phone number shouldn't be blank")
    @Schema(description = "Shop owner Phone number", example = "95431987643")
    private String ownerPhoneNumber; 
}
