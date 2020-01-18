package com.savannah.mq;

import com.alibaba.fastjson.JSON;
import com.savannah.dao.ItemStockMapper;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

/**
 * @author stalern
 * @date 2020/01/17~14:57
 */
@Component
public class MqConsumer {

    @Value("${mq.nameserver.addr}")
    private String nameAddr;

    @Value("${mq.topicname}")
    private String topicName;

    private final ItemStockMapper itemStockMapper;

    public MqConsumer(ItemStockMapper itemStockMapper) {
        this.itemStockMapper = itemStockMapper;
    }

    @PostConstruct
    public void init() throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("stock_consumer_group");
        consumer.setNamesrvAddr(nameAddr);
        // 订阅topicName的消息
        consumer.subscribe(topicName,"*");

        consumer.registerMessageListener((MessageListenerConcurrently) (msgs, context) -> {
            //实现库存真正到数据库内扣减的逻辑
            Message msg = msgs.get(0);
            String jsonString  = new String(msg.getBody());
            Map<String,Integer> map = JSON.parseObject(jsonString, Map.class);
            Integer itemId = map.get("itemId");
            Integer amount = map.get("amount");

            itemStockMapper.decreaseStock(itemId,amount);
            // 消息消费成功
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });

        consumer.start();
    }
}
