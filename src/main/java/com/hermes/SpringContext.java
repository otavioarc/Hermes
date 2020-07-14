package com.hermes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Get context of running application to retrieve Spring managed beans.
 * @author Otavio Carvalho
 * @since 0.0.1
 */
@Component
public class SpringContext {

    /**
     * Static field to store running Spring application context.
     */
    private final ApplicationContext applicationContext;

    /**
     * Constructor with {@code ApplicationContext} as parameter to be inject by Spring.
     * Assign Spring running application context to the final field {@link #applicationContext}
     * @param applicationContext represents Spring running application context.
     */
    @Autowired
    public SpringContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    /**
     * Retrieve Spring running application context.
     * @return Spring running application context.
     */
    ApplicationContext getApplicationContext() {
        return this.applicationContext;
    }
}
