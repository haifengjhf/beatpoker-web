package com.jhf.beatpoker.web.common.bean;

import java.util.List;

public class ProfitListDataBean {
    protected List<UserProfitBean> topProfitList;
    protected UserProfitBean curUserProfit;

    public ProfitListDataBean(){}

    public ProfitListDataBean(List<UserProfitBean> topProfitList,UserProfitBean curUserProfit){
        this.topProfitList = topProfitList;
        this.curUserProfit = curUserProfit;
    }

    public List<UserProfitBean> getTopProfitList() {
        return topProfitList;
    }

    public UserProfitBean getCurUserProfit() {
        return curUserProfit;
    }
}
