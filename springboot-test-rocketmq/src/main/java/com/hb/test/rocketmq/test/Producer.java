package com.hb.test.rocketmq.test;

import com.hb.test.rocketmq.util.RocketMQWapper;
import com.hb.test.rocketmq.util.RocketMQUtils;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 测试生产者
 *
 * @version v0.1, 2020/8/14 13:58, create by huangbiao.
 */
@Component
public class Producer implements InitializingBean {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(Producer.class);

    @Value("${rocketmq.nameServerCluster}")
    private String nameServerCluster;

    // 简单生产者代理对象
    private DefaultMQProducer simpleProducer;

    @Override
    public void afterPropertiesSet() throws Exception {

        simpleProducer = RocketMQWapper.createSimpleProducer(MessageProtocolEnums.WFP_TEST_TOPIC.getProducerGroup(), nameServerCluster);

        simpleProducer.start();

        LOGGER.info("开启生产者");
    }

    public void sendMessage(MessageModel model) {
        Message message = RocketMQUtils.createMessage(MessageProtocolEnums.WFP_TEST_TOPIC.getTopic(), model);
        try {
            simpleProducer.send(message, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    LOGGER.info("发送成功：{}", sendResult);
                }

                @Override
                public void onException(Throwable throwable) {
                    LOGGER.info("发送异常：", throwable);
                }
            });
        } catch (Exception e) {
            LOGGER.error("发送消息异常：", e);
        } finally {
            simpleProducer.shutdown();
        }

    }

}

    