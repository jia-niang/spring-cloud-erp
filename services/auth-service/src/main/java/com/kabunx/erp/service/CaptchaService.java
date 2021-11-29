package com.kabunx.erp.service;

import com.kabunx.erp.domain.dto.LoginKeyValueDTO;
import com.kabunx.erp.domain.vo.CaptchaVO;

public interface CaptchaService {
    CaptchaVO generate(String type);

    boolean verifyKeyValue(LoginKeyValueDTO loginKeyValueDTO);
}
