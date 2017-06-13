package com.bintime;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import redis.clients.jedis.Jedis;

@Configuration
@ComponentScan
@EnableWebMvc
@PropertySource("classpath:/application.properties")
public class Config extends WebMvcConfigurerAdapter {
    @Bean
    Jedis jedis() {
        return new Jedis();
    }

}
