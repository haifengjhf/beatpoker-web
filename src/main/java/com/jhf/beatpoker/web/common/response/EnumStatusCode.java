package com.jhf.beatpoker.web.common.response;

public enum  EnumStatusCode implements StatusCode{
    SUCCESS(200,"success"),
    FAILED(400,"unknown error"),
    FAILED_ACCOUNT_NOT_EXISTS(4001,"account error"),
    FAILED_EMAIL_ADDRESS_OR_PASSWORD_ERROR(4002,"emailAddress or password error"),
    FAILED_TOKEN_ERROR_EXCEPTION(4003,"password error"),
    FAILED_VERIFY_CODE_EXCEPTION(4004,"verify code error"),
    FAILED_LEADER_BOARD_EMPTY(4005,"leaderboard empty"),
    FAILED_PASSWORD_WRONG_EXCEPTION(4006,"password error"),
    FAILED_USER_NOT_FOUND_EXCEPTION(4007,"user not found"),
    FAILED_TOKEN_EXPIRED_EXCEPTION(4008,"login expired"),
    FAILED_ACCOUNT_EXISTS(4009,"account exist"),
    FAILED_EMAIL_FORMAT_ERROR(4010,"email format error"),
    FAILED_EMAIL_SEND_ERROR(4011,"email send error"),
    FAILED_DATE_UPDATE_PASSWORD_ERROR(4012,"db update password error"),
    ;

    private int code;
    private String message;


    private EnumStatusCode(int code,String message){
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
