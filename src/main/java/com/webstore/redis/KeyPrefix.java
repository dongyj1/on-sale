package com.webstore.redis;

public interface KeyPrefix {
    public String getPrefix();
    public int expireTimeInSeconds();
}
