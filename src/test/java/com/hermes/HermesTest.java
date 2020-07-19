package com.hermes;

import com.hermes.domain.handlers.PingHandler;
import com.hermes.domain.models.Ping;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationContext;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HermesTest {

    @InjectMocks
    private Hermes hermes;

    @Mock
    private SpringContext springContext;

    @Mock
    private ApplicationContext applicationContext;

    @Mock
    private PingHandler pingHandler;

    private final String PONG = "Pong";

    @Test
    void shouldRedirectAMessageCorrectlyToAHandler() {
        Ping ping = new Ping();

        doReturn(applicationContext).when(springContext).getApplicationContext();

        Map<String, MessageHandler> beans = new HashMap<>();
        beans.put(pingHandler.getClass().getSimpleName(), pingHandler);

        doReturn(beans).when(applicationContext).getBeansOfType(MessageHandler.class);
        doReturn(PONG).when(pingHandler).handle(ping);

        String response = (String) hermes.send(ping);

        assertNotNull(response);

        verify(springContext, times(1)).getApplicationContext();
        verify(applicationContext, times(1)).getBeansOfType(MessageHandler.class);
        verify(pingHandler, times(1)).handle(ping);
    }

    @Test
    void shouldThrowAnErrorIfNoHandlerWasFounderForAMessage() {
        Ping ping = new Ping();

        doReturn(applicationContext).when(springContext).getApplicationContext();

        assertThrows(
                NoSuchElementException.class,
                () -> hermes.send(ping),
                "No handler was found for message type of "
        );

        verify(springContext, times(1)).getApplicationContext();
        verify(applicationContext, times(1)).getBeansOfType(MessageHandler.class);
    }
}
