package com.proxycache.proxy.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class Webconfiguration {
        @Bean
        public RestTemplate getRestObject(){
            return new RestTemplate();
        }
    }
