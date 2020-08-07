package com.hb.test.websocket.config;

import com.hb.test.websocket.common.Constant;
import org.springframework.stereotype.Component;

import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;
import java.util.List;
import java.util.Map;

/**
 * 通用的ServerEndpointConfig配置
 *
 * @version v0.1, 2020/8/7 14:50, create by huangbiao.
 */
@Component
public class CommonServerConfigurator extends ServerEndpointConfig.Configurator {

    @Override
    public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
        // 将token放到ServerEndpointConfig中，方便从session中获取
//        List<String> token = request.getHeaders().get("token");
//        sec.getUserProperties().put(Constant.TOKEN, token.get(0));

        Map<String, List<String>> parameterMap = request.getParameterMap();
        sec.getUserProperties().put(Constant.TOKEN, parameterMap.get(Constant.TOKEN).get(0));
    }

}

    