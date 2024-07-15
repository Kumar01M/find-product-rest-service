package com.kumar.find_product.controller;

import java.util.List;

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

import com.kumar.find_product.model.Category;
import com.kumar.find_product.model.Response;
import com.kumar.find_product.service.CategoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@CrossOrigin("*")
@RestController()
@RequestMapping("api/v1/category")
public class CategoryController {

    private Logger logger;
    private CategoryService categoryService;

    public CategoryController(@Autowired CategoryService categoryService) {
        logger = LoggerFactory.getLogger(getClass());
        this.categoryService = categoryService;
    }

    @Operation(
        summary = "Add ,Update or Delete category",
        description = "Add category \n* `action` is **A** \n* `categoryID` is not required in **Request Body**" +
        "\n\nUpdate Category \n* `action` is **U** \n* All fields are mandatory in **Request Body**" +
        "\n\nDelete Category \n* `action` is **D** \n* `categoryID` & `shopID` is only required in **Request Body**",
        tags = {"Category"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category added/updated/deleted successfully",
                content = @Content(mediaType = "text/html")),
            @ApiResponse(responseCode = "400", description = "Bad Request.",
                content = @Content(mediaType = "text/html")),
            @ApiResponse(responseCode = "404", description = "Internal server error.",
                content = @Content(mediaType = "text/html"))
        }
    )
    @PostMapping("/manage-category")
    public ResponseEntity<String> manageCategory(@RequestParam(name = "action") char action, @RequestBody Category category) {
        logger.info("managing category Action: " + action + " Body: " + category);
        return categoryService.manageCategory(action, category);
    }

    @Operation(
        summary = "Get list of product categories in a shop",
        description = "Get list of categories of a particular shop\n\n `ShopID` is mandatory",
        tags = {"Category"}
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful response 0 or more categories in list"),
        @ApiResponse(responseCode = "400", description = "Invalid request parameters.",
            content = @Content(mediaType = "application/json", examples = {
                @ExampleObject(value = "{\"message\": \"Invalid request parameters.\", \"data\": null}")
            })
        ),
        @ApiResponse(responseCode = "404", description = "Internal server error.",
            content = @Content(mediaType = "application/json", examples = {
                @ExampleObject(value = "{\"message\": \"Internal server error.\", \"data\": null}")
            })
        )
    })
    @GetMapping("/get-categories")
    public ResponseEntity<Response<List<Category>>> getCategories(@RequestParam(name = "shopID") Integer shopID) {
        logger.info("get categories - shopID: " + shopID);
        return categoryService.getCategories(shopID);
    }

}
