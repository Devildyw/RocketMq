package com.dyw.rocketmq.consumer;

import com.dyw.rocketmq.Service.MailRecordService;

import lombok.extern.log4j.Log4j2;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.annotation.SelectorType;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import rocketmq.message.SendMessage;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;

/**
 * @author Devil
 * @create 2022-02-26 17:34
 */
@Log4j2
@Component
@RocketMQMessageListener(consumerGroup = "doOneToOne", topic = "mail", selectorType = SelectorType.TAG, selectorExpression = "oneToOne",messageModel = MessageModel.CLUSTERING)
public class OneToOneConsumer implements RocketMQListener<SendMessage> {
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private MailRecordService mailRecordService;
    @Autowired
    private TemplateEngine templateEngine;
    @Override
    public void onMessage(SendMessage message) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = null;
            mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setTo(message.getRecipient());
            mimeMessageHelper.setFrom(message.getFrom());
            mimeMessageHelper.setSubject(message.getSubject());
            mimeMessageHelper.setSentDate(new Date());
            Context context = new Context();
            String userName = mailRecordService.selectUserNameByEMAIl(message.getRecipient());
            context.setVariable("username",userName);
            String process = templateEngine.process("Mail.html", context);
            mimeMessageHelper.setText(process,true);
            javaMailSender.send(mimeMessage);
            log.info("邮件发送成功");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
