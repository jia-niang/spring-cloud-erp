package com.kabunx.erp.service;

import com.kabunx.erp.cache.CacheType;
import com.kabunx.erp.domain.dto.SmsCodeDTO;

public interface SmsCodeService {
    String generateAndSend(SmsCodeDTO smsCodeDTO);

    boolean validate(String key, String value, CacheType cacheType);
}
