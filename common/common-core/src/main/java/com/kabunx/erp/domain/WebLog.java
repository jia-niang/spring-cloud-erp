package com.kabunx.erp.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 统一的日志结构体
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WebLog implements Serializable {

    /**
     * IP地址
     */
    private String ip;

    /**
     * 请求类型
     */
    private String method;

    /**
     * URL
     */
    private String uri;

    /**
     * 请求参数
     */
    private Object args;

    /**
     * 请求类和方法
     */
    private String signature;
}
