package com.dyw.transaction.producer;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.*;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.exception.RemotingException;

/**
 * @author Devil
 * @create 2022-02-23 14:37
 */
@SuppressWarnings("all")
public class TransProducer {
    public static void main(String[] args) throws Exception {
        TransactionMQProducer producer = new TransactionMQProducer("group1");
        producer.setNamesrvAddr("localhost:9876");

        //设置事务监听
        producer.setTransactionListener(new TransactionListener() {
            //执行本地事务 这就是正常事务过程
            @Override
            public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {
                //消息保存到数据库中
                //sql代码
                //根据数据库事务状态 返回事务状态
                System.out.println("正常执行的过程");

                //LocalTransactionState.ROLLBACK_MESSAGE 表示事务回滚 这时broker就会删除掉half消息 消费者接收不到
                //如果是LocalTransactionState.COMMIT_MESSAGE 表示提交消息 这时broker就会提交half消息 消费能接收
                //LocalTransactionState.UNKNOW 事务结果未知 执行事务补偿过程 即broker主动询问生产者事务结果
                return LocalTransactionState.UNKNOW;
            }
            //检查本地事务 这就是事务补偿过程
            @Override
            public LocalTransactionState checkLocalTransaction(MessageExt msg) {
                System.out.println("执行事务补偿过程");
                return LocalTransactionState.UNKNOW;
            }
        });

        producer.start();

        String msg = "Hello Transaction";
        Message message = new Message("topic4", "tag1", msg.getBytes());
        SendResult send = producer.sendMessageInTransaction(message,null);
        System.out.println(send);

        System.out.println("消息生产完毕");

        //不能关闭 涉及事务的提交和回滚 以及事务与broker的交互过程 不能一发出消息就关闭
        //producer.shutdown();



    }
}
