package com.savannah.mq;

import com.alibaba.fastjson.JSON;
import com.savannah.dao.OrderLogMapper;
import com.savannah.entity.OrderLogDO;
import com.savannah.error.ReturnException;
import com.savannah.service.OrderService;
import com.savannah.service.model.OrderDTO;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author stalern
 * @date 2020/01/17~22:11
 */
@Component
public class MqTransactionListener implements TransactionListener {

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderLogMapper orderLogMapper;

    /**
     * 消息队列两阶段提交的准备阶段所执行的方法，该方法中包含所有回滚操作，包括消息事物
     * @param message 向消费者投递的消息
     * @param o 可使用的参数
     * @return 提交，回滚，未知
     */
    @Override
    public LocalTransactionState executeLocalTransaction(Message message, Object o) {
        String jsonString  = new String(message.getBody());
        Map<String,Object> map = JSON.parseObject(jsonString, Map.class);
        String orderLogId = (String) map.get("orderLogId");
        try {
            orderService.createOrder((OrderDTO) o, orderLogId);
        } catch (ReturnException e) {
            e.printStackTrace();
            return LocalTransactionState.ROLLBACK_MESSAGE;
        }
        return LocalTransactionState.COMMIT_MESSAGE;
    }

    /**
     * 如果executeLocalTransaction中的createOrder方法断链，其一直是UNKNOWN状态，则该方法会定期回调询问其状态
     * 根据是否扣减库存成功，来判断要返回COMMIT,ROLLBACK还是继续UNKNOWN
     * @param messageExt 向消费者投递的消息
     * @return 提交，回滚，未知
     */
    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
        String jsonString  = new String(messageExt.getBody());
        Map<String,Object> map = JSON.parseObject(jsonString, Map.class);
        String orderLogId = (String) map.get("orderLogId");
        OrderLogDO orderLogDO = orderLogMapper.selectByPrimaryKey(orderLogId);
        if (orderLogDO == null || orderLogDO.getStatus() == 0) {
            return LocalTransactionState.UNKNOW;
        } else if (orderLogDO.getStatus() == 1) {
            return LocalTransactionState.COMMIT_MESSAGE;
        }
        return LocalTransactionState.ROLLBACK_MESSAGE;
    }
}
