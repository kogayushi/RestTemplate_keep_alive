package com.example.keep_alive;

import lombok.RequiredArgsConstructor;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Configuration
public class KeepAliveApplicationConfiguration {

    private final KeepAliveConfigurationProperties prop;

    @Bean
    public RestTemplate restTemplate() {
        HttpComponentsClientHttpRequestFactory httpComponentsClientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        HttpClientBuilder builder = HttpClientBuilder
                .create()
                .setMaxConnPerRoute(prop.getMaxConnPerRoute())
                .setMaxConnTotal(prop.getMaxConnTotal());

        if (prop.evictExpiredConnections()) {
            builder.evictExpiredConnections();
        }
        if (prop.evictIdleConnection()) {
            builder.evictIdleConnections(prop.getSecondsToEvictIdleConnections(), TimeUnit.SECONDS);
        }

        httpComponentsClientHttpRequestFactory.setHttpClient(builder.build());

        return new RestTemplate(httpComponentsClientHttpRequestFactory);
    }
}
