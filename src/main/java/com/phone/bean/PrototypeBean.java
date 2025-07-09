package com.phone.bean;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Scope("prototype") // This bean will be created as a new instance each time it is requested
public class PrototypeBean {

    public PrototypeBean() {
        log.info("PrototypeBean instance created");
    }

    public String getMessage() {
        return "This is a prototype bean instance!";
    }
}
