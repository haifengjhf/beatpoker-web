package com.jhf.beatpoker.web.service.userregister;

import com.jhf.beatpoker.web.common.bean.LoginResultBean;
import com.jhf.beatpoker.web.common.response.EnumStatusCode;
import com.jhf.beatpoker.web.common.response.ResponseBody;
import com.jhf.beatpoker.web.common.utils.ConstUtils;
import com.jhf.beatpoker.web.common.utils.UserUtils;
import com.jhf.beatpoker.web.dao.entity.User;
import com.jhf.beatpoker.web.dao.mapper.IMasterUserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;

@Service
public class MasterUserService {
    private final Logger logger = LoggerFactory.getLogger("MasterUserService");
    private final int TOKEN_VALID_TIME = 7 * 24 * 3600 * 1000;
    private final IMasterUserDao mMasterUserDaoImpl;

    @Autowired
    public MasterUserService(IMasterUserDao iMasterUserDao){
        this.mMasterUserDaoImpl = iMasterUserDao;
    }

    public EnumStatusCode register(String emailAddress,String password,String nickName){
        String userId = DigestUtils.md5DigestAsHex(emailAddress.getBytes());

        User user = new User();
        user.setUserId(userId);
        user.setPassword(password);
        user.setEmailAddress(emailAddress);
        user.setNickName(nickName);
        user.setCreatedTime(new Date());
        user.setStatus(ConstUtils.ACCOUNT_STATUS_NORMAL);

        int result = mMasterUserDaoImpl.insertUserFirstTime(user);
        logger.info("register user:{},result:{}", user,result);
        return result == 1 ? EnumStatusCode.SUCCESS : EnumStatusCode.FAILED_ACCOUNT_EXISTS;
    }

    public ResponseBody<LoginResultBean> login(String emailAddress, String password){
        EnumStatusCode enumStatusCode = EnumStatusCode.SUCCESS;
        String userId = DigestUtils.md5DigestAsHex(emailAddress.getBytes());
        String newToken = UserUtils.formatToken(userId);
        User userInfo = mMasterUserDaoImpl.selectUser(userId);
        //check password
        if(userInfo == null){
            enumStatusCode = EnumStatusCode.FAILED_USER_NOT_FOUND_EXCEPTION;
        }
        else if(!userInfo.getPassword().equalsIgnoreCase(password)){
            enumStatusCode = EnumStatusCode.FAILED_PASSWORD_WRONG_EXCEPTION;
        }else{
            Date expiredTime = new Date(System.currentTimeMillis() + TOKEN_VALID_TIME);
            User user = new User();
            user.setUserId(userId);
            user.setToken(newToken);
            user.setExpiredTime(expiredTime);
            mMasterUserDaoImpl.updateToken(user);
        }
        return getLoginResultBeanResponseBody(enumStatusCode,userId, newToken);
    }

    public EnumStatusCode loginWithToken(String userId,String token){
        EnumStatusCode enumStatusCode = EnumStatusCode.SUCCESS;
        User userInfo = mMasterUserDaoImpl.selectUser(userId);
        if(userInfo == null){
            enumStatusCode = EnumStatusCode.FAILED_USER_NOT_FOUND_EXCEPTION;
        }
        else if(userInfo.getToken() == null || !userInfo.getToken().equalsIgnoreCase(token)){
            enumStatusCode = EnumStatusCode.FAILED_TOKEN_EXCEPTION;
        }
        else if(userInfo.getExpiredTime().before(new Date())){
            enumStatusCode = EnumStatusCode.FAILED_TOKEN_EXPIRED_EXCEPTION;
        }
        return enumStatusCode;
    }

    public ResponseBody<LoginResultBean> refreshToken(String userId,String token){
        EnumStatusCode enumStatusCode = EnumStatusCode.SUCCESS;
        User userInfo = mMasterUserDaoImpl.selectUser(userId);
        String newToken = UserUtils.formatToken(userId);
        if(userInfo == null){
            enumStatusCode = EnumStatusCode.FAILED_USER_NOT_FOUND_EXCEPTION;
        }
        else if(userInfo.getToken() == null || !userInfo.getToken().equalsIgnoreCase(token)){
            enumStatusCode = EnumStatusCode.FAILED_TOKEN_EXCEPTION;
        }else{
            Date expiredTime = new Date(System.currentTimeMillis() + TOKEN_VALID_TIME);

            User userInfoUpdate = new User();
            userInfoUpdate.setUserId(userId);
            userInfoUpdate.setToken(newToken);
            userInfoUpdate.setExpiredTime(expiredTime);
            mMasterUserDaoImpl.updateToken(userInfoUpdate);

            return getLoginResultBeanResponseBody(enumStatusCode,userId,  newToken);
        }

        ResponseBody<LoginResultBean>  responseBody = new ResponseBody<>();
        responseBody.code = enumStatusCode.getCode();
        responseBody.message = enumStatusCode.getMessage();
        return responseBody;
    }

    private ResponseBody<LoginResultBean> getLoginResultBeanResponseBody( EnumStatusCode enumStatusCode,String userId,String newToken) {
        ResponseBody<LoginResultBean>  responseBody = new ResponseBody<>();
        responseBody.code = enumStatusCode.getCode();
        responseBody.message = enumStatusCode.getMessage();
        responseBody.data = new LoginResultBean();
        responseBody.data.userId = userId;
        responseBody.data.token = newToken;
        return responseBody;
    }

//    public EnumStatusCode verify(String userId,String verifyCode){
//        return EnumStatusCode.SUCCESS;
//    }
//
//
//    public EnumStatusCode active(String userId){
//        int result = userDaoImpl.activeUser(userId);
//        return result == 1 ? EnumStatusCode.SUCCESS : EnumStatusCode.FAILED;
//    }
//
//    public EnumStatusCode sendVerifyMail(String userId,String verifyCode){
//        return EnumStatusCode.SUCCESS;
//    }
}
