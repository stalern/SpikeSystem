package com.savannah.mq;

import com.alibaba.fastjson.JSON;
import com.savannah.service.model.OrderDTO;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.*;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @author stalern
 * @date 2020/01/17~16:27
 */
@Component
public class MqProducer {

    private DefaultMQProducer producer;
    private TransactionMQProducer transactionMQProducer;
    @Value("${mq.nameserver.addr}")
    private String nameAddr;

    @Value("${mq.topicname}")
    private String topicName;

    @PostConstruct
    public void init() throws MQClientException {
        // 无意义
        producer = new DefaultMQProducer("producer_group");
        producer.setNamesrvAddr(nameAddr);
        producer.start();

        transactionMQProducer = new TransactionMQProducer("transaction_producer_group");
        transactionMQProducer.setNamesrvAddr(nameAddr);
        transactionMQProducer.start();

        transactionMQProducer.setTransactionListener(new MqTransactionListener());
    }

    /**
     * 事务性异步减少库存，在orderController中使用
     * @param orderDTO 订单
     * @param orderLogId 订单流水号
     * @return 成功为true，失败为false
     */
    public boolean transactionAsyncReduceStock(OrderDTO orderDTO, String orderLogId) {
        Map<String,Object> bodyMap = new HashMap<>(4);
        bodyMap.put("itemId",orderDTO.getItemId());
        bodyMap.put("amount",orderDTO.getAmount());
        bodyMap.put("orderLogId",orderLogId);

        Message message = new Message(topicName,"increase",
                JSON.toJSON(bodyMap).toString().getBytes(StandardCharsets.UTF_8));
        TransactionSendResult result;
        try {
            // msg代表向consumer投递的消息，orderDTO是事物中监听的参数
            // 发送的是两阶段提交的事物消息，第一次是prepare状态，会去执行MqTransactionListener的executeLocalTransaction方法，orderDTO为该方法的参数
            result = transactionMQProducer.sendMessageInTransaction(message,orderDTO);
        } catch (MQClientException e) {
            e.printStackTrace();
            return false;
        }
        return result.getLocalTransactionState() == LocalTransactionState.COMMIT_MESSAGE;
    }

    /**
     * 同步库存扣减消息，在orderService中使用
     * @param itemId 商品id
     * @param amount 商品数量
     * @return 发送消息
     */
    public boolean asyncReduceStock(Integer itemId,Integer amount)  {
        Map<String,Object> bodyMap = new HashMap<>(2);
        bodyMap.put("itemId",itemId);
        bodyMap.put("amount",amount);

        Message message = new Message(topicName,"increase",
                JSON.toJSON(bodyMap).toString().getBytes(StandardCharsets.UTF_8));
        try {
            producer.send(message);
        } catch (MQClientException | RemotingException | MQBrokerException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
