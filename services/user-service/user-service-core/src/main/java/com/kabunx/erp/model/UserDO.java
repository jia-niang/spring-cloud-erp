package com.kabunx.erp.model;

import com.baomidou.mybatisplus.annotation.*;
import com.kabunx.erp.pojo.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.DateTypeHandler;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "user", autoResultMap = true)
public class UserDO extends BaseDO {
    @TableId(type = IdType.AUTO)
    Long id;
    String account;
    String email;
    String phone;
    String name;
    Integer sex;
    String avatar;
    String password;

    @TableField(typeHandler = DateTypeHandler.class)
    Date emailVerifiedAt;

    @TableField(typeHandler = DateTypeHandler.class, fill = FieldFill.INSERT)
    Date createdAt;

    @TableField(typeHandler = DateTypeHandler.class, fill = FieldFill.UPDATE)
    Date updatedAt;

    @TableLogic
    Integer deletedTime;

    @TableField(exist = false)
    MemberDO member;
}
