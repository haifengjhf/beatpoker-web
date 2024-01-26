package com.jhf.beatpoker.web.common.response;

public class ResponseBody<T> {
    public int code;
    public String message;
    public T data;

    public ResponseBody(){
        code = EnumStatusCode.SUCCESS.getCode();
        message = EnumStatusCode.SUCCESS.getMessage();
        data = null;
    }

    public ResponseBody(T object){
        code = EnumStatusCode.SUCCESS.getCode();
        message = EnumStatusCode.SUCCESS.getMessage();
        data = object;
    }

    public ResponseBody(T object,EnumStatusCode statusCode){
        code = statusCode.getCode();
        message = statusCode.getMessage();
        data = object;
    }

    public ResponseBody(EnumStatusCode statusCode){
        code = statusCode.getCode();
        message = statusCode.getMessage();
    }
}
