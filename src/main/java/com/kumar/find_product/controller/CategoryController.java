package com.kumar.find_product.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kumar.find_product.model.Category;
import com.kumar.find_product.service.CategoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
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

}
