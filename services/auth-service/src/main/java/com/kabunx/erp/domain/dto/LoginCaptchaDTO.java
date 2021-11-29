package com.kabunx.erp.domain.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = true)
public class LoginCaptchaDTO extends LoginAccountDTO {
    @NotNull
    @NotEmpty
    String captchaKey;

    @NotNull
    @NotEmpty
    String captchaCode;
}
