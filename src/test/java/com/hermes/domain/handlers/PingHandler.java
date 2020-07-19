package com.hermes.domain.handlers;

import com.hermes.MessageHandler;
import com.hermes.domain.models.Ping;

public class PingHandler implements MessageHandler<Ping, String> {
    @Override
    public String handle(Ping message) {
        return null;
    }
}
