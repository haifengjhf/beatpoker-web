package com.jhf.beatpoker.web.common.bean;

public class UserRegisterRequestBean {
    public String emailAddress;//邮箱地址
    public String password;
    public String nickName;//显示昵称

    @Override
    public String toString() {
        return "UserRegisterRequestBean{" +
                "emailAddress='" + emailAddress + '\'' +
                ", password='" + password + '\'' +
                ", nickName='" + nickName + '\'' +
                '}';
    }
}
