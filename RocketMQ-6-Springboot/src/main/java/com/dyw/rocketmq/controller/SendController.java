package com.dyw.rocketmq.controller;

import com.dyw.rocketmq.domain.User;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Devil
 * @create 2022-02-23 21:53
 */
@SuppressWarnings("all")
@RestController
@RequestMapping("/demo")
public class SendController {
    @Autowired
    private RocketMQTemplate rocketMQTemplate;//RocketMQ模板类: 建连接 短链接
    @GetMapping("/send")
    public String send(){
        User user = new User("Devil", 10);
        rocketMQTemplate.convertAndSend("Topic2",user);//convert: 消息转换为字节数组 甚至可以自动将对象转化为字节数组 但必须实现序列化

        rocketMQTemplate.syncSend("Topic2",user);//发送同步消息

        //发送异步消息
        rocketMQTemplate.asyncSend("Topic2", user, new SendCallback() {
            //发送成功的回调方法
            @Override
            public void onSuccess(SendResult sendResult) {

            }
            //发送失败的回调方法
            @Override
            public void onException(Throwable e) {

            }
        });

        //发送单项消息
        rocketMQTemplate.sendOneWay("Topic2",user);

        //发送延时消息
        rocketMQTemplate.syncSend("Topic2:tag1", MessageBuilder.withPayload(user).build(),10,3);

        return "success";
    }
}
