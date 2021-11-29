package com.kabunx.erp.service.impl;

import com.google.code.kaptcha.Producer;
import com.kabunx.erp.cache.CacheType;
import com.kabunx.erp.cache.RedisStringCache;
import com.kabunx.erp.domain.dto.LoginKeyValueDTO;
import com.kabunx.erp.domain.vo.CaptchaVO;
import com.kabunx.erp.service.CaptchaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.UUID;

@Slf4j
@Service
public class CaptchaServiceImpl implements CaptchaService {

    @Resource(name = "textCodeKaptcha")
    Producer textCodeKaptcha;

    @Resource(name = "mathCodeKaptcha")
    Producer mathCodeKaptcha;

    @Override
    public CaptchaVO generate(String type) {
        // 1、创建验证码
        String text, code;
        BufferedImage image;
        if ("math".equals(type)) {
            text = mathCodeKaptcha.createText();
            String s1 = text.substring(0, 1);
            String s2 = text.substring(1, 2);
            int count = Integer.parseInt(s1) + Integer.parseInt(s2);
            code = String.valueOf(count);
            image = mathCodeKaptcha.createImage(s1 + "+" + s2 + "=?");
        } else {
            code = text = textCodeKaptcha.createText();
            image = textCodeKaptcha.createImage(text);
        }
        // 2、将text存入redis缓存<id, text>
        String key = UUID.randomUUID().toString();
        RedisStringCache.set(key, code, CacheType.CAPTCHA);
        // 3、转化为BASE64
        try {
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", output);
            String imageString = Base64.getEncoder().encodeToString(output.toByteArray());
            output.close();
            return CaptchaVO.builder().key(key).image(imageString).build();
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public boolean verifyKeyValue(LoginKeyValueDTO loginKeyValueDTO) {
        return true;
    }
}
