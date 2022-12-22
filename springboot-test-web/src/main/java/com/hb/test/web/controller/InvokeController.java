package com.hb.test.web.controller;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 反射调用
 *
 * @version v0.1, 2022/12/29 15:30, create by huangbiao.
 */
@Slf4j
@RestController
public class InvokeController {

    /**
     * spring上下文
     */
    @Resource
    private ApplicationContext applicationContext;

    /**
     * 支持调用：spring的bean的public/protected/private方法、普通类的静态的public/protected/private方法
     *
     * @param beanNameOrClassName bean名称
     * @param methodName          方法名
     * @param parameters          参数
     * @return 结果
     */
    @SneakyThrows
    @PostMapping("/invoke")
    public Object invokeMethod(@RequestParam("beanNameOrClassName") String beanNameOrClassName,
                               @RequestParam("methodName") String methodName,
                               @RequestBody(required = false) List<Parameter> parameters) {

        if (beanNameOrClassName.contains(".")) {
            return invokeStaticMethod(beanNameOrClassName, methodName, parameters);
        }
        return invokeBeanMethod(beanNameOrClassName, methodName, parameters);
    }


    /**
     * 调用spring的bean的方法
     *
     * @param beanName   bean名称
     * @param methodName 方法名
     * @param parameters 参数
     * @return 结果
     */
    @SneakyThrows
    private Object invokeBeanMethod(String beanName, String methodName, List<Parameter> parameters) {
        log.info("invokeBeanMethod, beanName={}, methodName={}", beanName, methodName);
        Assert.hasText(beanName, "beanName is empty");
        Assert.hasText(methodName, "methodName is empty");
        Object bean = applicationContext.getBean(beanName);
        Assert.notNull(bean, "bean is null");
        List<Class<?>> parameterTypes = getParameterTypes(parameters);
        List<Object> parameterValues = getParameterValues(parameters);
        Method method = bean.getClass().getDeclaredMethod(methodName, parameterTypes.toArray(new Class<?>[0]));
        method.setAccessible(true);
        return method.invoke(bean, parameterValues.toArray());
    }

    /**
     * 调用静态方法
     *
     * @param className  类名
     * @param methodName 方法名
     * @param parameters 参数
     * @return 结果
     */
    @SneakyThrows
    private Object invokeStaticMethod(String className, String methodName, List<Parameter> parameters) {
        log.info("invokeStaticMethod, className={}, methodName={}", className, methodName);
        Assert.hasText(className, "className is empty");
        Assert.hasText(methodName, "methodName is empty");
        Class<?> aClass = Class.forName(className);
        List<Class<?>> parameterTypes = getParameterTypes(parameters);
        List<Object> parameterValues = getParameterValues(parameters);
        Method method = aClass.getDeclaredMethod(methodName, parameterTypes.toArray(new Class<?>[0]));
        method.setAccessible(true);
        return method.invoke(null, parameterValues.toArray());
    }

    @SneakyThrows
    private List<Class<?>> getParameterTypes(List<Parameter> parameters) {
        List<Class<?>> parameterTypes = new ArrayList<>();
        if (CollectionUtils.isEmpty(parameters)) {
            return parameterTypes;
        }
        for (Parameter parameter : parameters) {
            String parameterType = parameter.getParameterType();
            if (StringUtils.isEmpty(parameterType)) {
                continue;
            }
            Class<?> parameterClass = ParameterClassEnum.matchParameterClass(parameterType);
            parameterTypes.add(parameterClass);
        }
        return parameterTypes;
    }

    @SneakyThrows
    private List<Object> getParameterValues(List<Parameter> parameters) {
        List<Object> parameterValues = new ArrayList<>();
        if (CollectionUtils.isEmpty(parameters)) {
            return parameterValues;
        }
        for (Parameter parameter : parameters) {
            String parameterType = parameter.getParameterType();
            if (StringUtils.isEmpty(parameterType)) {
                continue;
            }
            Object originParameterValue = parameter.getParameterValue();
            if (ObjectUtils.isEmpty(originParameterValue)) {
                parameterValues.add(null);
                continue;
            }
            Class<?> parameterClass = ParameterClassEnum.matchParameterClass(parameterType);
            String strValue = JSON.toJSONString(originParameterValue);
            Object targetParameterValue = JSON.parseObject(strValue, parameterClass);
            parameterValues.add(targetParameterValue);
        }
        return parameterValues;
    }

    @Getter
    private enum ParameterClassEnum {

        STRING("String", String.class),
        INT("int", int.class),
        INTEGER("Integer", Integer.class),
        BASE_LONG("long", long.class),
        LONG("Long", Long.class),;

        private String type;
        private Class<?> clazz;

        ParameterClassEnum(String type, Class<?> clazz) {
            this.type = type;
            this.clazz = clazz;
        }

        @SneakyThrows
        public static Class<?> matchParameterClass(String parameterType) {
            for (ParameterClassEnum parameterClassEnum : ParameterClassEnum.values()) {
                if (parameterClassEnum.getType().contains(parameterType)) {
                    return parameterClassEnum.getClazz();
                }
            }
            return Class.forName(parameterType);
        }

    }

    @Data
    private static class Parameter {
        String parameterType;
        Object parameterValue;
    }

}
