package com.hb.test.rocketmq.test;

import com.hb.test.rocketmq.util.RocketMQWapper;
import com.hb.test.rocketmq.util.RocketMQUtils;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 消费者
 *
 * @version v0.1, 2020/8/14 13:59, create by huangbiao.
 */
@Component
public class Consumer implements InitializingBean {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(Consumer.class);

    @Value("${rocketmq.nameServerCluster}")
    private String nameServerCluster;

    @Override
    public void afterPropertiesSet() throws Exception {

        MessageListenerConcurrently messageListenerConcurrently = (list, consumeConcurrentlyContext) -> {
            try {
                for (MessageExt messageExt : list) {
                    com.hb.test.rocketmq.test.MessageModel model = RocketMQUtils.decode(new String(messageExt.getBody()), com.hb.test.rocketmq.test.MessageModel.class);
                    LOGGER.info("消费者接收到消息：{}，当前重试次数：{}", model, messageExt.getReconsumeTimes());
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            } catch (Exception e) {
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }
        };

        DefaultMQPushConsumer pushConsumer = RocketMQWapper.createPushConsumer(MessageProtocolEnums.WFP_TEST_TOPIC.getConsumerGroup(), nameServerCluster,
                MessageProtocolEnums.WFP_TEST_TOPIC.getTopic(), MessageModel.CLUSTERING, messageListenerConcurrently);

        pushConsumer.start();

        LOGGER.info("开启消费者");
    }
}

    