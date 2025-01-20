package com.jhf.beatpoker.web.dao.mapper;

import com.jhf.beatpoker.web.dao.entity.MasterUserRow;

import java.util.Date;

public interface IMasterUserDao {
    int insertUserFirstTime(MasterUserRow masterUser);

    int updateToken(String userId, String token, Date expiredTime);

    int updatePassword(String userId,String password);

    MasterUserRow selectUser(String userId);

    int activeUser(String userId);

    int updateNickName(String userId,String nickName);
}
