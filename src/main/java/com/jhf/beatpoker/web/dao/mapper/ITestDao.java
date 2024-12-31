package com.jhf.beatpoker.web.dao.mapper;

import com.jhf.beatpoker.web.dao.entity.MasterUser;

import java.util.List;

public interface ITestDao {
    /**
     * 根据id查询用户信息
     *
     * @param userId
     * @return
     */
    public MasterUser queryUserByUserId(String userId);

    /**
     * 查询所有用户信息
     *
     * @return
     */
    public List<MasterUser> queryUserAll();

    /**
     * 新增用户
     *
     * @param masterUser
     */
    public void insertUser(MasterUser masterUser);

    /**
     * 更新用户信息
     *
     * @param masterUser
     */
    public void updateUser(MasterUser masterUser);

    /**
     * 根据id删除用户信息
     *
     * @param id
     */
    public void deleteUser(String id);
}
