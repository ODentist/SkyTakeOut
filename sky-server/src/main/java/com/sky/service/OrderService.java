package com.sky.service;

import com.sky.dto.*;
import com.sky.entity.OrderDetail;
import com.sky.result.PageResult;
import com.sky.vo.OrderPaymentVO;
import com.sky.vo.OrderStatisticsVO;
import com.sky.vo.OrderSubmitVO;
import com.sky.vo.OrderVO;

import java.util.List;

public interface OrderService {
    OrderSubmitVO oderSubmit(OrdersSubmitDTO ordersSubmitDTO);
    /**
     * 订单支付
     * @param ordersPaymentDTO
     * @return
     */
    OrderPaymentVO payment(OrdersPaymentDTO ordersPaymentDTO) throws Exception;

    /**
     * 支付成功，修改订单状态
     * @param outTradeNo
     */
    void paySuccess(String outTradeNo);

    PageResult checkHistoryOrders(OrdersPageQueryDTO ordersPageQueryDTO);

    OrderVO checkOrderDetail(Long id);

    void cancelOrder(Long id) throws Exception;

    void orderAgain(Long id);

    PageResult conditionSearchOrders(OrdersPageQueryDTO ordersPageQueryDTO);

    void cancelOrderBackend(OrdersCancelDTO ordersCancelDTO);

    void rejectionOrders(OrdersRejectionDTO ordersRejectionDTO) throws Exception;

    void confirm(OrdersConfirmDTO ordersConfirmDTO);

    void delivery(Long id);

    void complete(Long id);

    OrderStatisticsVO queryOrderStatistics();

    void reminder(Long id);
}
