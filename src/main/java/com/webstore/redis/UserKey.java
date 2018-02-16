package com.webstore.redis;


public class UserKey extends BasePrefix {

    public static final int TOKEN_EXPIRE = 3600*24 * 2;

    public UserKey(int expireTimeInSeconds, String prefix) {
        super(expireTimeInSeconds, prefix);
    }

    public UserKey(String prefix) {
        super(prefix);
    }
    public static UserKey token = new UserKey(TOKEN_EXPIRE, "tk");


}
