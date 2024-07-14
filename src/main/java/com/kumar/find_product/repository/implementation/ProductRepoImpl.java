package com.kumar.find_product.repository.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.kumar.find_product.model.Product;
import com.kumar.find_product.repository.ProductRepository;

@Repository
public class ProductRepoImpl implements ProductRepository {

    private JdbcTemplate jdbcTemplate;

    public ProductRepoImpl(@Autowired JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean addProduct(Product product) throws DataAccessException {
        String insertQuery = "INSERT INTO product(`shopID`, `categoryID`, `productName`, " +
            "`price`, `quantity`, `unit`) VALUES(?, ?, ?, ?, ?, ?)";
        int result = jdbcTemplate.update(insertQuery, product.getShopID(), product.getCategoryID(), product.getProductName(), 
        product.getPrice(), product.getQuantity(), product.getUnit());
        return result == 1;
    }

    @Override
    public boolean updateProduct(Product product) throws DataAccessException {
        String updateQuery = "UPDATE product SET `productName` = ?, `price` = ? ," +
        "`quantity` = ?, `unit` = ?, `categoryID` = ? WHERE `shopID` = ? AND `deletedFlag` = 'N";
        int result = jdbcTemplate.update(updateQuery, product.getProductName(), product.getPrice(), 
        product.getQuantity(), product.getUnit(), product.getCategoryID(), 
        product.getShopID());
        return result == 1;
    }

}
