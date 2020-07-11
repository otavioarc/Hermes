package com.hermes.core;

import com.hermes.SpringContext;
import com.hermes.core.handlers.IRequestHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class Hermes implements IHermes {

    private SpringContext springContext;

    @Autowired
    public Hermes(SpringContext springContext) {
        this.springContext = springContext;
    }

    @Override
    public Object send(Object message) {
        try {
            ApplicationContext applicationContext = springContext.getApplicationContext();
            Map<String, IRequestHandler> handlers = applicationContext.getBeansOfType(IRequestHandler.class);

            AtomicReference<IRequestHandler> requestHandler = new AtomicReference<>();

            handlers.forEach((s, iRequestHandler) -> {
                Arrays.asList(iRequestHandler.getClass().getDeclaredMethods())
                        .forEach(method -> {
                            Arrays.asList(method.getParameterTypes())
                                    .forEach(aClass -> {
                                        if (Objects.equals(aClass, message.getClass()))
                                            requestHandler.set(iRequestHandler);
                                    });
                        });
            });

            return requestHandler.get().handle(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
