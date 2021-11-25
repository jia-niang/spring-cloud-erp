package com.kabunx.erp.domain.dto;

import com.kabunx.erp.pojo.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = true)
public class LoginKeyValueDTO extends BaseDTO {
    @NotNull
    @NotEmpty
    String captchaKey;

    @NotNull
    @NotEmpty
    String captchaCode;
}
