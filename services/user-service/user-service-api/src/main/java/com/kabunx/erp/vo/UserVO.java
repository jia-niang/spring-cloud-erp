package com.kabunx.erp.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kabunx.erp.pojo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserVO extends BaseVO {
    Long id;
    String account;
    String name;
    Integer sex;
    String type;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    Date createdAt;

    MemberVO member;

    List<MemberVO> members;

    List<RoleVO> roles;
}
