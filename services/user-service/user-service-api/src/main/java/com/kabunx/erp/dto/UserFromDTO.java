package com.kabunx.erp.dto;

import com.kabunx.erp.pojo.BaseDTO;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserFromDTO extends BaseDTO {
    @NotNull(message = "用户名不能为NULL")
    @NotEmpty(message = "用户名不能为空")
    String username;

    @NotNull(message = "密码不能为NULL")
    @NotEmpty(message = "密码不能为空")
    String password;

    @NotNull(message = "登录类型不能为NULL")
    @NotEmpty(message = "登录类型不能为空")
    String type;

    String testKey;
}
