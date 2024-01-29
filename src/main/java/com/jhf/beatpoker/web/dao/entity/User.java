package com.jhf.beatpoker.web.dao.entity;

import com.jhf.beatpoker.web.common.bean.UserRegisterRequestBean;
import com.jhf.beatpoker.web.common.utils.UserUtils;

import java.util.Date;
import java.util.UUID;

public class User {
    //uuid
    private String userId;
    private String emailAddress;//邮箱地址
    //用户密码的md5保存
    private String password;
    private String nickName;//显示昵称

    private Date createdTime;

    public User(){
    }

    public User(UserRegisterRequestBean userRegisterBean){
        this.setUserId(UserUtils.formatUserId());
        this.setEmailAddress(userRegisterBean.emailAddress);
        this.setPassword(userRegisterBean.password);
        this.setNickName(userRegisterBean.nickName);
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public String getNickName() {
        return nickName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

//    public Date getCreatedTime() {
//        return createdTime;
//    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

//    public void setCreatedTime(Date createdTime) {
//        this.createdTime = createdTime;
//    }

    @Override
    public String toString() {
        return "User{" +
                ", userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                ", nickName='" + nickName + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", createdTime='" + createdTime + '\'' +
//                ", created='" + new SimpleDateFormat("yyyy-MM-dd").format(createdTime) + '\'' +
                '}';
    }
}
