package com.training.server.core.sleeper;

import org.springframework.stereotype.Component;

@Component
public class SleeperForBean implements Sleeper {

    @Override
    public void sleep(int ml) {
        try {
            Thread.sleep(ml);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
