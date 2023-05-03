package com.sky.service;

import com.sky.vo.BusinessDataVO;
import com.sky.vo.DishOverViewVO;
import com.sky.vo.OrderOverViewVO;
import com.sky.vo.SetmealOverViewVO;

import java.time.LocalDateTime;

public interface WorkSpaceService {
    BusinessDataVO queryBusinessData();

    SetmealOverViewVO queryOverviewSetmeals();

    DishOverViewVO queryOverviewDishes();

    OrderOverViewVO queryOverviewOrders();

    BusinessDataVO getBusinessData(LocalDateTime of, LocalDateTime of1);

}
