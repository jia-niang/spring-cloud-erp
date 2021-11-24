package com.kabunx.erp.domain.dto;

import com.kabunx.erp.pojo.BaseDTO;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginAccountDTO extends BaseDTO {

    @NotNull
    @NotEmpty
    private String account;

    @NotNull
    @NotEmpty
    private String password;
}
