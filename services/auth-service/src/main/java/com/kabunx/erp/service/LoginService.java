package com.kabunx.erp.service;

import com.kabunx.erp.domain.dto.LoginAccountDTO;
import com.kabunx.erp.domain.dto.LoginCaptchaDTO;
import com.kabunx.erp.domain.dto.LoginMiniAppDTO;
import com.kabunx.erp.domain.dto.LoginSmsCodeDTO;
import com.kabunx.erp.domain.vo.AuthTokenVO;

public interface LoginService {
    AuthTokenVO loginByAccountWithoutCaptcha(LoginAccountDTO loginAccountDTO);

    AuthTokenVO loginByCaptcha(LoginCaptchaDTO loginCaptchaDTO);

    AuthTokenVO loginBySmsCode(LoginSmsCodeDTO loginSmsCodeDTO);

    AuthTokenVO loginByMiniApp(LoginMiniAppDTO loginMiniAppDTO);
}
