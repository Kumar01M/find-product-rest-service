package com.kumar.find_product.repository;

import org.springframework.dao.DataAccessException;

import com.kumar.find_product.dto.GetProductsDTO;
import com.kumar.find_product.model.Product;

public interface ProductRepository {

    boolean addProduct(Product product) throws DataAccessException;
    boolean updateProduct(Product product) throws DataAccessException;
    GetProductsDTO getProducts(Object ...argObjects) throws DataAccessException, Exception;
    
}
