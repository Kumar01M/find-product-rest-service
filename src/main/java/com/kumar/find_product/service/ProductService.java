package com.kumar.find_product.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.kumar.find_product.dto.GetProductsDTO;
import com.kumar.find_product.model.Category;
import com.kumar.find_product.model.Product;
import com.kumar.find_product.model.Response;
import com.kumar.find_product.repository.ProductRepository;
import com.kumar.find_product.utils.ConstantMessage;

@Service
public class ProductService {

    private Logger logger;
    private ProductRepository productRepo;

    public ProductService(@Autowired ProductRepository productRepo) {
        logger = LoggerFactory.getLogger(this.getClass());
        this.productRepo = productRepo;
    }

    public ResponseEntity<String> addProduct(Product product) {
        logger.info("adding product " + product.getProductName());
        try {
            String message = "Product addition failed";
            if(productRepo.addProduct(product)) {
                message = "Product added successfully";
                logger.info(message);
                return ResponseEntity.status(HttpStatus.OK).body(message);
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

    public ResponseEntity<Response<GetProductsDTO>> getProducts(Object... argObjects) {
        logger.info("getProducts - args: " + argObjects.toString());//not working
        ResponseEntity<Response<GetProductsDTO>> response = ResponseEntity.badRequest()
            .body(new Response<>(ConstantMessage.INVALID_REQUEST_PARAM, null));
        GetProductsDTO data = null;
        try {
            data = productRepo.getProducts(argObjects);
            String message = "Products found";
            if (data.getFilteredCount() == 0) {
                message = "No Product found.";
            }
            return ResponseEntity.ok().body(new Response<>(message, data));
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
