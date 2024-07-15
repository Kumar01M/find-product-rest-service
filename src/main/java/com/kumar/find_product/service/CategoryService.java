package com.kumar.find_product.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.kumar.find_product.model.Category;
import com.kumar.find_product.model.Response;
import com.kumar.find_product.repository.CategoryRepository;
import com.kumar.find_product.utils.ConstantMessage;

import io.micrometer.common.util.StringUtils;

@Service
public class CategoryService {

    private Logger logger;
    private CategoryRepository categoryRepo;

    public CategoryService(@Autowired CategoryRepository categoryRepo) {
        logger = LoggerFactory.getLogger(this.getClass());
        this.categoryRepo = categoryRepo;
    }

    public ResponseEntity<String> manageCategory(char action, Category category) {
        logger.info("manage category " + category.getShopID());
        if (!isValid(action, category)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(HttpStatus.BAD_REQUEST.getReasonPhrase());
        }
        try {
            String message = "";
            switch (action) {
                case 'A':
                    message = "Category addition failed";
                    if(categoryRepo.addCategory(category)) {
                        message = "Category added successfully";
                        logger.info(message);
                        return ResponseEntity.status(HttpStatus.OK).body(message);
                    }
                    break;
                case 'U':
                    message = "Category updation failed";
                    if(categoryRepo.updateCategory(category)) {
                        message = "Category updated successfully";
                        logger.info(message);
                        return ResponseEntity.status(HttpStatus.OK).body(message);
                    }
                    break;
                case 'D':
                    message = "Category deletion failed";
                    if(categoryRepo.deleteCategory(category)) {
                        message = "Category deleted successfully";
                        logger.info(message);
                        return ResponseEntity.status(HttpStatus.OK).body(message);
                    }
                    break;
            }
            logger.info(message);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        } catch (DataAccessException e) {
            logger.error("message: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(HttpStatus.BAD_REQUEST.getReasonPhrase());
        } catch (Exception e) {
            logger.error("message: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        }
    }

    private boolean isValid(char action, Category category) {
        if (category.getShopID() == null) {
            return false;
        }
        if (action != 'A' && category.getCategoryID() == null) {
            return false;
        }
        if (action != 'D') {
            if (StringUtils.isBlank(category.getCategoryName())) {
                return false;
            }
            if (category.getCategoryTags() == null) {
                category.setCategoryTags("");
            }
        }
        return true;
    }

    public ResponseEntity<Response<List<Category>>> getCategories(Integer shopID) {
        logger.info("getCategories - shopID: " + shopID);
        ResponseEntity<Response<List<Category>>> response = ResponseEntity.badRequest()
            .body(new Response<>(ConstantMessage.INVALID_REQUEST_PARAM, null));
        if (shopID == null) {
            return response;
        }
        List<Category> categories = null;
        try {
            categories = categoryRepo.getCategories(shopID);
            String message = "Categories found";
            if (categories.isEmpty()) {
                message = "No categories found.";
            }
            return ResponseEntity.ok().body(new Response<>(message, categories));
        } catch (DataAccessException e) {
            logger.error("message: " + e.getMessage());
            return response;
        } catch (Exception e) {
            logger.error("message: " + e.getMessage());
            return ResponseEntity.internalServerError()
                .body(new Response<>(ConstantMessage.INTERNAL_SERVER_ERROR, null));
        }
    }

}
