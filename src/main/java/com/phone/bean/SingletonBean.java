package com.phone.bean;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton") // This is the default scope, so it's optional
@Slf4j
public class SingletonBean {

    public SingletonBean() {
        log.info("SingletonBean instance created");
    }
}
