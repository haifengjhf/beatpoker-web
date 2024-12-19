package com.jhf.beatpoker.web.controller;

import com.jhf.beatpoker.web.common.bean.LoginResultBean;
import com.jhf.beatpoker.web.common.response.EnumStatusCode;
import com.jhf.beatpoker.web.common.response.ResponseBody;
import com.jhf.beatpoker.web.service.userregister.MasterUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/MasterUser")
public class MasterUserController {
    private final MasterUserService userRegisterService;

    @Autowired
    public MasterUserController(MasterUserService userRegisterService){
        this.userRegisterService = userRegisterService;
    }


    @GetMapping(value = "/register")
    public ResponseBody<String> register(@RequestParam String emailAddress,@RequestParam String password,@RequestParam String nickName) {
        EnumStatusCode statusCode = userRegisterService.register(emailAddress,password,nickName);

        ResponseBody<String> responseBody = new ResponseBody<>();
        responseBody.code = statusCode.getCode();
        responseBody.message = statusCode.getMessage();
        return responseBody;
    }

    @GetMapping(value = "/login")
    public ResponseBody<LoginResultBean> login(@RequestParam String emailAddress, @RequestParam String password) {
        return userRegisterService.login(emailAddress,password);
    }

    @GetMapping(value = "/loginWithToken")
    public ResponseBody<String> loginWithToken(@RequestParam String userId, @RequestParam String token) {
        EnumStatusCode statusCode = userRegisterService.loginWithToken(userId,token);

        ResponseBody<String> responseBody = new ResponseBody<>();
        responseBody.code = statusCode.getCode();
        responseBody.message = statusCode.getMessage();
        return responseBody;
    }

    @GetMapping(value = "/refreshToken")
    public ResponseBody<LoginResultBean> refreshToken(String userId,String token){
        return userRegisterService.refreshToken(userId,token);
    }

//    @GetMapping(value = "/verify")
//    public ResponseBody<String> verify(@RequestParam String userId,@RequestParam String verify) {
//        ResponseBody<String> responseBody = new ResponseBody<>();
//        EnumStatusCode statusCode = userRegisterService.verify(userId,verify);
//        responseBody.code = statusCode.getCode();
//        responseBody.message = statusCode.getMessage();
//        return responseBody;
//    }
}
