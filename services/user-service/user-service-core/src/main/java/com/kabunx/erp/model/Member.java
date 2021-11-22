package com.kabunx.erp.model;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.kabunx.erp.base.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.DateTypeHandler;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "gb_user_mpc", autoResultMap = true)
public class Member extends Model {
    @TableId(type = IdType.INPUT)
    Integer userId;

    String position;

    Integer status;

    String accessToken;

    @TableField(typeHandler = DateTypeHandler.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    Date tokenExpiredTime;

    String openid;

    String unionid;

    Integer addType;

    String ch;

    @TableField(typeHandler = DateTypeHandler.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    Date addTime;

    @TableField(typeHandler = DateTypeHandler.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    Date lastLogin;

    @TableLogic
    Boolean isDeleted;

    @TableField(exist = false)
    User user;
}
