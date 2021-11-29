package com.kabunx.erp.service;

import com.kabunx.erp.cache.CacheType;
import com.kabunx.erp.domain.vo.CaptchaVO;

public interface CaptchaService {
    CaptchaVO generate(String type);

    boolean verify(String key, String value, CacheType cacheType);
}
