package com.kabunx.erp.domain.dto;

import com.kabunx.erp.pojo.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = true)
public class LoginSmsCodeDTO extends BaseDTO {

    @NotNull
    @NotEmpty
    String phone;

    @NotNull
    @NotEmpty
    String smsKey;

    @NotNull
    @NotEmpty
    String smsCode;
}
