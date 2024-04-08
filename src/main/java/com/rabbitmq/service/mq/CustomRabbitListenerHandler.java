package com.rabbitmq.service.mq;

import lombok.NoArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Component
@NoArgsConstructor(force = true)
public class CustomRabbitListenerHandler implements MessageListener {

    private final Object bean;
    private final Method method;

    public CustomRabbitListenerHandler(Object bean, String methodName) {
        this.bean = bean;
        try {
            this.method = bean.getClass().getMethod(methodName, String.class);
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException("Method " + methodName + " not found in class " + bean.getClass(), e);
        }
    }

    @Override
    public void onMessage(Message message) {
        try {
            String messageBody = new String(message.getBody());
            method.invoke(bean, messageBody);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Error invoking message handler method", e);
        }
    }
}