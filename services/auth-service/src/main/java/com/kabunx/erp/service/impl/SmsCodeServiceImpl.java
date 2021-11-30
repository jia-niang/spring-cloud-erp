package com.kabunx.erp.service.impl;

import com.kabunx.erp.cache.CacheType;
import com.kabunx.erp.cache.RedisStringCache;
import com.kabunx.erp.domain.dto.SmsCodeDTO;
import com.kabunx.erp.exception.AuthException;
import com.kabunx.erp.service.SmsCodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class SmsCodeServiceImpl implements SmsCodeService {

    private static final int count = 6;

    @Override
    public String generateAndSend(SmsCodeDTO smsCodeDTO) {
        // 1、生成短信验证码
        String code = generate(smsCodeDTO.getType());
        // 2、发送短信验证码
        boolean isSend = send(smsCodeDTO.getPhone(), code);
        if (!isSend) {
            throw new AuthException("");
        }
        // 3、加入缓存
        String key = UUID.randomUUID().toString();
        RedisStringCache.set(key, code, CacheType.SMS_CODE);
        log.info("phone {}, key {}, code {}", smsCodeDTO.getPhone(), key, code);
        return key;
    }

    @Override
    public boolean validate(String key, String value, CacheType cacheType) {
        String code = RedisStringCache.get(key, cacheType);
        boolean result = value.equals(code);
        // 验证成功需要清楚缓存
        if (result) {
            RedisStringCache.del(key, cacheType);
        }
        return result;
    }

    private String generate(String type) {
        int count;
        switch (type) {
            case "register":
                count = 4;
                break;
            case "login":
                count = 6;
                break;
            default:
                count = SmsCodeServiceImpl.count;
        }
        return String.valueOf((int) ((Math.random() * 9 + 1) * Math.pow(10, count - 1)));
    }

    // 短息发送
    private boolean send(String phone, String code) {
        return true;
    }
}
