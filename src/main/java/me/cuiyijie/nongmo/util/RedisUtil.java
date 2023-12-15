package me.cuiyijie.nongmo.util;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @desc: redis工具类
 * @author: shenghuang@iflytek.com
 * @date: 2019/11/21  10:52
 */
@Slf4j
@Component
public class RedisUtil {

    @Value("${redis.expire.hours:24}")
    private Integer expireTime;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 判断key是否存在
     *
     * @param key 键
     * @return true 存在 false不存在
     */
    public boolean hasKey(String key) {
        try {
            return stringRedisTemplate.hasKey(key);
        } catch (Exception e) {
            log.error("获取redis的key！", e);
            return false;
        }
    }

    /**
     * 删除多个key
     *
     * @param keys
     * @return
     */
    public boolean deletes(List<String> keys) {
        try {
            if (keys != null && !keys.isEmpty()) {
                stringRedisTemplate.delete(keys);
            }
            return true;
        } catch (Exception e) {
            log.error("获取redis的key！", e);
            return false;
        }
    }

    /**
     * 设置String
     *
     * @param key
     */
    public boolean setString(String key, String value) {
        try {
            stringRedisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 设置String
     *
     * @param key
     */
    public <T> boolean setObject(String key, T value) {
        try {
            String val = beanToString(value);
            if (val == null || val.length() <= 0) {
                return false;
            }
            stringRedisTemplate.opsForValue().set(key, val);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 获取String
     *
     * @param key
     */
    public String getString(String key) {
        try {
            return stringRedisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 设置值
     *
     * @param key
     * @param value
     * @param <T>
     * @return
     */
    public <T> boolean set(String key, T value) {
        if (expireTime == null) {
            throw new RuntimeException("没有设置redis超时时间配置！");
        }
        try {
            String val = beanToString(value);
            if (val == null || val.isEmpty()) {
                return false;
            }
            stringRedisTemplate.opsForValue().set(key, val, expireTime, TimeUnit.HOURS);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    /**
     * 设置含有过期时间的值
     *
     * @param key
     * @param value
     * @param <T>
     * @return
     */
    public <T> boolean setExpire(String key, T value, int time) {

        try {
            String val = beanToString(value);
            if (val == null || val.length() <= 0) {
                return false;
            }
            stringRedisTemplate.opsForValue().set(key, val, time, TimeUnit.SECONDS);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 获取值
     *
     * @param <T>
     * @param key
     * @param clazz
     * @return
     */
    public <T> T getList(String key, Class<T> clazz) {
        try {
            String value = stringRedisTemplate.opsForValue().get(key);
            return stringToListBean(value, clazz);
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * 获取值
     *
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T get(String key, Class<T> clazz) {
        try {
            String value = stringRedisTemplate.opsForValue().get(key);
            return stringToBean(value, clazz);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * String转对象
     *
     * @param <T>
     * @param value
     * @param clazz
     * @return
     */
    @SuppressWarnings("unchecked")
    private <T> T stringToListBean(String value, Class<T> clazz) {
        if (value == null || value.length() <= 0 || clazz == null) {
            return null;
        }
        try {
            return objectMapper.readValue(value, clazz);
        } catch (Exception exception) {
            return null;
        }
    }

    /**
     * String转对象
     *
     * @param value
     * @param clazz
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    private <T> T stringToBean(String value, Class<T> clazz) {
        if (value == null || value.length() <= 0 || clazz == null) {
            return null;
        }
        if (clazz == int.class || clazz == Integer.class) {
            return (T) Integer.valueOf(value);
        } else if (clazz == long.class || clazz == Long.class) {
            return (T) Long.valueOf(value);
        } else if (clazz == String.class) {
            return (T) value;
        } else {
            try {
                return objectMapper.readValue(value, clazz);
            } catch (Exception exception) {
                return null;
            }
        }
    }


    /**
     * 对象转字符串
     *
     * @param value
     * @param <T>
     * @return
     */
    private <T> String beanToString(T value) {
        if (value == null) {
            return null;
        }
        Class<?> clazz = value.getClass();
        if (clazz == int.class || clazz == Integer.class) {
            return "" + value;
        } else if (clazz == long.class || clazz == Long.class) {
            return "" + value;
        } else if (clazz == String.class) {
            return (String) value;
        } else {
            try {
                return objectMapper.writeValueAsString(value);
            } catch (Exception exception) {
                return "";
            }
        }
    }

    /**
     * 从队尾入队
     *
     * @param key
     * @param value
     * @return
     */
    public Long rightPush(String key, String value) {
        return stringRedisTemplate.opsForList().rightPush(key, value);
    }
}
