package com.sky.service.impl;
import com.sky.entity.Orders;
import com.sky.mapper.DishMapper;
import com.sky.mapper.OrderMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.mapper.UserMapper;
import com.sky.service.WorkSpaceService;
import com.sky.vo.BusinessDataVO;
import com.sky.vo.DishOverViewVO;
import com.sky.vo.OrderOverViewVO;
import com.sky.vo.SetmealOverViewVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


@Service
@Slf4j
public class WorkSpaceServiceImpl implements WorkSpaceService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    SetmealMapper setmealMapper;
    @Autowired
    DishMapper dishMapper;

    @Override
    public BusinessDataVO queryBusinessData() {
//        List<User> userList= userMapper.queryAll();
//        LocalDateTime now = LocalDateTime.of(LocalDateTime.now().getYear(),
//                LocalDateTime.now().getMonth(),
//                LocalDateTime.now().getDayOfYear()
//                ,0,0,0);


        Integer newUsers= userMapper.queryAll();
        Double unitPrice = 0.0;
        Double orderCompletionRate = 0.0;
//        for (User user : userList) {
//            if (user.getCreateTime().isAfter(now)) newUsers++;
//        }
        Integer orderCount=orderMapper.checkValidOrderCount();
        Double turnover=orderMapper.queryTurnover();
        turnover = turnover == null? 0.0 : turnover;
        Integer allOrderCount=orderMapper.queryAllOrderCount();
        allOrderCount=allOrderCount==null?0:allOrderCount;
        if (orderCount==0){
            orderCompletionRate=0.0;
        }else
        orderCompletionRate=allOrderCount/orderCount/1.0;
        if (allOrderCount==0){
            unitPrice=0.0;
        }else
        unitPrice=turnover/allOrderCount;
        BusinessDataVO businessDataVO = BusinessDataVO.builder().turnover(turnover).newUsers(newUsers)
                .orderCompletionRate(orderCompletionRate)
                .unitPrice(unitPrice).validOrderCount(orderCount).build();
        return businessDataVO;
    }

    @Override
    public SetmealOverViewVO queryOverviewSetmeals() {
        Integer onSeal=setmealMapper.queryStatus_ON();
        Integer offSeal=setmealMapper.queryStatus_OFF();
        SetmealOverViewVO setmealOverViewVO = new SetmealOverViewVO();
        setmealOverViewVO.setSold(onSeal);
        setmealOverViewVO.setDiscontinued(offSeal);
        return setmealOverViewVO;
    }

    @Override
    public DishOverViewVO queryOverviewDishes() {
        Integer onSeal=dishMapper.queryStatus_ON();
        Integer offSeal=dishMapper.queryStatus_OFF();
        DishOverViewVO dishOverViewVO = new DishOverViewVO();
        dishOverViewVO.setSold(onSeal);
        dishOverViewVO.setDiscontinued(offSeal);
        return dishOverViewVO;
    }

    @Override
    public OrderOverViewVO queryOverviewOrders() {
        Integer waitingOrders=orderMapper.queryWaitingOrders();
        Integer deliveredOrders=orderMapper.queryDeliveredOrders();
        Integer completedOrders=orderMapper.queryCompletedOrders();
        Integer cancelledOrders=orderMapper.queryCancelledOrders();
        Integer allOrders=orderMapper.queryAllOrders();
        OrderOverViewVO orderOverViewVO = OrderOverViewVO.builder().waitingOrders(waitingOrders)
                .cancelledOrders(cancelledOrders)
                .allOrders(allOrders)
                .completedOrders(completedOrders)
                .deliveredOrders(deliveredOrders).build();
        return orderOverViewVO;
    }

    @Override
    public BusinessDataVO getBusinessData(LocalDateTime begin, LocalDateTime end) {
        /**
         * 营业额：当日已完成订单的总金额
         * 有效订单：当日已完成订单的数量
         * 订单完成率：有效订单数 / 总订单数
         * 平均客单价：营业额 / 有效订单数
         * 新增用户：当日新增用户的数量
         */

        Map map = new HashMap();
        map.put("begin",begin);
        map.put("end",end);

        //查询总订单数
        Integer totalOrderCount = orderMapper.countByMap(map);

        map.put("status", Orders.COMPLETED);
        //营业额
        Double turnover = orderMapper.sumByMap(map);
        turnover = turnover == null? 0.0 : turnover;

        //有效订单数
        Integer validOrderCount = orderMapper.countByMap(map);

        Double unitPrice = 0.0;

        Double orderCompletionRate = 0.0;
        if(totalOrderCount != 0 && validOrderCount != 0){
            //订单完成率
            orderCompletionRate = validOrderCount.doubleValue() / totalOrderCount;
            //平均客单价
            unitPrice = turnover / validOrderCount;
        }

        //新增用户数
        Integer newUsers = userMapper.queryNewCustomers(map);

        return BusinessDataVO.builder()
                .turnover(turnover)
                .validOrderCount(validOrderCount)
                .orderCompletionRate(orderCompletionRate)
                .unitPrice(unitPrice)
                .newUsers(newUsers)
                .build();
    }
}
