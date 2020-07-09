package com.hb.test.log.support.filter;

import com.alibaba.dubbo.rpc.Filter;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.dubbo.rpc.RpcException;
import com.hb.test.log.support.common.Consts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.util.UUID;

/**
 * traceId的dubbo处理方式
 *
 * @version v0.1, 2020/7/9 15:16, create by huangbiao.
 */
public class TraceIdDubboFilter implements Filter {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(TraceIdDubboFilter.class);

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        RpcContext rpcContext = RpcContext.getContext();
        String traceId = "";
        if (rpcContext.isConsumerSide()) {
            traceId = MDC.get(Consts.TRACE_ID);
            RpcContext.getContext().setAttachment(Consts.TRACE_ID, traceId);
            LOGGER.debug("dubbo消费者设置traceId={}", traceId);
        }

        if (rpcContext.isProviderSide()) {
            traceId = RpcContext.getContext().getAttachment(Consts.TRACE_ID);
            if (traceId != null && traceId.length() != 0) {
                LOGGER.debug("dubbo提供者获取到的traceId不为空,traceId={}", traceId);
            } else {
                LOGGER.debug("dubbo提供者获取到的traceId为空，生成traceId", traceId);
                traceId = UUID.randomUUID().toString().replace("-", "");
            }

            MDC.put("traceId", traceId);
        }

        return invoker.invoke(invocation);
    }
}

    