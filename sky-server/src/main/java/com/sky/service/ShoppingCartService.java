package com.sky.service;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;

import java.util.List;

public interface ShoppingCartService {
    void addShopPingCart(ShoppingCartDTO shoppingCartDTO);

    List<ShoppingCart> checkShoppingCart();

    void cleanShoppingCart();

    void substanceShoppingCart(ShoppingCartDTO shoppingCartDTO);
}
