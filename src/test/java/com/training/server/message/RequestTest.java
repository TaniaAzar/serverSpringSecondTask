package com.training.server.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;

import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

@RunWith(JUnit4.class)
public class RequestTest {

    private final String STRING_JSON = "{\"name\":\"Tom\",\"com.training.server.message\":\"Hello, server\"}";

    private static ObjectMapper objectMapper;

    @BeforeClass
    public static void init(){
        objectMapper = new ObjectMapper();
    }

    @Test
    public void writeRequestToJson() throws JsonProcessingException {
        Request request = new Request("Tom", "Hello, server");
        String requestString = objectMapper.writeValueAsString(request);

        assertThat(requestString, containsString("Tom"));
        assertThat(requestString, containsString("Hello, server"));
    }

    @Test
    public void writeJsonToRequest() throws IOException {
        Request request = objectMapper.readValue(STRING_JSON, Request.class);

        assertNotNull(request);
        assertThat(request.getName(), containsString("Tom"));
        assertThat(request.getMessage(), containsString("Hello, server"));
    }

}
