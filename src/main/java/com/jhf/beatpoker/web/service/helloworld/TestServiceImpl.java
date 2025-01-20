package com.jhf.beatpoker.web.service.helloworld;

import com.jhf.beatpoker.web.common.bean.HelloBean;
import com.jhf.beatpoker.web.dao.entity.MasterUserRow;
import org.springframework.stereotype.Service;

@Service("TestService")
public class TestServiceImpl implements ITestService {
//    @Autowired
//    private ITestDao mTestData;

//    private final ITestDao mTestData;

//    @Autowired
//    public TestServiceImpl(ITestDao testData) {
//        this.mTestData = testData;
//    }


    @Override
    public HelloBean test() {
        HelloBean helloBean = new HelloBean();
        MasterUserRow masterUser = null;
//        User user = mTestData.queryUserByUserId("defaultId");
        if(masterUser == null){
            return helloBean;
        }
        System.out.println(masterUser.toString());
        helloBean.id = masterUser.getUserId();
        helloBean.name = masterUser.getNickName();
        return helloBean;
    }
}
