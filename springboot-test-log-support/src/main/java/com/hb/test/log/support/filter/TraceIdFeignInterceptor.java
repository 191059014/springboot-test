package com.hb.test.log.support.filter;

import com.hb.test.log.support.common.Consts;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.MDC;

/**
 * TraceId的Feign处理
 *
 * @version v0.1, 2020/7/9 15:27, create by huangbiao.
 */
public class TraceIdFeignInterceptor implements RequestInterceptor {

    public TraceIdFeignInterceptor() {
    }

    public void apply(RequestTemplate requestTemplate) {
        String traceId = MDC.get(Consts.TRACE_ID);
        requestTemplate.header(Consts.TRACE_ID, traceId);
    }

}

    