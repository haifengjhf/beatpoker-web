package com.jhf.beatpoker.web.dao.entity;

public class UserDynamic {
    private String userId;
    //token 采用 userId + '-' + time + '-' + randomNumber
    private String token;
    //verifycode 采用password + time 取前6位字母
    private String verifyCode;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    @Override
    public String toString() {
        return "UserDynamicData{" +
                "userId='" + userId + '\'' +
                ", token='" + token + '\'' +
                ", verifyCode='" + verifyCode + '\'' +
                '}';
    }
}
