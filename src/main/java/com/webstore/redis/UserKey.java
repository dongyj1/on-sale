package com.webstore.redis;


public class UserInfoKey extends BasePrefix {

    public static final int TOKEN_EXPIRE = 3600*24 * 2;

    public UserInfoKey(int expireTimeInSeconds, String prefix) {
        super(expireTimeInSeconds, prefix);
    }

    public UserInfoKey(String prefix) {
        super(prefix);
    }
    public static UserInfoKey token = new UserInfoKey(TOKEN_EXPIRE, "tk");


}
