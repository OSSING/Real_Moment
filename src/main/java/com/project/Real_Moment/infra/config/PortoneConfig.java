package com.project.Real_Moment.infra.config;

import com.siot.IamportRestClient.IamportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PortoneConfig {

    @Value("${port-one.api-key}")
    String apiKey;

    @Value("${port-one.secret-key}")
    String secretKey;

    @Bean
    public IamportClient iamportClient() {
        return new IamportClient(apiKey, secretKey);
    }
}
