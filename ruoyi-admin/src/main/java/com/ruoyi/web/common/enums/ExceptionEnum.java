package com.ruoyi.web.common.enums;

public enum ExceptionEnum {

    // 请求参数为空提示
    NULL_REQUEST_PARAM(10001,"请求参数不能为空"),

    // 查询不到结果提示
    NULL_RESULT(10002,"查询不到结果"),

    // 添加失败提示
    ADD_FAILED(10003,"添加失败，该内容已存在"),

    // 修改失败提示
    UPDATE_FAILED(10004,"修改失败"),

    // 关键字导入失败提示
    WRITE_TO_DOCUMENT_FAILED(10005,"写入关键字文档失败"),

    // 该内容不存在提示
    DELETE_FAILED(10006,"删除失败，该内容不存在"),

    // 参数解析错误提示
    NAME_ERROR(10007,"参数名称解析错误");

    private int errorCode;
    private String errorMsg;

    ExceptionEnum(int errorCode, String errorMsg){
        this.errorCode=errorCode;
        this.errorMsg=errorMsg;
    }

    public int getErrorCode(){
        return errorCode;
    }

    public String getErrorMsg(){
        return errorMsg;
    }
}
