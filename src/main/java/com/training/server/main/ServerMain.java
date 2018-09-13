package com.training.server.main;

import com.training.server.core.RootServer;
import com.training.server.main.ServerConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ServerMain {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ServerConfig.class);
        context.registerShutdownHook();

        RootServer rootServer = context.getBean(RootServer.class);
        while (true){
            rootServer.run();
        }
    }
}
