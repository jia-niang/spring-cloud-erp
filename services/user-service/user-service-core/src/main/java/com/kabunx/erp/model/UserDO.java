package com.kabunx.erp.model;

import com.baomidou.mybatisplus.annotation.*;
import com.kabunx.erp.pojo.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.DateTypeHandler;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_user_basic", autoResultMap = true)
public class UserDO extends BaseDO {
    @TableId(type = IdType.AUTO)
    Integer id;
    String account;
    String password;
    String name;
    Integer sex;
    String avatar;
    String phone;
    String email;
    String companyName;
    String licenseUrl;
    Integer points;
    Integer inviterUid;
    Integer source;
    Integer status;
    Integer isExpire;

    @TableField(typeHandler = DateTypeHandler.class)
    Date expireTime;

    Integer isOpen;
    Integer isCiLogin;
    Integer lastLogin;
    Integer profileSource;

    @TableField(typeHandler = DateTypeHandler.class)
    Date addTime;

    @TableLogic
    Boolean isDeleted;
}
