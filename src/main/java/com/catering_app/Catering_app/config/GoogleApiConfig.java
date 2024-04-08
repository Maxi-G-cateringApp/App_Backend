package com.catering_app.Catering_app.config;

import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GoogleApiConfig {

    @Bean
    public HttpTransport httpTransport() {
        return new NetHttpTransport();
    }
}
