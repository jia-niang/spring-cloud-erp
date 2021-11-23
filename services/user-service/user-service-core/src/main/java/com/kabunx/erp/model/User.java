package com.kabunx.erp.model;

import com.baomidou.mybatisplus.annotation.*;
import com.kabunx.erp.pojo.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_user_basic", autoResultMap = true)
public class User extends Model {
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

//    @TableField(typeHandler = DateTypeHandler.class)
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    Date expireTime;
    Integer isOpen;
    Integer isCiLogin;
    Integer lastLogin;
    Integer profileSource;

//    @TableField(typeHandler = DateTypeHandler.class)
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    Date addTime;

    @TableLogic
    Boolean isDeleted;
}
