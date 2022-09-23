package com.ruoyi.web.common.enums;

public enum ExceptionEnum {

    NULL_REQUEST_PARAM(10001,"request param must not be null."),
    NULL_RESULT(10002,"cant query the result."),
    ADD_FAILED(10003,"add failed,or keyword existed."),
    UPDATE_FAILED(10004,"update failed."),
    WRITE_TO_DOCUMENT_FAILED(10005,"write to document failed."),
    DELETE_FAILED(10006,"id not existed or wrong, delete failed");

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
