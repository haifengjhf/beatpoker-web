package com.jhf.beatpoker.web.service.userregister;

import com.jhf.beatpoker.web.common.bean.LoginResultBean;
import com.jhf.beatpoker.web.common.response.EnumStatusCode;
import com.jhf.beatpoker.web.common.response.ResponseBody;
import com.jhf.beatpoker.web.common.utils.*;
import com.jhf.beatpoker.web.dao.entity.PasswordResetRow;
import com.jhf.beatpoker.web.dao.entity.MasterUserRow;
import com.jhf.beatpoker.web.dao.mapper.IPasswordResetDao;
import com.jhf.beatpoker.web.dao.mapper.IMasterUserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class MasterUserService {
    private final Logger logger = LoggerFactory.getLogger("MasterUserService");
    private final int TOKEN_VALID_TIME = 7 * 24 * 3600 * 1000;
    private final int RESET_PASSWORD_URL_VALID_TIME = 30 * 60 * 1000;
    private final IMasterUserDao mMasterUserDaoImpl;
    private final IPasswordResetDao mEmailResetDaoImpl;

    @Autowired
    public MasterUserService(IMasterUserDao iMasterUserDao, IPasswordResetDao emailResetDao){
        this.mMasterUserDaoImpl = iMasterUserDao;
        this.mEmailResetDaoImpl = emailResetDao;
    }

    public EnumStatusCode register(String emailAddress,String nickName){
        if(TextUtils.isEmpty(emailAddress)  || TextUtils.isEmpty(nickName)){
            return EnumStatusCode.FAILED_PARAMETER_EMPTY_ERROR;
        }
        String userId = UserUtils.formatUserId(emailAddress);
        String randomPassword = RandomGenerator.generateRandom(6);
        String dbPassword = UserUtils.formatDbPassword(emailAddress,randomPassword);

        MasterUserRow masterUser = mMasterUserDaoImpl.selectUser(userId);
        if(masterUser != null){
            return EnumStatusCode.FAILED_ACCOUNT_EXISTS;
        }

        EnumStatusCode statusCode = EmailUtils.sendNewPasswordEmail(emailAddress,randomPassword);
        if(statusCode != EnumStatusCode.SUCCESS){
            return statusCode;
        }

        masterUser = new MasterUserRow();
        masterUser.setUserId(userId);
        masterUser.setPassword(dbPassword);
        masterUser.setEmailAddress(emailAddress);
        masterUser.setNickName(nickName);
        masterUser.setCreatedTime(new Date());
        masterUser.setStatus(ConstUtils.ACCOUNT_STATUS_NORMAL);

        int result = mMasterUserDaoImpl.insertUserFirstTime(masterUser);
        logger.info("register user:{},result:{}", masterUser,result);
        return result == 1 ? EnumStatusCode.SUCCESS : EnumStatusCode.FAILED_ACCOUNT_EXISTS;
    }

    public ResponseBody<LoginResultBean> login(String emailAddress, String password){
        if(TextUtils.isEmpty(emailAddress) || TextUtils.isEmpty(password)){
            return new ResponseBody(EnumStatusCode.FAILED_PARAMETER_EMPTY_ERROR);
        }
        EnumStatusCode enumStatusCode = EnumStatusCode.SUCCESS;
        String userId = UserUtils.formatUserId(emailAddress);
        String newToken = UserUtils.formatToken(userId);
        MasterUserRow masterUserInfo = mMasterUserDaoImpl.selectUser(userId);
        //check password
        if(masterUserInfo == null){
            enumStatusCode = EnumStatusCode.FAILED_USER_NOT_FOUND_EXCEPTION;
            return getLoginResultBeanResponseBody(enumStatusCode,"","", "","");
        }
        else if(!masterUserInfo.getPassword().equalsIgnoreCase(password)){
            enumStatusCode = EnumStatusCode.FAILED_PASSWORD_WRONG_EXCEPTION;
            return getLoginResultBeanResponseBody(enumStatusCode,"","", "","");
        }else{
            Date expiredTime = new Date(System.currentTimeMillis() + TOKEN_VALID_TIME);
            mMasterUserDaoImpl.updateToken(userId,newToken,expiredTime);
            return getLoginResultBeanResponseBody(enumStatusCode,userId,emailAddress, newToken,masterUserInfo.getNickName());
        }

    }

    public EnumStatusCode loginWithToken(String userId,String token){
        return isUserLoginValid(userId,token);
    }

    public ResponseBody<LoginResultBean> refreshToken(String userId,String token){
        EnumStatusCode enumStatusCode = EnumStatusCode.SUCCESS;
        if(TextUtils.isEmpty(userId) || TextUtils.isEmpty(token)){
            return new ResponseBody(EnumStatusCode.FAILED_PARAMETER_EMPTY_ERROR);
        }

        MasterUserRow masterUserInfo = mMasterUserDaoImpl.selectUser(userId);
        String newToken = UserUtils.formatToken(userId);
        if(masterUserInfo == null){
            enumStatusCode = EnumStatusCode.FAILED_USER_NOT_FOUND_EXCEPTION;
        }
        else if(masterUserInfo.getToken() == null || !masterUserInfo.getToken().equalsIgnoreCase(token)){
            enumStatusCode = EnumStatusCode.FAILED_TOKEN_ERROR_EXCEPTION;
        }else{
            Date expiredTime = new Date(System.currentTimeMillis() + TOKEN_VALID_TIME);

            mMasterUserDaoImpl.updateToken(userId,newToken,expiredTime);

            return getLoginResultBeanResponseBody(enumStatusCode,userId,masterUserInfo.getEmailAddress(),  newToken,masterUserInfo.getNickName());
        }

        ResponseBody<LoginResultBean>  responseBody = new ResponseBody<>();
        responseBody.code = enumStatusCode.getCode();
        responseBody.message = enumStatusCode.getMessage();
        return responseBody;
    }

    public EnumStatusCode changePassword(String userId,String token,String newPassword){
        EnumStatusCode enumStatusCode = EnumStatusCode.SUCCESS;
        if(TextUtils.isEmpty(userId) || TextUtils.isEmpty(token) || TextUtils.isEmpty(newPassword)){
            return EnumStatusCode.FAILED_PARAMETER_EMPTY_ERROR;
        }
        enumStatusCode = isUserLoginValid(userId,token);
        if(enumStatusCode != EnumStatusCode.SUCCESS){
            return enumStatusCode;
        }
        else{
            int result = mMasterUserDaoImpl.updatePassword(userId,newPassword);
            if(result != 1){
                enumStatusCode = EnumStatusCode.FAILED_DB_UPDATE_PASSWORD_ERROR;
            }
        }
        return enumStatusCode;
    }

    public EnumStatusCode resetPassword(String emailAddress){
        EnumStatusCode enumStatusCode = EnumStatusCode.SUCCESS;
        if(TextUtils.isEmpty(emailAddress)){
            return EnumStatusCode.FAILED_PARAMETER_EMPTY_ERROR;
        }

        String userId = UserUtils.formatUserId(emailAddress);
        MasterUserRow masterUserInfo = mMasterUserDaoImpl.selectUser(userId);
        if(masterUserInfo == null){
            enumStatusCode = EnumStatusCode.FAILED_USER_NOT_FOUND_EXCEPTION;
        }
        else if(!EmailUtils.isValidEmail(emailAddress)){
            enumStatusCode = EnumStatusCode.FAILED_EMAIL_FORMAT_ERROR;
        }
        else{
            String verificationCode = RandomGenerator.generateNumberRandom(6);
            int result = mEmailResetDaoImpl.updateVerificationCode(new PasswordResetRow(emailAddress,new Date(),verificationCode));
            if(result == 0){
                enumStatusCode = EnumStatusCode.FAILED_DB_UPDATE_PASSWORD_ERROR;
            }else{
                enumStatusCode = EmailUtils.sendVerificationCodeEmail(emailAddress,verificationCode);
            }
        }
        return enumStatusCode;
    }

    public EnumStatusCode confirmResetPassword(String emailAddress, String verificationCode) {
        EnumStatusCode enumStatusCode = EnumStatusCode.SUCCESS;
        if(TextUtils.isEmpty(emailAddress) || TextUtils.isEmpty(verificationCode)){
            return EnumStatusCode.FAILED_PARAMETER_EMPTY_ERROR;
        }

        String userId = UserUtils.formatUserId(emailAddress);
        MasterUserRow masterUserInfo = mMasterUserDaoImpl.selectUser(userId);
        PasswordResetRow emailResetRow = mEmailResetDaoImpl.getEmailReset(emailAddress);
        if(masterUserInfo == null){
            enumStatusCode = EnumStatusCode.FAILED_USER_NOT_FOUND_EXCEPTION;
        }
        else if(!EmailUtils.isValidEmail(emailAddress)){
            enumStatusCode = EnumStatusCode.FAILED_EMAIL_FORMAT_ERROR;
        }
        else if(emailResetRow == null || !emailResetRow.getVerificationCode().equalsIgnoreCase(verificationCode)){
            enumStatusCode = EnumStatusCode.FAILED_RESET_VERIFICATION_CODE_ERROR;
        }
        else {
            String randomPassword = RandomGenerator.generateRandom(6);
            String dbPassword = UserUtils.formatDbPassword(emailAddress,randomPassword);
            int result = mMasterUserDaoImpl.updatePassword(userId,dbPassword);
            if(result == 0){
                enumStatusCode = EnumStatusCode.FAILED_DB_UPDATE_PASSWORD_ERROR;
            }else{
                enumStatusCode = EmailUtils.sendNewPasswordEmail(emailAddress,randomPassword);
            }
        }
        return enumStatusCode;
    }

    public EnumStatusCode changeNickName(String userId,String token,String newNickName){
        if(TextUtils.isEmpty(userId) || TextUtils.isEmpty(token) || TextUtils.isEmpty(newNickName)){
            return EnumStatusCode.FAILED_PARAMETER_EMPTY_ERROR;
        }
        EnumStatusCode enumStatusCode = isUserLoginValid(userId,token);
        if(enumStatusCode != EnumStatusCode.SUCCESS){
            return enumStatusCode;
        }
        int result = mMasterUserDaoImpl.updateNickName(userId,newNickName);
        return result == 1 ? EnumStatusCode.SUCCESS : EnumStatusCode.FAILED_DB_UPDATE_NICKNAME_ERROR;
    }

    public EnumStatusCode isUserLoginValid(String userId,String token){
        EnumStatusCode enumStatusCode = EnumStatusCode.SUCCESS;
        if(TextUtils.isEmpty(userId) || TextUtils.isEmpty(token)){
            return EnumStatusCode.FAILED_PARAMETER_EMPTY_ERROR;
        }
        MasterUserRow masterUserInfo = mMasterUserDaoImpl.selectUser(userId);
        logger.debug("isUserLoginValid userInfo:{},userId:{},token:{}", masterUserInfo,userId,token);
        if(masterUserInfo == null ){
            enumStatusCode = EnumStatusCode.FAILED_USER_NOT_FOUND_EXCEPTION;
        }
        else if(masterUserInfo.getToken() == null || !masterUserInfo.getToken().equalsIgnoreCase(token)){
            enumStatusCode = EnumStatusCode.FAILED_TOKEN_ERROR_EXCEPTION;
        }
        else if(masterUserInfo.getExpiredTime() == null || masterUserInfo.getExpiredTime().before(new Date())){
            enumStatusCode = EnumStatusCode.FAILED_TOKEN_EXPIRED_EXCEPTION;
        }
        return enumStatusCode;
    }

    private ResponseBody<LoginResultBean> getLoginResultBeanResponseBody( EnumStatusCode enumStatusCode,String userId,String emailAddress,String newToken,String nickName) {
        ResponseBody<LoginResultBean>  responseBody = new ResponseBody<>();
        responseBody.code = enumStatusCode.getCode();
        responseBody.message = enumStatusCode.getMessage();
        if(responseBody.code == EnumStatusCode.SUCCESS.getCode()){
            responseBody.data = new LoginResultBean();
            responseBody.data.userId = userId;
            responseBody.data.emailAddress = emailAddress;
            responseBody.data.token = newToken;
            responseBody.data.nickName = nickName;
        }
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
