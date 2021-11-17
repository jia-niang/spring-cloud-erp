package com.kabunx.erp.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginKeyValueDTO {
    @NotNull
    @NotEmpty
    String captchaKey;

    @NotNull
    @NotEmpty
    String captchaCode;
}
