package com.webstore.controller;

import com.webstore.domain.DiscountOrder;
import com.webstore.domain.OrderInfo;
import com.webstore.domain.User;
import com.webstore.result.CodeMsg;
import com.webstore.service.GoodsService;
import com.webstore.service.OrderService;
import com.webstore.service.UserService;
import com.webstore.vo.GoodsVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/discount")
public class DiscountController {

    private static Logger logger = LoggerFactory.getLogger(DiscountController.class);

    @Autowired
    UserService userService;

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @RequestMapping("/do_discount")
    public String list(Model model, User user, @PathVariable("goodsId")long id) {
        // if login
        if (user == null) {
            return "login";
        }
        model.addAttribute("user", user);
        // if can find good by id
        GoodsVo goodsVo = goodsService.getGoodsVoById(id);
        if (goodsVo == null) {
            logger.info("No goods found by " + id);
        }

        // Check stock
        int stock = goodsVo.getStockCount();
        if (stock <= 0) {
            model.addAttribute("errmsg", CodeMsg.DISCOUNT_OVER.getMsg());
            return "discount_fail";
        }

        // Check if the goods has been repeatedly ordered
        DiscountOrder order = orderService.getDiscountOrderByUserIdGoodsId(user.getId(), id);
        if (order != null) {
            model.addAttribute("errmsg", CodeMsg.REPEATE_DISCOUNT.getMsg());
            return "discount_fail";
        }

        // Go for the buying process
        OrderInfo orderInfo = orderService.placeOrder(user, goodsVo);
        model.addAttribute("orderInfo", orderInfo);
        model.addAttribute("goods", goodsVo);

        return "order_detail";
    }
}
