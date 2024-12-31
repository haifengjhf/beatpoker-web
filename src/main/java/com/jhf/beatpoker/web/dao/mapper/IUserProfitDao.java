package com.jhf.beatpoker.web.dao.mapper;

import com.jhf.beatpoker.web.common.bean.UserProfitBean;

import java.util.Date;
import java.util.List;

public interface IUserProfitDao {
    int updateProfit(String userId, Date lastUpdateTime, int diffScore);

    List<UserProfitBean> getTopN(int topN);
}
