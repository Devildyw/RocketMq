package com.dyw.rocketmq.Service;


import org.springframework.messaging.MessagingException;
import rocketmq.result.Result;
import rocketmq.vo.MailParam;



/**
 * @author Devil
 * @create 2022-02-26 15:06
 */
public interface MailService {

    /**
     * 发送邮件
     * @param mailParam
     * @return
     */
    Result sendMail(MailParam mailParam) throws MessagingException;

    /**
     * 群发邮件
     * @param mailId
     * @param subject
     * @return
     */
    Result sendMailToAll(String mailId, String subject) throws MessagingException;
}
