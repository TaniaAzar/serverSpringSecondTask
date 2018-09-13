package com.training.server.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

@Component
public class ProfilingAnnotationBeanPostProcessor implements BeanPostProcessor{

    private Map<String, Object> map = new HashMap<>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean.getClass().isAnnotationPresent(Profiling.class)){
            map.put(beanName,bean);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (map.containsKey(beanName)){
            Object originalClass = map.getClass();
            return Proxy.newProxyInstance(
                    originalClass.getClass().getClassLoader(),
                    originalClass.getClass().getInterfaces(),
                    (proxy, method, args) -> {
                        Object invoke = method.invoke(originalClass, args);
                        System.out.println("I work...");
                        return invoke;
                    });
        }
        return bean;
    }
}
