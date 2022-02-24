package com.dyw.producer;

import com.dyw.vo.OrderStep;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.util.ArrayList;
import java.util.List;

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

        List<OrderStep> orderSteps = Producer.buildOrders();

        //这样发送会导致消息错乱
//        for (OrderStep orderStep : orderSteps) {
//            Message message = new Message("topic3", "tag1", orderStep.toString().getBytes());
//            SendResult send = producer.send(message);
//
//            System.out.println(send);
//        }

        //正确的发送
        for (OrderStep orderStep : orderSteps) {
            Message message = new Message("topic3", "tag1", orderStep.toString().getBytes());
            SendResult send = producer.send(message, new MessageQueueSelector() {
                //这个方法就是队列悬着的方法
                @Override
                public MessageQueue select(List<MessageQueue> mqs/*消息队里额*/, Message msg, Object arg) {
                    //队列数
                    int size = mqs.size();
                    //确定的orderId对应确定的队列 取模运算
                    int orderId = (int) (orderStep.getOrderId());
                    int queueId = orderId % size;

                    //根据 计算出的queueId 从List<MessageQueue> mqs中获取消息队列
                    return mqs.get(queueId);
                }
            }, null);
            System.out.println(send);
        }
        System.out.println("生产完毕");
        producer.shutdown();
    }

    private static List<OrderStep> buildOrders(){
        ArrayList<OrderStep> orderSteps = new ArrayList<>();

        OrderStep orderStep = new OrderStep();
        orderStep.setOrderId(1L);
        orderStep.setDesc("创建");
        orderSteps.add(orderStep);

        orderStep = new OrderStep();
        orderStep.setOrderId(2L);
        orderStep.setDesc("创建");
        orderSteps.add(orderStep);

        orderStep = new OrderStep();
        orderStep.setOrderId(1L);
        orderStep.setDesc("付款");
        orderSteps.add(orderStep);

        orderStep = new OrderStep();
        orderStep.setOrderId(3L);
        orderStep.setDesc("创建");
        orderSteps.add(orderStep);

        orderStep = new OrderStep();
        orderStep.setOrderId(2L);
        orderStep.setDesc("付款");
        orderSteps.add(orderStep);

        orderStep = new OrderStep();
        orderStep.setOrderId(2L);
        orderStep.setDesc("完成");
        orderSteps.add(orderStep);

        orderStep = new OrderStep();
        orderStep.setOrderId(3L);
        orderStep.setDesc("付款");
        orderSteps.add(orderStep);

        orderStep = new OrderStep();
        orderStep.setOrderId(1L);
        orderStep.setDesc("完成");
        orderSteps.add(orderStep);


        orderStep = new OrderStep();
        orderStep.setOrderId(3L);
        orderStep.setDesc("完成");
        orderSteps.add(orderStep);



        return orderSteps;
    }
}
