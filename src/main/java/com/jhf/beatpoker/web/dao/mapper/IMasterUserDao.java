package com.jhf.beatpoker.web.dao.mapper;

import com.jhf.beatpoker.web.dao.entity.User;

public interface IMasterUserDao {
    int insertUserFirstTime(User user);

    int updateToken(User user);

    User selectUser(String userId);

    int activeUser(String userId);
}
