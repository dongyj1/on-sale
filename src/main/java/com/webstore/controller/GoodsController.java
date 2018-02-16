package com.webstore.controller;

import com.alibaba.druid.util.StringUtils;
import com.webstore.domain.User;
import com.webstore.redis.GoodsKey;
import com.webstore.redis.RedisService;
import com.webstore.service.GoodsService;
import com.webstore.service.UserService;
import com.webstore.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.spring4.context.SpringWebContext;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;
import java.util.List;

@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    UserService userService;

    @Autowired
    GoodsService goodsService;

    @Autowired
    RedisService redisService;

    @Autowired
    ThymeleafViewResolver thymeleafViewResolver;

    @Autowired
    ApplicationContext applicationContext;

    /**
     * List all goods, with page caching added.
     * @param request
     * @param response
     * @param model
     * @param user
     * @return
     */

    @RequestMapping(value = "/to_list", produces="text/html")
    @ResponseBody
    public String list(HttpServletRequest request, HttpServletResponse response, Model model, User user) {
        model.addAttribute("user", user);
        // show goods list
//        List<GoodsVo> list = goodsService.listGoodsVo();
//        model.addAttribute("goodsList", list);
//        return "goods_list";

        // Fetch from cache
        String list = redisService.get(GoodsKey.goodsList, "", String.class);
        if (!StringUtils.isEmpty(list)) {
            return list;
        }

        // Save static page to the cache
        List<GoodsVo> goodsList = goodsService.listGoodsVo();
        for (GoodsVo g : goodsList) {
            System.out.println(g);
        }
        model.addAttribute("goodsList", goodsList);
        SpringWebContext ctx = new SpringWebContext(request, response, request.getServletContext(), request.getLocale(), model.asMap(), applicationContext);
        String html = thymeleafViewResolver.getTemplateEngine().process("goods_list", ctx);
        if (!StringUtils.isEmpty(html)) {
            redisService.set(GoodsKey.goodsList, "", html);
        }
        return html;
    }



//    @RequestMapping(value = "/to_detail/{goodsId}", produces = "text/html")
//    @ResponseBody
//    public String detail(Model model, User user, @PathVariable("goodsId") long id) {
//        model.addAttribute("user", user);
//        GoodsVo goodsVo = goodsService.getGoodsVoById(id);
//        model.addAttribute("goods", goodsVo);
//
//        long startTime = goodsVo.getStartDate().getTime();
//        long endTime = goodsVo.getEndDate().getTime();
//        long now = System.currentTimeMillis();
//
//        int status = 0;
//        long remainSeconds = 0;
//        if (now < startTime) { // discount not started
//            status = 0;
//            remainSeconds = (startTime - now) / 1000;
//        } else if (endTime < now) { // discount is over
//            status = 2;
//            remainSeconds = -1;
//        } else {
//            status = 1;
//            remainSeconds = 0;
//        }
//        model.addAttribute("status", status);
//        model.addAttribute("remaining_seconds", remainSeconds);
////        return "goods_detail";
//
//
//    }

    @RequestMapping(value = "/to_detail/{goodsId}", produces = "text/html")
    @ResponseBody
    public String detail(HttpServletRequest request, HttpServletResponse response, Model model, User user, @PathVariable("goodsId") long id) {
        model.addAttribute("user", user);
        String html = redisService.get(GoodsKey.goodsDetail, "", String.class);
        if (!StringUtils.isEmpty(html)) {
            return html;
        }
        // Generate page and save to cache
        GoodsVo goodsVo = goodsService.getGoodsVoById(id);
        model.addAttribute("goods", goodsVo);

        long startTime = goodsVo.getStartDate().getTime();
        long endTime = goodsVo.getEndDate().getTime();
        long now = System.currentTimeMillis();

        int status = 0;
        long remainSeconds = 0;
        if (now < startTime) { // discount not started
            status = 0;
            remainSeconds = (startTime - now) / 1000;
        } else if (endTime < now) { // discount is over
            status = 2;
            remainSeconds = -1;
        } else {
            status = 1;
            remainSeconds = 0;
        }

        model.addAttribute("status", status);
        model.addAttribute("remaining_seconds", remainSeconds);

        SpringWebContext ctx = new SpringWebContext(request, response, request.getServletContext(), request.getLocale(), model.asMap(), applicationContext);
        html = thymeleafViewResolver.getTemplateEngine().process("goods_detail", ctx);
        if (!StringUtils.isEmpty(html)) {
            redisService.set(GoodsKey.goodsDetail, "" + id, html);
        }
        return html;
    }
}
