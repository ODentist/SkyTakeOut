package com.sky.controller.admin;

import com.sky.result.Result;
import com.sky.service.WorkSpaceService;
import com.sky.vo.BusinessDataVO;
import com.sky.vo.DishOverViewVO;
import com.sky.vo.OrderOverViewVO;
import com.sky.vo.SetmealOverViewVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

    @Slf4j
    @RestController
    @RequestMapping("/admin/workspace")
    public class WorkspaceController {
    @Autowired
    WorkSpaceService workSpaceService;


    @GetMapping("/businessData")
    public Result<BusinessDataVO> queryBusinessData(){
        log.info("今日数据查询");
        BusinessDataVO businessDataVO=workSpaceService.queryBusinessData();
        return Result.success(businessDataVO);
    }

    @GetMapping("/overviewSetmeals")
        public Result<SetmealOverViewVO> queryOverviewSetmeals(){
        log.info("查询套餐总览");
        SetmealOverViewVO setmealOverViewVO=workSpaceService.queryOverviewSetmeals();
        return Result.success(setmealOverViewVO);
    }

    @GetMapping("/overviewDishes")
        public Result<DishOverViewVO> queryOverviewDishes(){
        log.info("查询菜品总览");
        DishOverViewVO dishOverViewVO=workSpaceService.queryOverviewDishes();
        return Result.success(dishOverViewVO);
    }

    @GetMapping("/overviewOrders")
        public Result<OrderOverViewVO> queryOverviewOrders(){
        log.info("查询订单总览");
        OrderOverViewVO orderOverViewVO=workSpaceService.queryOverviewOrders();
        return Result.success(orderOverViewVO);
    }
}
