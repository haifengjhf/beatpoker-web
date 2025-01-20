package com.jhf.beatpoker.web.controller;

import com.jhf.beatpoker.web.common.bean.ProfitListDataBean;
import com.jhf.beatpoker.web.common.response.ResponseBody;
import com.jhf.beatpoker.web.service.profit.UserProfitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/userProfit")
public class UserProfitController {
    private final UserProfitService userCreditsService;

    @Autowired
    public UserProfitController(UserProfitService userProfitService){
        this.userCreditsService = userProfitService;
    }

    @GetMapping("/updateProfit")
    public ResponseBody<String> updateProfit(String userId, @RequestHeader("token") String token, int diffScore) {
        return new ResponseBody<>(userCreditsService.updateProfit(userId,token,diffScore));
    }

    @GetMapping("/getTopProfit")
    public ResponseBody<ProfitListDataBean> getTopProfit(int topN, String curUserId) {
        return new ResponseBody<>(userCreditsService.getTopProfit(topN,curUserId));
    }

}
