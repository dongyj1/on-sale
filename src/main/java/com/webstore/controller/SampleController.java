package com.webstore.controller;

import com.webstore.domain.UserTest;
import com.webstore.redis.RedisService;
import com.webstore.redis.UserKey;
import com.webstore.result.CodeMsg;
import com.webstore.result.Result;
import com.webstore.service.UserTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/demo")
public class SampleController {

    @Autowired
    UserTestService userTestService;

    @Autowired
    RedisService redisService;

    @RequestMapping("/")
    @ResponseBody
    public String home() {
        return "HelloWorld";
    }

    @RequestMapping("/hello")
    @ResponseBody
    public String hello() {
        return "HelloWorld";
    }

    @RequestMapping("/error")
    @ResponseBody
    public Result<String> error() {
        return Result.error(CodeMsg.SESSION_ERROR);
    }

    @RequestMapping("/hello/thymeleaf")
    public String thymeleaf(Model model) {
        model.addAttribute("name", "jjjjj");
        return "hello";
    }

    @RequestMapping("/db/get")
    @ResponseBody
    public Result<UserTest> dbGet() {
        UserTest userTest = userTestService.getById(1);
        return Result.success(userTest);
    }

    @RequestMapping("/db/tx")
    @ResponseBody
    public Result<Boolean> dbTx() {
        boolean res = userTestService.tx();
        return Result.success(res);
    }

    @RequestMapping("/redis/get")
    @ResponseBody
    public Result<UserTest> redisGet() {
        UserTest userTest = redisService.get(UserKey.getById, "1", UserTest.class);
        return Result.success(userTest);
    }

    @RequestMapping("/redis/set")
    @ResponseBody
    public Result<Boolean> redisSet() {
        UserTest userTest = new UserTest();
        userTest.setId(1);;
        userTest.setName("1111111");
        boolean res = redisService.set(UserKey.getById, "1", userTest);
        return Result.success(res);
    }
}
