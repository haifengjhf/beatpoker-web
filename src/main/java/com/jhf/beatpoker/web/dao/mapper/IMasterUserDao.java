package com.jhf.beatpoker.web.dao.mapper;

import com.jhf.beatpoker.web.dao.entity.User;

import java.util.Date;

public interface IMasterUserDao {
    int insertUserFirstTime(User user);

    int updateToken(String userId, String token, Date expiredTime);

    int updatePassword(String userId,String password);

    User selectUser(String userId);

    int activeUser(String userId);
}
