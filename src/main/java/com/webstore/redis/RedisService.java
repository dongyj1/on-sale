package com.webstore.redis;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
public class RedisService {

    @Autowired
    JedisPool jedisPool;

    public <T> T get(KeyPrefix prefix, String key, Class<T> tClass) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            // Real Key
            String realKey = prefix.getPrefix() + key;
            String str = jedis.get(realKey);
            T t = stringToBean(str, tClass);
            return t;
        } finally {
            returnToPool(jedis);
        }
    }

    private void returnToPool(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }

    @SuppressWarnings("unchecked")
    private <T> T stringToBean(String str, Class<T> tClass) {
        if (str == null || str.length() == 0 || tClass == null) {
            return null;
        }
        if (tClass == int.class || tClass == Integer.class) {
            return (T) Integer.valueOf(str);
        } else if (tClass == long.class || tClass == Long.class) {
            return (T) Long.valueOf(str);
        } else if (tClass == String.class) {
            return (T) str;
        } else {
            return JSON.toJavaObject(JSON.parseObject(str), tClass);
        }
    }

    public <T> boolean set(KeyPrefix keyPrefix, String key, T t) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String str = beanToString(t);
            if (str == null || str.length() <= 0) {
                return false;
            }
            String realKey = keyPrefix.getPrefix() + key;
            int expireTime = keyPrefix.expireTimeInSeconds();
            if (expireTime <= 0) {
                jedis.set(realKey, str);
            } else {
                jedis.setex(realKey, expireTime, str);
            }
            return true;
        } finally {
            returnToPool(jedis);
        }
    }

    private <T> String beanToString(T t) {
        if (t == null) {
            return "";
        }
        Class<?> tClass = t.getClass();
        if (tClass == int.class || tClass == Integer.class) {
            return "" + t;
        } else if (tClass == long.class || tClass == Long.class) {
            return "" + t;
        } else if (tClass == String.class) {
            return (String) t;
        } else {
            return JSON.toJSONString(t);
        }

    }

    public <T> boolean exists(KeyPrefix keyPrefix, String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            if (key == null) {
                return false;
            }
            String realKey = keyPrefix.getPrefix() + key;
            return jedis.exists(realKey);
        } finally {
            returnToPool(jedis);
        }
    }

    public <T> Long incr(KeyPrefix prefix, String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            //生成真正的key
            String realKey = prefix.getPrefix() + key;
            return jedis.incr(realKey);
        } finally {
            returnToPool(jedis);

        }
    }

    public <T> Long decr(KeyPrefix prefix, String key) {
        Jedis jedis = null;
        try {
            jedis =  jedisPool.getResource();
            //生成真正的key
            String realKey  = prefix.getPrefix() + key;
            return  jedis.decr(realKey);
        }finally {
            returnToPool(jedis);
        }
    }
}
