package com.webstore.redis;

public class OrderKey extends BasePrefix {
    public OrderKey(int expireTimeInSeconds, String prefix) {
        super(expireTimeInSeconds, prefix);
    }
}
