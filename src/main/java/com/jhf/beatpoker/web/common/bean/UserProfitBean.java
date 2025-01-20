package com.jhf.beatpoker.web.common.bean;

import java.util.Date;

public class UserProfitBean {
    private String userId;
    private String nickName;
    private Date lastUpdateTime;
    private long score;
    private int ranking;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }


    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }
}
