package com.dyw.rocketmq.consumer;

import com.dyw.rocketmq.Service.MailRecordService;

import lombok.extern.log4j.Log4j2;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.annotation.SelectorType;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rocketmq.message.SendMessage;

/**
 * @author Devil
 * @create 2022-02-26 18:02
 */
@Log4j2
@Component
@RocketMQMessageListener(topic = "register",consumerGroup ="doRegister",selectorType = SelectorType.TAG,selectorExpression = "register",messageModel = MessageModel.CLUSTERING)
public class RegisterConsumer implements RocketMQListener<SendMessage> {
    @Autowired
    private MailRecordService mailRecordService;

    /**
     * 注册 如果后续使用可能会加上注测后发送邮件功能
     * @param message
     */
    @Override
    public void onMessage(SendMessage message) {
        String email = message.getEmail();
        String username = message.getUsername();
        mailRecordService.userRegister(username,email);
        log.info("注册中...");
    }
}
