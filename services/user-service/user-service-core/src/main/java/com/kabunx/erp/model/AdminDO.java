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
@TableName(value = "user_admin", autoResultMap = true)
public class AdminDO extends BaseDO {
    @TableId(type = IdType.INPUT)
    Long userId;

    /**
     * 鉴权token
     */
    String accessToken;

    /**
     * 来源
     */
    Integer createdType;

    /**
     * 会员等级 1普通会员 2VIP会员 3试用会员 4招聘管家 5七天会员 6内部员工
     */
    Integer vipLevel;

    /**
     * vip到期时间
     */
    Date vipExpiredAt;

    /**
     * 状态(1:开启,2:禁用)
     */
    Integer status;

    @TableField(typeHandler = DateTypeHandler.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    Date createdAt;

    @TableField(typeHandler = DateTypeHandler.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    Date updatedAt;

    @TableField(typeHandler = DateTypeHandler.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    Date activeTime;

    @TableLogic
    @TableField(typeHandler = DateTypeHandler.class)
    Integer deletedAt;
}
