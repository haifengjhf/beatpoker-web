package com.jhf.beatpoker.web.controller;

import com.jhf.beatpoker.web.common.bean.LoginResultBean;
import com.jhf.beatpoker.web.common.response.EnumStatusCode;
import com.jhf.beatpoker.web.common.response.ResponseBody;
import com.jhf.beatpoker.web.service.userregister.MasterUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestHeader;

@RestController
@RequestMapping("/masterUser")
public class MasterUserController {
    private final Logger logger = LoggerFactory.getLogger("MasterUserService");

    private final MasterUserService userRegisterService;

    @Autowired
    public MasterUserController(MasterUserService userRegisterService){
        this.userRegisterService = userRegisterService;
    }


    @GetMapping("/register")
    public ResponseBody<String> register(@RequestParam String emailAddress, @RequestParam String nickName) {
        logger.info("register emailAddress:{}  nickName:{}",emailAddress,nickName);

        EnumStatusCode statusCode = userRegisterService.register(emailAddress,nickName);
        return new ResponseBody<>(statusCode);
    }

    @GetMapping("/login")
    public ResponseBody<LoginResultBean> login(@RequestParam String emailAddress, @RequestParam String password) {
        return userRegisterService.login(emailAddress,password);
    }

    @GetMapping("/loginWithToken")
    public ResponseBody<String> loginWithToken(@RequestParam String userId, @RequestHeader("token") String token) {
        EnumStatusCode statusCode = userRegisterService.loginWithToken(userId,token);

        return new ResponseBody<>(statusCode);
    }

    @GetMapping("/refreshToken")
    public ResponseBody<LoginResultBean> refreshToken(String userId,@RequestHeader("token") String token){
        return userRegisterService.refreshToken(userId,token);
    }

    @GetMapping("/changePassword")
    public ResponseBody<String> changePassword(String userId,@RequestHeader("token") String token,String newPassword){
        EnumStatusCode statusCode = userRegisterService.changePassword(userId,token,newPassword);
        return new ResponseBody<>(statusCode);
    }

    @GetMapping("/resetPassword")
    public ResponseBody<String> resetPassword(String emailAddress){
        EnumStatusCode statusCode = userRegisterService.resetPassword(emailAddress);
        return new ResponseBody<>(statusCode);
    }

    @GetMapping("/confirmResetPassword")
    public ResponseBody<String> confirmResetPassword(String emailAddress,String verificationCode){
        EnumStatusCode statusCode = userRegisterService.confirmResetPassword(emailAddress,verificationCode);
        return new ResponseBody<>(statusCode);
    }

    @GetMapping("/changeNickName")
    public ResponseBody<String> changeNickName(String userId,@RequestHeader("token") String token,String newNickName){
        EnumStatusCode statusCode = userRegisterService.changeNickName(userId,token,newNickName);
        return new ResponseBody<>(statusCode);
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
