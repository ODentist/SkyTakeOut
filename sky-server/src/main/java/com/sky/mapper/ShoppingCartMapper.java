package com.sky.mapper;

import com.sky.entity.Dish;
import com.sky.entity.ShoppingCart;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ShoppingCartMapper {


    List<ShoppingCart> checkShoppingCartList(ShoppingCart shoppingCart);

    void updateShoppingCart(ShoppingCart cart);

    void insert(ShoppingCart shoppingCart);

    void deleteByUserId(ShoppingCart shoppingCart);

    void deleteShoppingCart(ShoppingCart shoppingCartChecked);
}
