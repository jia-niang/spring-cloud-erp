package com.kabunx.erp.vo;

import com.kabunx.erp.pojo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
public class MemberVO extends BaseVO {
    private Long userId;

    private String accessToken;

    Date tokenExpiredTime;

    private UserVO user;
}
