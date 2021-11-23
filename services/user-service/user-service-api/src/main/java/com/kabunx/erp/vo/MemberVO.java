package com.kabunx.erp.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.kabunx.erp.pojo.BaseVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class MemberVO extends BaseVO {
    private Integer userId;

    private String accessToken;

    Date tokenExpiredTime;

    @JSONField(name = "user")
    private UserVO user;
}
