package com.dyw.rocketmq.Service.impl;

import com.dyw.rocketmq.Service.MailService;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;
import rocketmq.message.SendMessage;
import rocketmq.result.Result;
import rocketmq.vo.MailParam;

/**
 * @author Devil
 * @create 2022-02-26 15:08
 */
@Log4j2
@Service
public class MailServiceImpl implements MailService {

    @Value("${spring.mail.username}")
    String From;
    @Autowired
    private RocketMQTemplate rocketMQTemplate;
    @Override
    public Result sendMail(MailParam mailParam) throws MessagingException {
        SendMessage sendMessage = new SendMessage();
        String subject = mailParam.getSubject();
        String from = mailParam.getFrom();
        String recipient = mailParam.getRecipient();
        if(StringUtils.isBlank(subject)||StringUtils.isBlank(recipient)){
            log.info("邮件发送失败");
            return Result.fail(-1000,"参数有误");
        }
        if(StringUtils.isBlank(from)){
            from =From;
        }
        sendMessage.setFrom(from);
        sendMessage.setRecipient(recipient);
        sendMessage.setSubject(subject);
        rocketMQTemplate.asyncSend("mail:oneToOne", sendMessage, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                log.info("发送成功");
            }

            @Override
            public void onException(Throwable e) {
                log.info("发送失败,请重新发送");
            }
        });
        return Result.succeed(null);
    }

    @Override
    public Result sendMailToAll(String mailId, String subject) throws MessagingException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setEmail(mailId);
        sendMessage.setSubject(subject);
        if(StringUtils.isBlank(subject)||StringUtils.isBlank(mailId)){
            log.info("邮件发送失败");
            return Result.fail(-1000,"参数有误");
        }
        rocketMQTemplate.asyncSend("mail:OneToAll", sendMessage, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                log.info("发送成功");
            }

            @Override
            public void onException(Throwable e) {
                log.info("发送失败,请重新发送");
            }
        });
        return Result.succeed(null);
    }
}
