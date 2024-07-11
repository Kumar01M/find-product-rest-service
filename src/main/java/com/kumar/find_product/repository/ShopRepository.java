package com.kumar.find_product.repository;

import org.springframework.dao.DataAccessException;

import com.kumar.find_product.model.Shop;

public interface ShopRepository {
    boolean registerShop(Shop shop) throws DataAccessException;
}
