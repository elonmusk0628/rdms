package com.ruoyi.web.common.enums;

public enum ExceptionEnum {

    NULL_REQUEST_PARAM(10001,"请求参数不能为空"),
    NULL_RESULT(10002,"查询不到结果"),
    ADD_FAILED(10003,"添加失败，该内容已存在"),
    UPDATE_FAILED(10004,"修改失败"),
    WRITE_TO_DOCUMENT_FAILED(10005,"写入关键字文档失败"),
    DELETE_FAILED(10006,"删除失败，该内容不存在"),
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
