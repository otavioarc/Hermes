package com.hermes;

/**
 * Hermes interface used to redirect message to the corresponding message handler.
 * @author Otavio Carvalho
 * @since 0.0.1
 */
public interface IHermes {

    /**
     * Send {@code message} to handler responsible to treat it.
     * @param message that should be treated by a handler.
     * @return class type specified in {@code message} class declaration.
     */
    Object send(IMessage message);
}
