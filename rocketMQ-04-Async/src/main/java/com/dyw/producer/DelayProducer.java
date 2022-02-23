package com.dyw.producer;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

/**
 * @author Devil
 * @create 2022-02-23 20:27
 */
public class DelayProducer {
    public static void main(String[] args) throws Exception{
        DefaultMQProducer producer = new DefaultMQProducer("group1");
        producer.setNamesrvAddr("localhost:9876");
        producer.start();

        //延时消息
        for (int i = 0; i < 10; i++) {
            String msg = "Hello World"+i;
            Message message = new Message("Topic1", "tag1", msg.getBytes());

            //设置延时 能分别设置每一条消息的延时等级
            message.setDelayTimeLevel(4);
            //发送延时消息
            SendResult sendResult = producer.send(message);
            System.out.println(sendResult);
        }
        System.out.println("发送成功了");
        //断开连接
        producer.shutdown();
    }
}
