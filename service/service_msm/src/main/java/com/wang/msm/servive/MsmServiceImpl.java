package com.wang.msm.servive;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.springframework.stereotype.Service;

@Service
public class MsmServiceImpl implements MsmService{
    public  String sendAuthCodeEmail(String email, String authCode) {
        String send;
        try {
            //String emailFinal=email+"@163.com";
            SimpleEmail mail = new SimpleEmail();
            mail.setHostName("smtp.qq.com");//发送邮件的服务器
            mail.setAuthentication("2596352411@qq.com","coxkszfgxjjsdifd");//刚刚记录的授权码，是开启SMTP的密码
            mail.setFrom("2596352411@qq.com","中国刑警");  //发送邮件的邮箱和发件人
            mail.setSSLOnConnect(true); //使用安全链接
            mail.addTo(email);//接收的邮箱
            //System.out.println("email"+email);
            mail.setSubject("注册验证码");//设置邮件的主题
            mail.setMsg("尊敬的用户:你好! 注册验证码为:" + authCode+" (有效期为五分钟)");//设置邮件的内容
            send = mail.send();
            //发送
            return send;
        } catch (EmailException e) {
            e.printStackTrace();
        }
        return null;
    }

}
