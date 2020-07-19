package com.hermes;

/**
 * Interface used to implements message handler for a specific type and response.
 * @author Otavio Carvalho
 * @since 0.0.1
 * @param <MessageT> the class type that should be treated by the handler.
 * @param <ResponseT> the class type that handler should return.
 */
public interface MessageHandler<MessageT extends Message<ResponseT>, ResponseT> {

    /**
     * Handle {@code message} sent by {@link Hermes} to be treated.
     * @param message that should be treated by a handler.
     * @return class type specified in {@code message} class declaration.
     */
    ResponseT handle(MessageT message);
}
