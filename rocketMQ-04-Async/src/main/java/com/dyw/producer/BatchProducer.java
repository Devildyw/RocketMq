package com.dyw.producer;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.ArrayList;

/**
 * @author Devil
 * @create 2022-02-23 20:36
 */
@SuppressWarnings("all")
public class BatchProducer {
    public static void main(String[] args) throws Exception{
        DefaultMQProducer producer = new DefaultMQProducer("group1");
        producer.setNamesrvAddr("localhost:9876");
        producer.start();

        //通过producer的send方法可以传输Collection的机制 我们只需要将消息封装到一个集合中 我们就能发送批量消息了
        ArrayList<Message> messages = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            String msg = "Hello World"+i;
            Message message = new Message("Topic1", "tag1", msg.getBytes());
            messages.add(message);
        }
        //批量发送
        SendResult send = producer.send(messages);
        System.out.println(send);
        System.out.println("批量消息发送成功");

        producer.shutdown();
    }
}
