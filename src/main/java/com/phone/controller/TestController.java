package com.phone.controller;

import com.phone.bean.MyServiceConfig;
import com.phone.bean.PrototypeBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class TestController {

    @Autowired
    private PrototypeBean prototypeBean;

    @Autowired
    private MyServiceConfig myServiceConfig;

    @Autowired
    private ApplicationContext applicationContext;

    @GetMapping("/test/prototype")
    public String testPrototypeBean() {
        log.info("Accessing prototype bean way1: {}", prototypeBean);
        return prototypeBean.getMessage();
    }

    @GetMapping("/test/prototype2")
    public String testPrototypeBean2() {
        PrototypeBean prototypeBean2 = applicationContext.getBean(PrototypeBean.class);
        log.info("Accessing prototype bean way2: {}", prototypeBean2);
        return prototypeBean2.getMessage();
    }

    @GetMapping("/test/config")
    public String testConfig() {
        log.info("myServiceConfig.getUrl(): {}", myServiceConfig.getUrl());
        log.info("myServiceConfig.getTimeout(): {}", myServiceConfig.getTimeout());
        return "Configuration bean accessed successfully!";
    }
}
