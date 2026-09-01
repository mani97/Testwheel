package com.aishu.spring_security.config;

import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SessionListener implements HttpSessionListener {
    private static final Logger log = LoggerFactory.getLogger(SessionListener.class);

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        log.info("Session expired for ID: {}", se.getSession().getId());
    }
}