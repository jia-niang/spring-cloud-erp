package com.kabunx.erp.service;

import com.kabunx.erp.cache.CacheType;
import com.kabunx.erp.domain.dto.CaptchaDTO;
import com.kabunx.erp.domain.vo.CaptchaVO;

public interface CaptchaService {
    CaptchaVO generate(CaptchaDTO captchaDTO);

    boolean validate(String key, String value, CacheType cacheType);
}
