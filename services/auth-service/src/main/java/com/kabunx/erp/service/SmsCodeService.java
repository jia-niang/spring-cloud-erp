package com.kabunx.erp.service;

import com.kabunx.erp.domain.dto.SmsCodeDTO;

public interface SmsCodeService {
    boolean send(SmsCodeDTO smsCodeDTO);
}
