package com.kumar.find_product.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.kumar.find_product.model.Product;
import com.kumar.find_product.repository.ProductRepository;

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

}
