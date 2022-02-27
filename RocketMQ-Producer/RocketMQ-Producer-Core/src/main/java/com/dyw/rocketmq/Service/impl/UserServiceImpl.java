package com.dyw.rocketmq.Service.impl;

import com.dyw.rocketmq.Service.UserService;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rocketmq.message.SendMessage;
import rocketmq.result.Result;

/**
 * @author Devil
 * @create 2022-02-26 19:24
 */
@Log4j2
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private RocketMQTemplate rocketMQTemplate;
    @Override
    public Result userRegister(String name, String email) {
        SendMessage sendMessage = new SendMessage();
        if(StringUtils.isBlank(name)||StringUtils.isBlank(email)){
            log.error("邮件发送失败");
        }
        sendMessage.setUsername(name);
        sendMessage.setEmail(email);
        rocketMQTemplate.asyncSend("register:register", sendMessage, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                log.info("发送成功");
            }

            @Override
            public void onException(Throwable e) {
                log.error("发送失败");
            }
        });
        return null;
    }
}
