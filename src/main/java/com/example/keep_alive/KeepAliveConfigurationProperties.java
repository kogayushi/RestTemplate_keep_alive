package com.example.keep_alive;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


@Data
@Validated
@Component
@ConfigurationProperties(prefix = "keep-alive")
public class KeepAliveConfigurationProperties {

    @Min(1)
    @Max(200)
    private int maxConnPerRoute;

    @Min(1)
    @Max(200)
    private int maxConnTotal;

    @Getter(AccessLevel.NONE) // avoid to name as 'isXXX' for boolean field.
    private boolean evictExpiredConnections;

    @Min(1)
    @Max(120)
    private Integer secondsToEvictIdleConnections;

    public boolean evictExpiredConnections() {
        return this.evictExpiredConnections;
    }

    public boolean evictIdleConnection() {
        return secondsToEvictIdleConnections != null && this.secondsToEvictIdleConnections > 0L;
    }
}
