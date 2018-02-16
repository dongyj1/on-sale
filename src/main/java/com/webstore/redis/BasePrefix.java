package com.webstore.redis;

public abstract class BasePrefix implements KeyPrefix {

    private int expireTimeInSeconds;
    private String prefix;

    @Override
    public String getPrefix() {
        String className = getClass().getSimpleName();
        return className + ":" + prefix;
    }

    @Override
    public int expireTimeInSeconds() {
        return expireTimeInSeconds;
    }

    public BasePrefix(int expireTimeInSeconds, String prefix) {
        this.expireTimeInSeconds = expireTimeInSeconds;
        this.prefix = prefix;
    }

    public BasePrefix(String prefix) {
        this(0, prefix);
    }
}
