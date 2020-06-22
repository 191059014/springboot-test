package com.hb.test.exception.handler.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 默认的全局异常处理器
 *
 * @author Mr.Huang
 * @version v0.1, defaultGlobalExceptionHandler.java, 2020/6/22 13:51, create by huangbiao.
 */
@ControllerAdvice
@Slf4j
public class defaultGlobalExceptionHandler {

    /**
     * 统一异常处理-返回数据
     *
     * @param exception JsonException
     * @return 统一返回 json 格式
     */
    @ExceptionHandler(value = IllegalStateException.class)
    @ResponseBody
    public String handleIllegalStateException(IllegalStateException exception) {
        log.error("handleIllegalStateException:{}", exception.getMessage());
        return "com.hb.test.exception.handler.handler.defaultGlobalExceptionHandler.handleIllegalStateException";
    }

    /**
     * 统一异常处理-返回错误页面
     *
     * @param exception PageException
     * @return 统一跳转到异常页面
     */
    @ExceptionHandler(value = IllegalArgumentException.class)
    public ModelAndView handleIllegalArgumentException(IllegalArgumentException exception) {
        log.error("handleIllegalArgumentException:{}", exception.getMessage());
        ModelAndView view = new ModelAndView();
        view.addObject("message", exception.getMessage());
        view.setViewName("error");
        return view;
    }

}

    