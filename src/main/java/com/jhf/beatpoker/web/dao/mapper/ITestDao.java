package com.jhf.beatpoker.web.dao.mapper;

import com.jhf.beatpoker.web.dao.entity.MasterUserRow;

import java.util.List;

public interface ITestDao {
    /**
     * 根据id查询用户信息
     *
     * @param userId
     * @return
     */
    MasterUserRow queryUserByUserId(String userId);

    /**
     * 查询所有用户信息
     *
     * @return
     */
    List<MasterUserRow> queryUserAll();

    /**
     * 新增用户
     *
     * @param masterUser
     */
    void insertUser(MasterUserRow masterUser);

    /**
     * 更新用户信息
     *
     * @param masterUser
     */
    void updateUser(MasterUserRow masterUser);

    /**
     * 根据id删除用户信息
     *
     * @param id
     */
    void deleteUser(String id);
}
