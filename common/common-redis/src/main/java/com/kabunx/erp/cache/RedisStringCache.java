package com.kabunx.erp.cache;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Data
@Component
public class RedisStringCache {
    // 静态成员变量
    private static RedisStringCache redisStringCache;

    // 不能使用new来实例化
    private RedisStringCache() {
    }

    @Resource
    StringRedisTemplate template;

    @PostConstruct
    private void init() {
        redisStringCache = new RedisStringCache();
        redisStringCache.setTemplate(template);
    }

    // 增加缓存
    public static void set(String key, String value, CacheType cacheType) {
        int timeout;
        switch (cacheType) {
            case CAPTCHA:
            case SMS_CODE:
                timeout = 600;
                break;
            default:
                timeout = 120;
        }
        redisStringCache.getTemplate()
                .opsForValue()
                .set(cacheType.getType() + key, value, timeout, TimeUnit.SECONDS);
    }

    public static void set(String key, String value, int timeout) {
        redisStringCache.getTemplate()
                .opsForValue()
                .set(key, value, timeout, TimeUnit.SECONDS);
    }

    // 查询缓存
    public static String get(String key, CacheType cacheType) {
        return redisStringCache.getTemplate()
                .opsForValue()
                .get(cacheType.getType() + key);
    }

    // 清除缓存
    public static Boolean delete(String key, CacheType cacheType) {
        return redisStringCache.getTemplate()
                .delete(cacheType.getType() + key);
    }
}
