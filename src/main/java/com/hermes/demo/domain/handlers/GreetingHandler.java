package com.hermes.demo.domain.handlers;

import com.hermes.core.handlers.IRequestHandler;
import com.hermes.demo.domain.commands.GreetingCommand;
import org.springframework.stereotype.Component;

@Component
public class GreetingHandler implements IRequestHandler<GreetingCommand, String> {

    private final String GREETING_MESSAGE = "Hello, world!";

    @Override
    public String handle(GreetingCommand greetingCommand) {
        return GREETING_MESSAGE;
    }
}
