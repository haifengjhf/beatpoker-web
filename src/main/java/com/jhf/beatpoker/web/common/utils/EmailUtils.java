package com.jhf.beatpoker.web.common.utils;

import com.jhf.beatpoker.web.common.response.EnumStatusCode;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.net.ssl.SSLSocketFactory;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailUtils {
    public static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static EnumStatusCode sendResetPasswordEmail(String email, String newPassword){

        String myEmailAdress = "12260723@qq.com";
        String authorizationCode = "rpatydpzppmgbhhe";
        String smtpHost = "smtp.qq.com";
        String smtpPort = "465";

        Properties props = new Properties();
        props.put("mail.smtp.host", smtpHost); // 你的 SMTP 服务器
        props.put("mail.smtp.port", smtpPort); // SMTP 端口
        props.put("mail.transport.protocol", "smtp");

        props.put("mail.smtp.auth", "true"); // 是否需要认证
        props.put("mail.smtp.starttls.enable", "true"); // 是否开启 STARTTLS
        props.put("mail.smtp.starttls.required", "true");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        Session session = Session.getDefaultInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(myEmailAdress, authorizationCode);
                    }
                });
        session.setDebug(true);

        try{
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myEmailAdress));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(email));
            message.setSubject("password reset");
            message.setText("new password is: " + newPassword);

            Transport.send(message);
        }catch (Exception e){
            e.printStackTrace();
            return EnumStatusCode.FAILED_EMAIL_SEND_ERROR;
        }

        return EnumStatusCode.SUCCESS;
    }
}
