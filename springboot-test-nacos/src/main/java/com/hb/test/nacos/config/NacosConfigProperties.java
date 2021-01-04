package com.hb.test.nacos.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringReader;
import java.util.Properties;
import java.util.concurrent.Executor;

/**
 * nacos配置
 *
 * @version v0.1, 2021/1/4 13:23, create by huangbiao.
 */
@Component
public class NacosConfigProperties {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(NacosConfigProperties.class);

    @Value(value = "${nacos.config.server-addr}")
    private String serverAddr;

    @Value(value = "${nacos.config.namespace}")
    private String namespace;

    /**
     * 配置
     */
    private static Properties configProperties = new Properties();

    @PostConstruct
    public void init() {
        String dataId = "com.gaoyang.pmarketing.wfpserver";
        String group = "pmarketing";
        int timeout = 15000;
        try {
            Properties properties = new Properties();
            properties.put("serverAddr", serverAddr);
            properties.put("namespace", namespace);
            ConfigService configService = NacosFactory.createConfigService(properties);
            String content = configService.getConfig(dataId, group, timeout);
            configProperties.load(new StringReader(content));
            configService.addListener(dataId, group, new Listener() {
                @Override
                public void receiveConfigInfo(String configInfo) {
                    try {
                        configProperties.clear();
                        configProperties.load(new StringReader(configInfo));
                        LOGGER.info("nacos配置更新完成，配置信息={}", JSON.toJSONString(configProperties));
                    } catch (IOException e) {
                        LOGGER.error("监听获取配置文件更改失败，文件格式不争气", e);
                    }
                }

                @Override
                public Executor getExecutor() {
                    return null;
                }
            });
            LOGGER.info("nacos配置信息加载完成，配置信息={}", JSON.toJSONString(configProperties));
        } catch (NacosException ne) {
            LOGGER.error("获取配置文件更改异常,nacos异常", ne);
        } catch (IOException ioe) {
            LOGGER.error("获取配置文件更改异常，文件格式不正确", ioe);
        } catch (Exception e) {
            LOGGER.error("获取配置文件更改异常", e);
        }
    }

    public static String getValue(String key) {
        if (configProperties.get(key) == null) {
            throw new IllegalArgumentException("无此配置");
        }
        return (String)configProperties.get(key);
    }

    /**
     * 获取Integer类型的值
     *
     * @param key
     *            nacos的key
     * @return Integer
     */
    public static Integer getInteger(String key) {
        return Integer.parseInt(getValue(key));
    }

}
