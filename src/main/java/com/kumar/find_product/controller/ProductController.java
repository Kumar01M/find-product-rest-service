package com.kumar.find_product.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kumar.find_product.dto.GetProductsDTO;
import com.kumar.find_product.model.Product;
import com.kumar.find_product.model.Response;
import com.kumar.find_product.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@CrossOrigin("*")
@RestController
@RequestMapping("api/v1/product")
public class ProductController {

    private Logger logger;
    private ProductService productService;

    public ProductController(@Autowired ProductService productService) {
        logger = LoggerFactory.getLogger(getClass());
        this.productService = productService;
    }

    @Operation(
        summary = "Add product",
        description = "Add product to your shop\n* `categoryID` & `shopID` is Mandatory" +
        "\n* `productID` is not required in **Request Body**.",
        tags = {"Product"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product added successfully",
                content = @Content(mediaType = "text/html")),
            @ApiResponse(responseCode = "400", description = "Product addition failed / Bad Request.",
                content = @Content(mediaType = "text/html")),
            @ApiResponse(responseCode = "404", description = "Internal server error.",
                content = @Content(mediaType = "text/html"))
        }
    )
    @PostMapping("/add-product")
    public ResponseEntity<String> addProduct(@Valid @RequestBody Product product) {
        logger.info("Adding product: " + product);
        return productService.addProduct(product);
    }

    @GetMapping("/get-products")
    public ResponseEntity<Response<GetProductsDTO>> getProducts(
        @RequestParam(name = "start", defaultValue = "0") Integer start,
        @RequestParam(name = "length", defaultValue = "10") Integer length,
        @RequestParam(name = "sort", required = false, defaultValue = "productName") String sort,
        @RequestParam(name = "search", required = false) String search,
        @RequestParam(name = "category", required = false) String category,
        @RequestParam(name = "shopID", required = false) Integer shopID,
        @RequestParam(name = "categoryID", required = false) Integer categoryID
    ) {
        logger.info("getProducts-params: search " + search + " category " + category + " start " + start
        + " length " + length + " sort " + sort + "shopID " + shopID + " categoryID " + categoryID);
        
        return productService.getProducts(start, length, sort, search, category, shopID, categoryID);
    }
}
