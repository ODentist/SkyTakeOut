package com.sky.build;

import com.sky.entity.OrderDetail;
import com.sky.entity.Orders;
import lombok.Data;

import java.util.List;

@Data
public class OrderItem {
    private Orders orders;
    private List<OrderDetail> orderDetailList;
}
