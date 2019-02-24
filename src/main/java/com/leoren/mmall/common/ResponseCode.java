package com.leoren.mmall.common;

import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName ResponseCode
 * @Auther Leoren
 * @Date 2019/2/15 23:12
 * @Desc : 响应的各种码
 * @Version v1.0
 **/
public enum ResponseCode {

    SUCCESS(0,"SUCCESS"),
    ERROR(1,"ERROR"),
    NEED_LOGIN(10,"NEED_LOGIN"),
    ILLEGAL_ARGUMENT(2,"ILLEGAL_ARGUMENT");

    private final int code;
    private final String desc;

    ResponseCode(int code, String desc){
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }}
