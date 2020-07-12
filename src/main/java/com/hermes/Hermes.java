package com.hermes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.NoSuchElementException;

@Component
public class Hermes implements IHermes {

    private SpringContext springContext;

    @Autowired
    public Hermes(SpringContext springContext) {
        this.springContext = springContext;
    }

    @Override
    public Object send(IMessage message) {
        ApplicationContext applicationContext = springContext.getApplicationContext();

        Collection<IMessageHandler> initializedHandlers =  applicationContext.getBeansOfType(IMessageHandler.class).values();

        IMessageHandler handler = initializedHandlers.stream()
                .filter(messageHandler -> doesItHandleMessage(messageHandler, message))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("No handler was found for message type of " + message.getClass().getSimpleName()));

        return handler.handle(message);
    }

    private boolean doesItHandleMessage(IMessageHandler requestHandler, IMessage message) {
        return Arrays.stream(requestHandler.getClass().getDeclaredMethods())
                .anyMatch(method -> doesItReceivesMessageTypeAsParameter(method, message));
    }

    private boolean doesItReceivesMessageTypeAsParameter(Method method, IMessage message) {
        return Arrays.stream(method.getParameterTypes())
                .anyMatch(parameterType -> parameterType.equals(message.getClass()));
    }
}
