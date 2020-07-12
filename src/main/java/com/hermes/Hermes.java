package com.hermes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.NoSuchElementException;

/**
 * Hermes orchestrate message redirection to the handler responsible to treat it.
 * @author Otavio Carvalho
 * @since 0.0.1
 */
@Component
public class Hermes implements IHermes {

    /**
     * Represents Spring Context of running application.
     * @see SpringContext
     */
    private SpringContext springContext;

    /**
     * Inject {@code SpringContext} during bean initialization.
     * @param springContext the bean to be injected in {@code Hermes} class constructor
     * @see SpringContext
     */
    @Autowired
    public Hermes(SpringContext springContext) {
        this.springContext = springContext;
    }

    /**
     * Redirect message to the handler responsible for treat it based on Spring managed beans that implement
     * {@code IMessageHandler}.
     * @param message an object that implements {@code IMessage} and should be treated by a handler.
     * @return response type specified on {@code message} class declaration.
     * @see IMessageHandler
     * @see IMessage
     * @throws NoSuchElementException if no handler was found for message class type.
     * @throws RuntimeException if no {@code handle} method with an argument of type {@code IMessage} was found in
     * a bean that implements {@code IMessageHandler}.
     */
    @Override
    public Object send(IMessage message) {
        ApplicationContext applicationContext = springContext.getApplicationContext();

        Collection<IMessageHandler> initializedHandlers = applicationContext
                .getBeansOfType(IMessageHandler.class)
                .values();

        IMessageHandler handler = initializedHandlers.stream()
                .filter(messageHandler -> doesItHandleMessage(messageHandler, message))
                .findFirst()
                .orElseThrow(() ->
                        new NoSuchElementException("No handler was found for message type of "
                                + message.getClass().getSimpleName())
                );

        return handler.handle(message);
    }

    /**
     * Private method that get {@code handle} declared method that receive {@code IMessage} as parameter and
     * calls {@link #doesItReceivesMessageTypeAsParameter(Method, IMessage)} to validate if the parameter class type of
     * {@code handle} method is the same of {@code message} parameter.
     * @param requestHandler the handler being validated.
     * @param message that should be redirect to the corresponding message handler.
     * @return true if the message handler passed as parameter can handle message class type. Otherwise
     * return false.
     * @throws RuntimeException if message handler passed as parameter doesn't declares a {@code handle} method
     */
    private boolean doesItHandleMessage(IMessageHandler requestHandler, IMessage message) {
        return Arrays.stream(requestHandler.getClass().getDeclaredMethods())
                .anyMatch(method -> doesItReceivesMessageTypeAsParameter(method, message));
    }

    /**
     * Private method that validates if the parameter class type of {@code method} is the same of {@code message}
     * parameter.
     * @param method extracted from a {@code message handler} that will be validated if can be executed with
     * {@code message} class type.
     * @param message that should be redirect to the corresponding message handler.
     * @return true if the method passed as parameter can be executed with message class type. Otherwise
     * return false.
     */
    private boolean doesItReceivesMessageTypeAsParameter(Method method, IMessage message) {
        return Arrays.stream(method.getParameterTypes())
                .anyMatch(parameterType -> parameterType.equals(message.getClass()));
    }
}
