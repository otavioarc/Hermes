package com.hermes.domain.handlers;

import com.hermes.IMessageHandler;
import com.hermes.domain.models.Ping;

public class PingHandler implements IMessageHandler<Ping, String> {
    @Override
    public String handle(Ping message) {
        return null;
    }
}
