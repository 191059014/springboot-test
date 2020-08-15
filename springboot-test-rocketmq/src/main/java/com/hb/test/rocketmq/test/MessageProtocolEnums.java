package com.hb.test.rocketmq.test;

/**
 * 消息协议常量类
 */
public enum MessageProtocolEnums {

    WFP_TEST_TOPIC("WFP_TEST_TOPIC",
            "WFP_TEST_PRODUCER",
            "WWFP_TEST_CONSUMER",
            "测试TOPIC");
    /**
     * 消息主题
     */
    private String topic;
    /**
     * 生产者组
     */
    private String producerGroup;
    /**
     * 消费者组
     */
    private String consumerGroup;
    /**
     * 消息描述
     */
    private String desc;

    MessageProtocolEnums(String topic, String producerGroup, String consumerGroup, String desc) {
        this.topic = topic;
        this.producerGroup = producerGroup;
        this.consumerGroup = consumerGroup;
        this.desc = desc;
    }

    public String getTopic() {
        return topic;
    }

    public String getProducerGroup() {
        return producerGroup;
    }

    public String getDesc() {
        return desc;
    }

    public String getConsumerGroup() {
        return consumerGroup;
    }
}
