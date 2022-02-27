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
import rocketmq.vo.MailVo;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.List;

/**
 * @author Devil
 * @create 2022-02-26 18:00
 */
@Log4j2
@Component
@RocketMQMessageListener(consumerGroup = "doOneToAll",topic = "mail",selectorType = SelectorType.TAG,selectorExpression = "OneToAll",messageModel = MessageModel.CLUSTERING)
public class OneToAllConsumer implements RocketMQListener<SendMessage> {
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
            String from = message.getEmail();
            List<MailVo> mailVoList = mailRecordService.selectMailVoList();
            for (MailVo mailVo : mailVoList) {
                String recipient = mailVo.getEMail();
                mimeMessageHelper.setTo(recipient);
                mimeMessageHelper.setFrom(from);
                mimeMessageHelper.setSubject(message.getSubject());
                mimeMessageHelper.setSentDate(new Date());
                Context context = new Context();
                context.setVariable("username",mailVo.getName());
                String process = templateEngine.process("Mail.html", context);
                mimeMessageHelper.setText(process,true);
                javaMailSender.send(mimeMessage);
                log.info("发送成功");
            }
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
