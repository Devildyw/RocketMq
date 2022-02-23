package com.dyw.consumer;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * @author Devil
 * @create 2022-02-23 14:37
 */
@SuppressWarnings("all")
public class Consumer {
    public static void main(String[] args) throws Exception{
        //1.谁来收
        //消费者有两种模式 一种是拉去(需要消费者自己去拉去) 一种是推送(消息主动推送给消费者)
        DefaultMQPushConsumer pushConsumer = new DefaultMQPushConsumer("group1");

        //2.从哪里收
        //与生产者一样 消费者 也许要去name Server中获得对应broker的地址去获得消息
        pushConsumer.setNamesrvAddr("localhost:9876");

        //3.监听那个消息队列
        //设置监听队列 subscribe:订阅 指定主题 和订阅表达式 "*"表示订阅主题中的所有
        pushConsumer.subscribe("Topic1","*");

        //4.处理业务流程
        //注册一个监听器 去监听是否有消息被生产 一有就立刻接收
        pushConsumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                //接收到的消息就是 List<MessageExt> msgs 这时我们就能写我们的业务逻辑
                for (MessageExt msg : msgs) {
                    System.out.println(new String(msg.getBody()));
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        //启动连接
        pushConsumer.start();

        System.out.println("消费者启动起来了");

        //注意不要关闭消费者(如果还有对应主题的生产者的情况下 关闭就无法监听消息 就无法收到消息了)
    }
}
