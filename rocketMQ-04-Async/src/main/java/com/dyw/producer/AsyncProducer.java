package com.dyw.producer;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

/**
 * @author Devil
 * @create 2022-02-23 18:05
 */
@SuppressWarnings("all")
public class AsyncProducer {
    public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException {
        DefaultMQProducer producer = new DefaultMQProducer("group3");
        producer.setNamesrvAddr("localhost:9876");
        producer.start();

        for (int i = 0; i < 10; i++) {
            String msg = "Hello World";
            Message message = new Message("Topic3", "tag1", msg.getBytes());
            //异步消息 Callback也是一个多线的接口
            producer.send(message, new SendCallback() {
                //发送成功的回调方法a
                @Override
                public void onSuccess(SendResult sendResult) {
                    System.out.println(sendResult);
                }
                //发送失败的回调方法
                @Override
                public void onException(Throwable e) {
                    System.out.println(e);
                }
            });
        }
        System.out.println("异步发送完成");
    }
}
