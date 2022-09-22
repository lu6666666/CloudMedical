package com.medical.verify.service.impl;

import com.medical.utils.RandomUtils;
import com.medical.verify.model.Email;
import com.medical.verify.service.CodeService;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;
/**
 * @author Luo.X
 * @Description 登录邮箱验证实现类
 * @CreateTime 2022-09-18 15:08
 * @Version 1.0
 */
@Service
public class CodeServiceImpl implements CodeService {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    //@Cacheable(value = "verification_code",keyGenerator = "keyGenerator")
    @Override
    public String sendEmail(String email) {
        String code = "";
        try {
            HtmlEmail mail = new HtmlEmail();
            /*发送邮件的服务器*/
            mail.setHostName("smtp.163.com");
            mail.setSmtpPort(465);
            mail.setSSLOnConnect(true);
            /*不设置发送的消息有可能是乱码*/
            mail.setCharset("UTF-8");
            /*IMAP/SMTP服务的密码*/
            mail.setAuthentication(Email.EMAIL, Email.EMAIL_KEY);
            /*发送邮件的邮箱和发件人*/
            //mail.setFrom("发件邮箱", "发件人");
            mail.setFrom(Email.EMAIL);
            /*使用安全链接*/
            mail.setSSLOnConnect(true);
            /*接收的邮箱*/
            mail.addTo(email);
            /*验证码*/
            code = this.getCode();
            /*设置邮件的主题*/
            mail.setSubject("登录验证码");
            /*设置邮件的内容*/
            mail.setMsg("尊敬的用户：你好! 注册验证码为：" + code + "，请区分大小写(有效期为五分钟)");
            mail.send();//发送
        } catch (EmailException e) {
            e.printStackTrace();
            return "";
        }
        // 将验证码存入redis中
        redisTemplate.opsForValue().set("verification_code",code,5, TimeUnit.MINUTES);
        return code;
    }

    @Override
    public String getAliCode(String phone) {
        // 随机数验证码
        String code = RandomUtils.getSixBitRandom();


        /*DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI5tJC81R9o9xSbc4ZK2q3", "Xn59t4Hypmhlmf2Xnca33oYmL3ZpNV");
        *//** use STS Token
         DefaultProfile profile = DefaultProfile.getProfile(
         "<your-region-id>",           // The region ID
         "<your-access-key-id>",       // The AccessKey ID of the RAM account
         "<your-access-key-secret>",   // The AccessKey Secret of the RAM account
         "<your-sts-token>");          // STS Token
         **//*

        IAcsClient client = new DefaultAcsClient(profile);

        SendSmsRequest request = new SendSmsRequest();
        request.setSignName("阿里云短信测试");
        request.setTemplateCode("SMS_154950909");
        request.setPhoneNumbers("18438897188");
        request.setTemplateParam("{\"code\":\"" + code + "\"}");

        try {
            SendSmsResponse response = client.getAcsResponse(request);
            System.out.println(new Gson().toJson(response));
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            System.out.println("ErrCode:" + e.getErrCode());
            System.out.println("ErrMsg:" + e.getErrMsg());
            System.out.println("RequestId:" + e.getRequestId());
        }*/
        // 将验证码存入redis中
        redisTemplate.opsForValue().set(phone,code,5, TimeUnit.MINUTES);
        return code;
    }

    /**
     * 生成验证码
     * @return
     */
    public String getCode(){
        String code = "";
        String str = "abcdefghijkemnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRTUVWXYZ";
        char[] chars = str.toCharArray();
        int captain = chars.length;
        for (int i=0;i<6;i++){
            char aChar = chars[new Random().nextInt(captain)];
            code = code+aChar;
        }
        return code;
    }
}
