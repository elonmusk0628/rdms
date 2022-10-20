package com.ruoyi.web.common.enums;

public enum ConstantEnum {

    // 状态良好
    STATUS_NORMAL("1","在用良好"),

    // 状态故障
    STATUS_FAULT("2","在用故障"),

    // 状态停用
    STATUS_DISABLE("3","停用");

    private String code;
    private String msg;

    ConstantEnum(String code, String msg){
        this.code=code;
        this.msg=msg;
    }

    public String getCode(){
        return code;
    }

    public String getMsg(){
        return msg;
    }
}
