package com.training.server.core.processor;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.training.server.core.sleeper.SleeperForBean;
import com.training.server.message.Request;
import com.training.server.message.Response;
import com.training.server.util.Profiling;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@PropertySource(value = "classpath:server.properties")
public class RequestProcessorMessage implements RequestProcessor {

    private final SleeperForBean sleeper;

    @Value("${server.ml.sleep}")
    private int mlSleep;

    @Autowired
    public RequestProcessorMessage(SleeperForBean sleeper) {
        this.sleeper = sleeper;
    }

    @Override
    @Profiling
    public Response process(Request request) {
        String message = request.getMessage();
        String name = request.getName();

        sleeper.sleep(mlSleep);

        System.out.println(String.format("{from: %s, message: %s}", name, message));
        return new Response("Hello, " + name);
    }

    @Override
    public boolean accept(Request request) {
        return true;
    }
}
