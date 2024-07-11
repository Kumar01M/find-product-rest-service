package com.kumar.find_product.controller;

import org.apache.logging.log4j.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.kumar.find_product.model.Shop;
import com.kumar.find_product.service.ShopService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/shop")
public class ShopController {

    private Logger logger;
    private ShopService shopService;

    public ShopController(@Autowired ShopService shopService) {
        logger = LoggerFactory.getLogger(this.getClass());
        this.shopService = shopService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerShop(@RequestBody Shop shop) {
        logger.info("registering shop: " + shop);
        return shopService.registerShop(shop);
    }
    
        

}
