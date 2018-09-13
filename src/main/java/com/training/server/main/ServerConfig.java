package com.training.server.main;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.training.server.core.RootServer;
import com.training.server.core.processor.RequestProcessor;
import javax.inject.Provider;

import com.training.server.core.processor.RequestProcessorMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.List;

@Configuration
@ComponentScan("com.training.server")
@PropertySource(value = "classpath:server.properties")
public class ServerConfig {

    @Value("${server.port}")
    private Integer port;

    @Value("${server.thread.count}")
    private Integer threadCount;

    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper();
    }

    @Bean
    public RootServer rootServer(ObjectMapper objectMapper, Provider<List<RequestProcessorMessage>> provider){
        return new RootServer(port, threadCount, objectMapper, provider);
    }
}
