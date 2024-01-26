package com.project.Real_Moment.config;

import com.p6spy.engine.spy.P6SpyOptions;
import com.project.Real_Moment.util.CustomP6spySqlFormat;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

@Configuration
public class P6spyLogMessageFormatConfiguration {
    @PostConstruct
    public void setLogMessageFormat() {
        P6SpyOptions.getActiveInstance().setLogMessageFormat(CustomP6spySqlFormat.class.getName());
    }
}
