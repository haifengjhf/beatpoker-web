package com.jhf.beatpoker.web.dao.entity;

import java.util.Date;

public class MasterUser {
    //uuid
    private String userId;
    private String emailAddress;//邮箱地址
    //用户密码的md5保存
    private String password;
    private String nickName;//显示昵称
    private Date createdTime;

    private String token;
    private Date expiredTime;
    private int status;//账号状态，是否无效  ConstUtils.ACCOUNT_STATUS_NORMAL

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(Date expiredTime) {
        this.expiredTime = expiredTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "MasterUser{" +
                "userId='" + userId + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", password='" + password + '\'' +
                ", nickName='" + nickName + '\'' +
                ", createdTime=" + createdTime +
                ", token='" + token + '\'' +
                ", expiredTime=" + expiredTime +
                ", status=" + status +
                '}';
    }
}
