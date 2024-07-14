package com.kumar.find_product.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.kumar.find_product.exceptionhandling.CustomExceptionHandler;
import com.kumar.find_product.model.Shop;
import com.kumar.find_product.service.ShopService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("api/v1/shop")
public class ShopController extends CustomExceptionHandler {

    private Logger logger;
    private ShopService shopService;

    public ShopController(@Autowired ShopService shopService) {
        logger = LoggerFactory.getLogger(this.getClass());
        this.shopService = shopService;
    }

    @Operation(
      summary = "Register shop",
      description = "Register your shop.\n* Already registered email is not valid\n" +
        "* `shopID` not required in **Request body**",
      tags = {"Shop"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Shop registration success.",
                content = @Content(mediaType = "text/html")),
            @ApiResponse(responseCode = "400", description = "Failed to register! Contact Administrator. / Bad Request.",
                content = @Content(mediaType = "text/html")),
            @ApiResponse(responseCode = "404", description = "Internal server error.",
                content = @Content(mediaType = "text/html"))
        }
    )
    @PostMapping("/register")
    public ResponseEntity<String> registerShop(@Valid @RequestBody Shop shop) {
        logger.info("registering shop: " + shop);
        return shopService.registerShop(shop);
    }

}
