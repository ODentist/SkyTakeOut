package com.sky.controller.user;


import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;
import com.sky.result.Result;
import com.sky.service.ShoppingCartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 购物车
 */
@RestController
@RequestMapping("/user/shoppingCart")
@Slf4j
@Api(tags = "C端-购物车接口")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    /**
     * 添加购物车
     * @param shoppingCartDTO
     * @return
     */
    @PostMapping("/add")
    @ApiOperation("添加购物车")
    public Result<String> add(@RequestBody ShoppingCartDTO shoppingCartDTO){
        log.info("添加购物车{}",shoppingCartDTO);
        shoppingCartService.addShopPingCart(shoppingCartDTO);
        return Result.success();
    }


    @GetMapping("/list")
    @ApiOperation("查看购物车")
    public Result<List<ShoppingCart>> checkShoppingCart(){
        log.info("查看购物车");
        List<ShoppingCart> shoppingCartList=shoppingCartService.checkShoppingCart();
        return Result.success(shoppingCartList);
    }

    @DeleteMapping
    @ApiOperation("清空购物车")
    public Result cleanShoppingCart(){
        log.info("开始清空购物车");
        shoppingCartService.cleanShoppingCart();
        return Result.success();
    }

    @PostMapping("/sub")
    @ApiOperation("减少购物车数量")
    public Result substanceShoppingCart(@RequestBody ShoppingCartDTO shoppingCartDTO){
        log.info("减少购物车货物数量");
        shoppingCartService.substanceShoppingCart(shoppingCartDTO);
        return Result.success();

    }
}
