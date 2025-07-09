package com.phone.bean;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "my.service")
@Getter
@Setter
public class MyServiceConfig {

    private String url;
    private int timeout;

}
