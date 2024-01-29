package com.jhf.beatpoker.web.service.helloworld;

import com.jhf.beatpoker.web.common.bean.HelloBean;
import com.jhf.beatpoker.web.dao.entity.User;
import com.jhf.beatpoker.web.dao.mapper.ITestDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("TestService")
public class TestServiceImpl implements ITestService {
//    @Autowired
//    private ITestDao mTestData;

    private final ITestDao mTestData;

    @Autowired
    public TestServiceImpl(ITestDao testData) {
        this.mTestData = testData;
    }


    @Override
    public HelloBean test() {
        HelloBean helloBean = new HelloBean();
        User user = mTestData.queryUserByUserId("defaultId");
        if(user == null){
            return helloBean;
        }
        System.out.println(user.toString());
        helloBean.id = user.getUserId();
        helloBean.name = user.getNickName();
        return helloBean;
    }
}
