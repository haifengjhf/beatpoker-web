package com.jhf.beatpoker.web.controller;

import com.jhf.beatpoker.web.common.response.ResponseBody;
import com.jhf.beatpoker.web.service.helloworld.ITestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class HelloWorld {
    private final ITestService mTestService;

    @Autowired
    public HelloWorld(@Qualifier("TestService")ITestService testService){
        mTestService = testService;
    }

    @RequestMapping("/helloworld")
    public ResponseBody HelloWorld(){
        return new ResponseBody();
    }

    @RequestMapping("/sql")
    public ResponseBody sql(){
        return new ResponseBody(mTestService.test());
    }
}
