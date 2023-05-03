//package com.sky.service.impl;
//
//import com.sky.build.OrderItem;
//import com.sky.build.OrderItemBuilder;
//import com.sky.constant.MessageConstant;
//import com.sky.context.BaseContext;
//import com.sky.dto.OrdersPageQueryDTO;
//import com.sky.dto.OrdersPaymentDTO;
//import com.sky.dto.OrdersSubmitDTO;
//import com.sky.entity.*;
//import com.sky.exception.AddressBookBusinessException;
//import com.sky.exception.ShoppingCartBusinessException;
//import com.sky.mapper.*;
//import com.sky.result.PageResult;
//import com.sky.service.OrderService;
//import com.sky.vo.OrderPaymentVO;
//import com.sky.vo.OrderSubmitVO;
//import com.sky.vo.OrderVO;
//import org.springframework.beans.BeanUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//public class OrderServiceImpl_Bulider implements OrderService {
//
//    @Autowired
//    private AddressBookMapper addressBookMapper;
//
//    @Autowired
//    private ShoppingCartMapper cartMapper;
//
//    @Autowired
//    private UserMapper userMapper;
//
//    @Autowired
//    private OrderMapper orderMapper;
//
//    @Autowired
//    private OrderDetailMapper orderDetailMapper;
//
//    @Override
//    public OrderSubmitVO oderSubmit(OrdersSubmitDTO submitDTO) {
//        /**
//         * 构造器设计模式
//         *      我们现在使用的构造器设计模式其实都是利用lombok 这哥们帮我们生成
//         *      1、普通场景，本来你可以用set的，现在你就替换成lombok 生成的构造器设计模式的代码，但是这种方式其实没有什么实际的意义
//         *      2、就用于构建复杂的对象，比如说，以后我们某个实体类里边可能包含 上百个字段，或者是有很多个实体类对象，就无比适合我们的构造器设计模式
//         *  我们可以利用一个builder 去构建出来一个当前实体类中需要使用到的所有内容的实体类，然后再利用这个builder类去获得里边内容
//         *      通过这个构造器设计模式 我就将一类一类的数据封装 封装成了一个一个build方法,如果我们进行修改代码，或者我们要进行修改bug，我们就只需要找到具体的
//         *      这个build方法不就OK了，而不用像之前一样，去漫无目的的把所有代码都阅读一遍
//         */
//
//        // 学完苍穹外卖之后，我们学习 微服务框架 微服务框架高级
//        // 90以上
//        // 18K 以上 20K
//
//
//        /*
//        OrderItemBuilder orderBuilder = new OrderItemBuilder();
//        OrderItem  orderItem =    orderBuilder.buildOrderDetails()
//                                                .buildAddress();
//        List<OrderDetail> orderDetails = orderItem.getOrderDetails();
//        Orders order = orderItem.getOrder();
//        User user = orderItem.getUser();*/
//
//        // 1、面试官问：哥们有没有使用过设计模式  0、他的场景 1、提高代码阅读性  2、方便后期维护
//        // 2、在使用构造器设计模式：就是如何在类与类之间传参数
//                                     //构造器传参   //@Component  @Autowire(就不好传递非spring管理的类型)  // threadLocal
//        // 3、有关分库分表的  高表以及宽表对应的知识
//
//        // 只有主干，没有分支
//        OrderItemBuilder orderItemBuilder =
//                new OrderItemBuilder(addressBookMapper,cartMapper,userMapper,submitDTO);
//        OrderItem orderItem = orderItemBuilder.builderOrders()
//                .builderOrderDetails()
//                .build();
//
//        Orders orders = orderItem.getOrders();
//        List<OrderDetail> orderDetailList = orderItem.getOrderDetailList();
//        orderMapper.insertOrders(orders);
//        orderDetailMapper.insertDetailList(orderDetailList);
//        //5、删除购物车
//        ShoppingCart shoppingCart = new ShoppingCart();
//        shoppingCart.setUserId(BaseContext.getCurrentId());
//        cartMapper.deleteByUserId(shoppingCart);
//
//        OrderSubmitVO orderVO = buildeOrderVo(orders);//封装前端VO对象提交返回信息
//        return orderVO;
//    }
//
//    private OrderSubmitVO buildeOrderVo(Orders orders) {
//        OrderSubmitVO orderVO = new OrderSubmitVO();
//        orderVO.setId(orders.getId());
//        //正常操作是需要验价的
//        orderVO.setOrderAmount(orders.getAmount());
//        orderVO.setOrderTime(orders.getOrderTime());
//        orderVO.setId(orders.getId());
//        return orderVO;
//    }
//
//    @Override
//    public OrderPaymentVO payment(OrdersPaymentDTO ordersPaymentDTO) throws Exception {
//        return null;
//    }
//
//    @Override
//    public void paySuccess(String outTradeNo) {
//
//    }
//
//    @Override
//    public PageResult checkHistoryOrders(OrdersPageQueryDTO ordersPageQueryDTO) {
//        return null;
//    }
//
//    @Override
//    public OrderVO checkOrderDetail(Long id) {
//        return null;
//    }
//
//    @Override
//    public void cancelOrder(Long id) throws Exception {
//
//    }
//
//    @Override
//    public void orderAgain(Long id) {
//
//    }
//
//   /* @Override
//    public OrderVO submitOrders(OrdersSubmitDTO submitDTO) {
//        // 订单幂等性问题
//        //1、查询地址
//        AddressBook addressBook = addressBookMapper.getById(submitDTO.getAddressBookId());
//        if(addressBook == null){
//            throw new AddressBookBusinessException(MessageConstant.ADDRESS_BOOK_IS_NULL);
//        }
//        //2、需要使用到 userId 查询购物车
//        ShoppingCart cart = new ShoppingCart();
//        Long userId = BaseContext.getCurrentId();
//        cart.setUserId(userId);
//        List<ShoppingCart> cartList = cartMapper.list(cart);
//        if(cartList == null || cartList.size() == 0){
//            throw new ShoppingCartBusinessException(MessageConstant.SHOPPING_CART_IS_NULL);
//        }
//        //  需要填充时使用到的实体类都有了
//        Orders orders = new Orders();
//        BeanUtils.copyProperties(submitDTO,orders);
//        //一般订单号确实需要单独处理成一个字段，主要原因是因为  1、支付时候需要使用 2、订单号通常都会具备一些特有含义
//        User user = userMapper.getById(userId);
//        orders.setUserName(user.getName());
//        orders.setNumber(String.valueOf(System.currentTimeMillis()));
//        orders.setStatus(Orders.PENDING_PAYMENT); //魔法数字  使用枚举来限制入参
//        orders.setUserId(BaseContext.getCurrentId());
//        orders.setOrderTime(LocalDateTime.now());
//        orders.setPayStatus(Orders.UN_PAID);
//        orders.setPhone(addressBook.getPhone());
//        orders.setAddress(addressBook.getDetail());
//        orders.setConsignee(addressBook.getConsignee());
//        // 需要主键返回
//        //4、保存order，保存orderDetails
//        orderMapper.insert(orders);
//        //3、根据地址和购物车填充 order和orderDetails
//        //4、进行批量插入
//        List<OrderDetail> orderDetails = new ArrayList<>();
//        for (ShoppingCart shoppingCart : cartList) {
//            OrderDetail orderDetail = new OrderDetail();
//            BeanUtils.copyProperties(shoppingCart,orderDetail);
//            orderDetail.setOrderId(orders.getId());
//            orderDetails.add(orderDetail);
//        }
//        orderDetailMapper.insertBatch(orderDetails);
//        //5、删除购物车
//        cartMapper.deleteByUserId(userId);
//        //6、封装vo返回
//        OrderVO orderVO = new OrderVO();
//        orderVO.setId(orders.getId());
//        //正常操作是需要验价的
//        orderVO.setAmount(orders.getAmount());
//        orderVO.setOrderTime(orders.getOrderTime());
//        orderVO.setNumber(orders.getNumber());
//        return orderVO;
//    }*/
//
//}
