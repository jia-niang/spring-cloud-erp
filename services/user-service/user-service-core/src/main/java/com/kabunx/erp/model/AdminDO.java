package com.kabunx.erp.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.kabunx.erp.pojo.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.DateTypeHandler;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "gb_user_hh", autoResultMap = true)
public class AdminDO extends BaseDO {
    @TableId
    Integer userId;

    String token;

    /**
     * 鉴权token
     */
    String accessToken;

    /**
     * 来源
     */
    Integer addType;

    /**
     * 部门id
     */
    Integer departmentId;

    /**
     * 会员等级 1普通会员 2VIP会员 3试用会员 4招聘管家 5七天会员 6内部员工
     */
    @TableField("is_free")
    Integer vipLevel;

    /**
     * vip到期时间
     */
    Date vipExpire;

    /**
     * 微信服务号？
     */
    String mpOpenId;

    /**
     * 状态(1:开启,2:禁用)
     */
    Integer status;

    @TableField(typeHandler = DateTypeHandler.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    Date auditTime;

    Integer addTime;

    Integer updateTime;

    Integer activeTime;

    @TableLogic
    Integer isDeleted;
}
