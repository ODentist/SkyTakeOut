package com.sky.service.impl;

import com.sky.context.BaseContext;
import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.ShoppingCart;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.mapper.ShoppingCartMapper;
import com.sky.service.ShoppingCartService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Autowired
    ShoppingCartMapper shoppingCartMapper;
    @Autowired
    DishMapper dishMapper;
    @Autowired
    SetmealMapper setmealMapper;

    @Override
    public void addShopPingCart(ShoppingCartDTO shoppingCartDTO) {
        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO, shoppingCart);
        shoppingCart.setUserId(BaseContext.getCurrentId());
//先查这个人有没有购物车（未结算）
//        boolean flage_Dish=false;//默认这个购物车内没找到该菜品或套餐
//        boolean flage_Setmeal=false;//默认这个购物车内没找到该菜品或套餐
        //公共部分
        List<ShoppingCart> shoppingCartList = shoppingCartMapper.checkShoppingCartList(shoppingCart);
            if (shoppingCartList != null && shoppingCartList.size() == 1) {
                //如果已经存在，就更新数量，数量加1
                shoppingCart = shoppingCartList.get(0);
                shoppingCart.setNumber(shoppingCart.getNumber() + 1);
                shoppingCartMapper.updateShoppingCart(shoppingCart);
            } else {
                //如果不存在，插入数据，数量就是1

                //判断当前添加到购物车的是菜品还是套餐
                Long dishId = shoppingCartDTO.getDishId();
                if (dishId != null) {
                    //添加到购物车的是菜品
                    insertDish(shoppingCart);
                } else {
                    //添加到购物车的是套餐
                    insertSetmeal(shoppingCart);
                }
                shoppingCart.setCreateTime(LocalDateTime.now());
                shoppingCart.setNumber(1);
                shoppingCartMapper.insert(shoppingCart);
            //查到有该用户的购物车，直接给数量加1，即使判断了套餐或菜品无意义
//            for (ShoppingCart cart : shoppingCartList) {
//                if (shoppingCart.getDishId() != null) {
//                    if (cart.getDishId() == shoppingCart.getDishId()) {
//                        //查出来的菜品id等于购物车的货物id，或套餐id==货物id
//                        flage_Dish=true;
//                        cart.setNumber(cart.getNumber() + 1);
//                        shoppingCartMapper.updateShoppingCart(cart);
//                    }
//                } if (shoppingCart.getSetmealId() != null) {
//                    if (cart.getSetmealId() == shoppingCart.getSetmealId()) {
//                        flage_Setmeal=true;
//                        cart.setNumber(cart.getNumber() + 1);
//                        shoppingCartMapper.updateShoppingCart(cart);
//                    }
//                }
//            }
//            if (flage_Dish==false){
//                insertDish(shoppingCart);
//                shoppingCart.setCreateTime(LocalDateTime.now());
//                shoppingCart.setNumber(1);
//                shoppingCartMapper.insert(shoppingCart);
//            }
//            if (flage_Setmeal==false){
//                insertSetmeal(shoppingCart);
//                shoppingCart.setCreateTime(LocalDateTime.now());
//                shoppingCart.setNumber(1);
//                shoppingCartMapper.insert(shoppingCart);
//            }
            //如果没找到就新增
            }
//        else {
//            //判断是否未菜品
//            if (shoppingCart.getDishId() != null) {
//                //通过dishid查菜品获得字段
//                insertDish(shoppingCart);
//                //判断是否未套餐
//            } else if (shoppingCart.getSetmealId() != null) {
//                insertSetmeal(shoppingCart);
//            }
            //执行插入。菜品获或者套餐

//        }
    }

    @Override
    public List<ShoppingCart> checkShoppingCart() {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUserId(BaseContext.getCurrentId());
        return shoppingCartMapper.checkShoppingCartList(shoppingCart);
    }

    @Override
    public void cleanShoppingCart() {
        ShoppingCart shoppingCart = ShoppingCart.builder().userId(BaseContext.getCurrentId()).build();
        shoppingCartMapper.deleteByUserId(shoppingCart);
    }

    @Override
    public void substanceShoppingCart(ShoppingCartDTO shoppingCartDTO) {
        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO,shoppingCart);
        shoppingCart.setUserId(BaseContext.getCurrentId());
        List<ShoppingCart> shoppingCartList = shoppingCartMapper.checkShoppingCartList(shoppingCart);
        ShoppingCart shoppingCartChecked = shoppingCartList.get(0);
        if (shoppingCartChecked.getNumber()>1){
            shoppingCartChecked.setNumber(shoppingCartChecked.getNumber() - 1);
            shoppingCartMapper.updateShoppingCart(shoppingCartChecked);
        }else shoppingCartMapper.deleteShoppingCart(shoppingCartChecked);
    }

    private void insertDish(ShoppingCart shoppingCart){
        Dish dish = dishMapper.getById(shoppingCart.getDishId());
        shoppingCart.setName(dish.getName());
        shoppingCart.setImage(dish.getImage());
        shoppingCart.setAmount(dish.getPrice());
    }

    private void insertSetmeal(ShoppingCart shoppingCart){
        Setmeal setmeal = setmealMapper.querySetMealById(shoppingCart.getSetmealId());
        shoppingCart.setName(setmeal.getName());
        shoppingCart.setImage(setmeal.getImage());
        shoppingCart.setAmount(setmeal.getPrice());
    }
}
