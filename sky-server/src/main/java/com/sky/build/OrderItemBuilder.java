package com.sky.build;

import com.sky.constant.MessageConstant;
import com.sky.context.BaseContext;
import com.sky.dto.OrdersSubmitDTO;
import com.sky.entity.*;
import com.sky.exception.AddressBookBusinessException;
import com.sky.exception.ShoppingCartBusinessException;
import com.sky.mapper.*;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class OrderItemBuilder {

    // 问题:你明明不是一个spring相关的类，但是这个类在构建对象时候，肯定会使用
    // 到一些spring相关的内容
    // 方式一、把当前这个类交给spring管理，直接就可以使用@Autowired 拿到这些要使用的mapper，或者是spring相关的内容
    // 方式二、构造方法传参
    private AddressBookMapper addressBookMapper;
    private ShoppingCartMapper cartMapper;
    private UserMapper userMapper;
    private OrderItem orderItem;
    private OrdersSubmitDTO submitDTO;
    private Long userId;
    private String orderId;

    public OrderItemBuilder(AddressBookMapper addressBookMapper, ShoppingCartMapper cartMapper
            , UserMapper userMapper
            , OrdersSubmitDTO submitDTO
    ) {
        this.addressBookMapper = addressBookMapper;
        this.cartMapper = cartMapper;
        this.userMapper = userMapper;
        this.submitDTO = submitDTO;
        this.orderItem = new OrderItem();
        this.userId  = BaseContext.getCurrentId();
        this.orderId = String.valueOf(System.currentTimeMillis());
    }
    //其实订单要使用到对象，肯定是非常多的，比如快照，比如售后，比如支付，比如配送

    //当你去builderOrder的时候，虽然此时的orders 并没有id
    public OrderItemBuilder builderOrders() {

        AddressBook addressBook = addressBookMapper.getById(submitDTO.getAddressBookId());
        if(addressBook == null){
            throw new AddressBookBusinessException(MessageConstant.ADDRESS_BOOK_IS_NULL);
        }
        Orders orders = new Orders();
        BeanUtils.copyProperties(submitDTO,orders);
        //一般订单号确实需要单独处理成一个字段，主要原因是因为  1、支付时候需要使用 2、订单号通常都会具备一些特有含义
        User user = userMapper.getById(userId);
        orders.setUserName(user.getName());


        //手动去设置主键id
        orders.setId(Long.parseLong(orderId));

        // 假设这个就是雪花算法生成的id 我就让订单表的id和 number保持一致
        orders.setNumber(orderId);
        orders.setStatus(Orders.PENDING_PAYMENT); //魔法数字  使用枚举来限制入参
        orders.setUserId(BaseContext.getCurrentId());
        orders.setOrderTime(LocalDateTime.now());
        orders.setPayStatus(Orders.UN_PAID);
        orders.setPhone(addressBook.getPhone());
        orders.setAddress(addressBook.getDetail());
        orders.setConsignee(addressBook.getConsignee());
        orderItem.setOrders(orders);
        return this;
    }


    public OrderItemBuilder builderOrderDetails() {

        ShoppingCart cart = new ShoppingCart();
        cart.setUserId(userId);
        List<ShoppingCart> cartList = cartMapper.checkShoppingCartList(cart);
        if(cartList == null || cartList.size() == 0){
            throw new ShoppingCartBusinessException(MessageConstant.SHOPPING_CART_IS_NULL);
        }
        List<OrderDetail> orderDetails = new ArrayList<>();
        for (ShoppingCart shoppingCart : cartList) {
            OrderDetail orderDetail = new OrderDetail();
            BeanUtils.copyProperties(shoppingCart,orderDetail);
            orderDetail.setOrderId(Long.parseLong(orderId));
            orderDetails.add(orderDetail);
        }
        orderItem.setOrderDetailList(orderDetails);
        return this;
    }

    public OrderItem build() {
        return orderItem;
    }

}
