package com.webstore.service;

import com.webstore.dao.OrderDao;
import com.webstore.domain.DiscountOrder;
import com.webstore.domain.Goods;
import com.webstore.domain.OrderInfo;
import com.webstore.domain.User;
import com.webstore.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class OrderService {

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderDao orderDao;

    public DiscountOrder getDiscountOrderByUserIdGoodsId(Long userId, long goodsId) {
        return orderDao.getMiaoshaOrderByUserIdGoodsId(userId, goodsId);
    }

    @Transactional
    public OrderInfo createOrder(User user, GoodsVo goodsVo) {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setCreateDate(new Date());
        orderInfo.setDeliveryAddrId(0L);
        orderInfo.setGoodsCount(1);
        orderInfo.setGoodsId(goodsVo.getId());
        orderInfo.setGoodsName(goodsVo.getName());
        orderInfo.setGoodsPrice(goodsVo.getDiscountPrice());
        orderInfo.setOrderChannel(1);
        orderInfo.setStatus(0);
        orderInfo.setUserId(user.getId());
        long orderId = orderDao.insert(orderInfo);
        DiscountOrder discountOrder = new DiscountOrder();
        discountOrder.setGoodsId(goodsVo.getId());
        discountOrder.setOrderId(orderId);
        discountOrder.setUserId(user.getId());
        orderDao.insertMiaoshaOrder(discountOrder);
        return orderInfo;
    }

    public OrderInfo placeOrder(User user, GoodsVo goodsVo) {
        goodsService.reduceStockByOne(goodsVo);
        return createOrder(user, goodsVo);
    }
}
