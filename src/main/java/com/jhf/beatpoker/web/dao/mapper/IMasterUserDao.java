package com.jhf.beatpoker.web.dao.mapper;

import com.jhf.beatpoker.web.dao.entity.MasterUser;

import java.util.Date;

public interface IMasterUserDao {
    int insertUserFirstTime(MasterUser masterUser);

    int updateToken(String userId, String token, Date expiredTime);

    int updatePassword(String userId,String password);

    MasterUser selectUser(String userId);

    int activeUser(String userId);

    int updateNickName(String userId,String nickName);
}
