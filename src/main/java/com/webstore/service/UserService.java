package com.webstore.service;

import com.alibaba.druid.util.StringUtils;
import com.webstore.dao.UserDao;
import com.webstore.domain.User;
import com.webstore.exception.GlobalException;
import com.webstore.redis.RedisService;
import com.webstore.redis.UserInfoKey;
import com.webstore.result.CodeMsg;
import com.webstore.util.MD5Util;
import com.webstore.util.UUIDUtil;
import com.webstore.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Service
public class UserService {

    public static String cookie_name_token = "token";

    @Autowired
    UserDao userDao;

    @Autowired
    RedisService redisService;

    public User getById(long id) {
        return userDao.getById(id);
    }

    public User getByToken(HttpServletResponse response, String token) {
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        User user = redisService.get(UserInfoKey.token, token, User.class);
        // extend the user valid time
        if (user != null) {
            addCookie(response, token, user);
        }
        return user;
    }


    private void addCookie(HttpServletResponse response, String token, User user) {
        Cookie cookie = new Cookie(cookie_name_token, token);
        redisService.set(UserInfoKey.token, token, user);
        cookie.setMaxAge(UserInfoKey.token.expireTimeInSeconds());
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    public boolean login(HttpServletResponse response, LoginVo loginVo) {
        if (loginVo == null) {
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }
        String phoneNumber = loginVo.getPhoneNumber();
        String formpass = loginVo.getPassword();
        // Validate phoneNumber exists
        User user = userDao.getById(Long.parseLong(phoneNumber));
        if (user == null) {
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        }
        // Validate password with input password
        String dbPass = user.getPassword();
        String saltDB = user.getSalt();
        String calculatedPass = MD5Util.inputPassToDBPass(formpass, saltDB);
        if (!dbPass.equals(calculatedPass)) {
            throw new GlobalException(CodeMsg.PASSWORD_ERROR);
        }
        // Generate new cookie
        String token = UUIDUtil.uuid();
        addCookie(response, token, user);
        return true;
    }
}
