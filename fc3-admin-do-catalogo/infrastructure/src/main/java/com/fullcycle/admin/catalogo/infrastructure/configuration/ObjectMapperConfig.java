package com.fullcycle.admin.catalogo.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fullcycle.admin.catalogo.infrastructure.configuration.json.Json;


@Configuration
public class ObjectMapperConfig {
    
    @Bean
    public ObjectMapper objectMapper(){
        return Json.mapper();
    }

}
