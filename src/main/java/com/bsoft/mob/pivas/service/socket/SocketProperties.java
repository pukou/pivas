package com.bsoft.mob.pivas.service.socket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * 用于加载PB Socket连接属性
 * Created by huangy on 2015-05-06.
 */
@Configuration
@PropertySource("classpath:socket.properties")
public class SocketProperties {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
