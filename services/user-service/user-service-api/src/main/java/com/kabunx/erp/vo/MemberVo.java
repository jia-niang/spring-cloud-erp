package com.kabunx.erp.vo;

import com.kabunx.erp.base.Vo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
public class MemberVo extends Vo {
    private Integer userId;

    private String accessToken;

    Date tokenExpiredTime;

    private UserVo userVo;
}
