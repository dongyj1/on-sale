package com.webstore.util;

import com.alibaba.druid.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidatorUtil {


    private static final Pattern phoneNumber_pattern = Pattern.compile("1\\d{10}");

    public static boolean isPhoneNumber(String src) {
        if(StringUtils.isEmpty(src)) {
            return false;
        }
        Matcher m = phoneNumber_pattern.matcher(src);
        return m.matches();
    }

}
