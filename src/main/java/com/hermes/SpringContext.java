package com.hermes;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Get context of running application to retrieve Spring managed beans.
 * @author Otavio Carvalho
 * @since 0.0.1
 */
@Component
public class SpringContext implements ApplicationContextAware {

    /**
     * Static field to store running Spring application context.
     */
    private static ApplicationContext applicationContext;

    /**
     * Constructor with {@code ApplicationContext} as parameter to be inject by Spring.
     * Assign Spring running application context to the static field {@link #applicationContext}
     * @param applicationContext represents Spring running application context.
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContext.applicationContext = applicationContext;
    }

    /**
     * Retrieve Spring running application context.
     * @return Spring running application context.
     */
    public ApplicationContext getApplicationContext() {
        return SpringContext.applicationContext;
    }
}
