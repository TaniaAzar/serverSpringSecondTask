package com.training.server.core;

import com.training.server.core.processor.RequestProcessorMessage;
import com.training.server.core.sleeper.SleeperForBean;
import com.training.server.message.Request;
import com.training.server.message.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RequestProcessorMessageTest {

    @Mock
    private SleeperForBean sleeper;

    @InjectMocks
    private RequestProcessorMessage test;

    @Before
    public void init(){
        doNothing().when(sleeper).sleep(anyInt());
    }

    @Test
    public void processorMessage(){
        Response response = test.process(new Request("Tom", "Hello, server"));
        assertNotNull(response);
        assertEquals("Hello, Tom", response.getMessage());
        verify(sleeper, times(1)).sleep(anyInt());
    }

    @Test(expected = Exception.class)
    public void sleeperException(){
        doThrow(new Exception()).when(sleeper).sleep(anyInt());
    }

}
