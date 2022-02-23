package com.dyw.producer;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;

/**
 * @author Devil
 * @create 2022-02-23 20:18
 */
public class OneWayProducer {
    public static void main(String[] args) throws Exception{
        DefaultMQProducer producer = new DefaultMQProducer("group3");
        producer.setNamesrvAddr("localhost:9876");
        producer.start();

        //单项消息
        for (int i = 0; i < 10; i++) {
            String msg = "Hello World"+i;
            Message message = new Message("Topic1", "tag1", msg.getBytes());
            //发送单项消息 没有回执消息
            producer.sendOneway(message);
        }
        System.out.println("发送完成了");
        producer.shutdown();
    }
}
