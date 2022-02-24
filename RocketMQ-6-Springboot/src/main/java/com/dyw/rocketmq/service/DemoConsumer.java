package com.dyw.rocketmq.service;
import com.dyw.rocketmq.domain.User;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.annotation.SelectorType;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

/**
 * @author Devil
 * @create 2022-02-24 19:13
 */
@Service
@RocketMQMessageListener(topic = "Topic2",selectorExpression = "tag1 || tag2",consumerGroup = "${rocketmq.producer.group}",
        selectorType = SelectorType.TAG,messageModel = MessageModel.BROADCASTING)
public class DemoConsumer implements RocketMQListener<User> {
    /**
     * 接收成功的回调方法
     * @param message
     */
    @Override
    public void onMessage(User message) {
        System.out.println(message);
    }

}
