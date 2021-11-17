package com.kabunx.erp.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountLoginDTO {

    @NotNull
    @NotEmpty
    private String account;

    @NotNull
    @NotEmpty
    private String password;
}
