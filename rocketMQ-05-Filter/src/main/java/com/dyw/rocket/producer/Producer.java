package com.dyw.rocket.producer;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

/**
 * @author Devil
 * @create 2022-02-23 14:37
 */
@SuppressWarnings("all")
public class Producer {
    public static void main(String[] args) throws MQBrokerException, RemotingException, InterruptedException, MQClientException {
        //1. 谁来发?
        //创建一个生产者
        DefaultMQProducer producer = new DefaultMQProducer(/*可以在这里设置名称*/"group1");

        //2.发给谁
        //发送给命名服务器 通过Name Server分配Brokerip 再由生产者发送给broker
        producer.setNamesrvAddr("localhost:9876");

        //启动连接
        producer.start();

        //3.怎么发
        //发送Message apache包下的 网络传输都是字节流传输
        Message message = new Message("Topic1","vip",("Hello World").getBytes());

        message.putUserProperty("name","张三");
        message.putUserProperty("age","18");

        //4.发什么
        SendResult sendResult = producer.send(message);

        //5.发的结果是什么
        //SendResult 就是发送后的结果
        System.out.println(sendResult);

        //6.打扫战场
        //生产者是与name Server建立了一个长连接进行发送消息 所以发送完毕后 关闭连接
        producer.shutdown();
    }
}
