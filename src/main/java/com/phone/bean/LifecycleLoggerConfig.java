package com.phone.bean;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class LifecycleLoggerConfig implements BeanNameAware,
        BeanClassLoaderAware,
        BeanFactoryAware,
        InitializingBean,
        DisposableBean {

    public LifecycleLoggerConfig() {
        log.info("1. Constructor called");
    }

    @Bean(initMethod = "customInit", destroyMethod = "customDestroy")
    public LifecycleLoggerConfig lifecycleLoggerConfiguration() {
        return new LifecycleLoggerConfig();
    }

    @Override
    public void setBeanName(String name) {
        log.info("2. BeanNameAware.setBeanName: {}", name);
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        log.info("3. BeanClassLoaderAware.setBeanClassLoader");
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        log.info("4. BeanFactoryAware.setBeanFactory");
    }

    @PostConstruct
    public void postConstruct() {
        log.info("5. @PostConstruct called");
    }

    @Override
    public void afterPropertiesSet() {
        log.info("6. InitializingBean.afterPropertiesSet called");
    }

    public void customInit() {
        log.info("7. customInit method called");
    }

    @PreDestroy
    public void preDestroy() {
        log.info("8. @PreDestroy called");
    }

    @Override
    public void destroy() {
        log.info("9. DisposableBean.destroy called");
    }

    public void customDestroy() {
        log.info("10. customDestroy method called");
    }
}
