package com.kumar.find_product.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.kumar.find_product.model.Shop;
import com.kumar.find_product.repository.ShopRepository;
import com.kumar.find_product.utils.ConstantMessage;

@Service
public class ShopService {

    private Logger logger;
    private ShopRepository shopRepo;

    public ShopService(@Autowired ShopRepository shopRepo) {
        logger = LoggerFactory.getLogger(this.getClass());
        this.shopRepo = shopRepo;
    }

    public ResponseEntity<String> registerShop(Shop shop) {
        logger.info("creating shop " + shop.getOwnerEmail());
        try {
            if(shopRepo.registerShop(shop)) {
                logger.info(ConstantMessage.REGISTER_SHOP_SUCCESS);
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(ConstantMessage.REGISTER_SHOP_SUCCESS);
            }
            logger.info(ConstantMessage.REGISTER_SHOP_FAILURE);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ConstantMessage.REGISTER_SHOP_FAILURE);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        }
    }
}
