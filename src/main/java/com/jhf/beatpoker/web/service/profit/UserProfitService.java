package com.jhf.beatpoker.web.service.profit;

import com.jhf.beatpoker.web.common.bean.ProfitListDataBean;
import com.jhf.beatpoker.web.common.response.EnumStatusCode;
import com.jhf.beatpoker.web.common.utils.TextUtils;
import com.jhf.beatpoker.web.dao.mapper.IUserProfitDao;
import com.jhf.beatpoker.web.service.userregister.MasterUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserProfitService {
    private final int TOP_NUMBER_MAX = 100;
    private final int TOP_NUMBER_MIN = 1;
    private final MasterUserService masterUserService;
    private final IUserProfitDao userProfitDao;

    @Autowired
    public UserProfitService(IUserProfitDao userProfitDao, MasterUserService masterUserService){
        this.userProfitDao = userProfitDao;
        this.masterUserService = masterUserService;
    }

    public EnumStatusCode updateProfit(String userId, String token, int diffScore){
        if(TextUtils.isEmpty(userId) || TextUtils.isEmpty(token)){
            return EnumStatusCode.FAILED_PARAMETER_EMPTY_ERROR;
        }

        EnumStatusCode enumStatusCode = masterUserService.isUserLoginValid(userId,token);
        if(enumStatusCode != EnumStatusCode.SUCCESS){
            return enumStatusCode;
        }

        int result = userProfitDao.updateProfit(userId,new Date(),diffScore);
        return result == 1 || result == 2 ? EnumStatusCode.SUCCESS : EnumStatusCode.FAILED_DB_UPDATE_SCORE_ERROR;
    }

    public ProfitListDataBean getTopProfit(int topN,String curUserId){
        if(topN < TOP_NUMBER_MIN || topN > TOP_NUMBER_MAX) {
            topN = TOP_NUMBER_MAX;
        }
        return new ProfitListDataBean(userProfitDao.getTopN(topN),userProfitDao.getUserProfit(curUserId));
    }

}
