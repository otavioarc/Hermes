package com.hermes.demo.domain.handlers;

import com.hermes.core.handlers.IRequestHandler;
import com.hermes.demo.domain.commands.GetDateCommand;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class GetDateHandler implements IRequestHandler<GetDateCommand, Date> {
    @Override
    public Date handle(GetDateCommand getDateTimeCommand) {
        return new Date();
    }
}
