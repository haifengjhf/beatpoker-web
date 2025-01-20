package com.jhf.beatpoker.web.dao.entity;

import java.util.Date;

public class PasswordResetRow {
    private String emailAddress;

    private Date expiredTime;
    private String verificationCode;

    public PasswordResetRow(){}

    public PasswordResetRow(String emailAddress, Date expiredTime, String verificationCode){
        this.emailAddress = emailAddress;
        this.expiredTime = expiredTime;
        this.verificationCode = verificationCode;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public Date getExpiredTime() {
        return expiredTime;
    }

    public String getVerificationCode() {
        return verificationCode;
    }
}
