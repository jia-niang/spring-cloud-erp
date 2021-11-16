package com.kabunx.erp.dto;

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
public class UserDTO {
    @NotNull
    @NotEmpty
    String username;

    @NotNull
    @NotEmpty
    String password;

    @NotNull
    @NotEmpty
    String type;
}
