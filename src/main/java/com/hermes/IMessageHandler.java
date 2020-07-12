package com.hermes;

public interface IMessageHandler<MessageT extends IMessage<ResponseT>, ResponseT> {
    ResponseT handle(MessageT message);
}
