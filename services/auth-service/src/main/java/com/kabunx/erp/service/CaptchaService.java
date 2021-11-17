package com.kabunx.erp.service;

import com.kabunx.erp.domain.dto.LoginKeyValueDTO;

public interface CaptchaService {
    boolean verifyKeyValue(LoginKeyValueDTO loginKeyValueDTO);
}
