package com.kumar.find_product.repository.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.kumar.find_product.model.Shop;
import com.kumar.find_product.repository.ShopRepository;



@Repository
public class ShopRepoImpl implements ShopRepository{

    private JdbcTemplate jdbcTemplate;

    
    public ShopRepoImpl(@Autowired JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean registerShop(Shop shop) throws DataAccessException {
        String insertQuery = "INSERT INTO `shop` (ownerEmail, shopName, shopAddress, shopInfo, "
            + "ownerName, ownerPassword, ownerPhoneNumber) values (?, ?, ?, ?, ?, ?, ?)";
        int result = jdbcTemplate.update(insertQuery, shop.getOwnerEmail(), shop.getShopName(), 
            shop.getShopAdress(), "", shop.getOwnerName(), shop.getOwnerPassword(), shop.getOwnerPhoneNumber());
        return result == 1;
    }

}
