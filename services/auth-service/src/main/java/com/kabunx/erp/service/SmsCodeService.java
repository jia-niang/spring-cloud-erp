package com.kabunx.erp.service;

import com.kabunx.erp.domain.dto.LoginKeyValueDTO;

public interface SmsCodeService {
    boolean sendByLoginKeyValue(LoginKeyValueDTO loginKeyValueDTO);
}
