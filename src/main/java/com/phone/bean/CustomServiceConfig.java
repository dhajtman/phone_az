package com.phone.bean;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "custom.service")
@Getter
@Setter
public class CustomServiceConfig {

    private String url;
    private int timeout;

}
