package com.webstore.redis;

public class GoodsKey extends BasePrefix {
    public GoodsKey(int expireTimeInSeconds, String prefix) {
        super(expireTimeInSeconds, prefix);
    }

    public GoodsKey(String prefix) {
        super(prefix);
    }

    public static GoodsKey goodsList = new GoodsKey(60,"gl");
    public static GoodsKey goodsDetail = new GoodsKey(60, "gd");
}
