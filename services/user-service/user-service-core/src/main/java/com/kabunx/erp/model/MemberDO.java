package com.kabunx.erp.model;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.kabunx.erp.pojo.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.DateTypeHandler;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "user_member", autoResultMap = true)
public class MemberDO extends BaseDO {
    @TableId(type = IdType.INPUT)
    Long userId;

    Integer status;

    String accessToken;

    Date lastLoginTime;

    @TableField(typeHandler = DateTypeHandler.class, fill = FieldFill.DEFAULT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    Date createdAt;

    @TableField(typeHandler = DateTypeHandler.class, fill = FieldFill.UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    Date updatedAt;

    @TableLogic
    Integer deletedTime;
}
