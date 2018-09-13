package com.training.server.core.processor;

import com.training.server.message.Request;
import com.training.server.message.Response;

public interface RequestProcessor {
    Response process(Request request);
    boolean accept(Request request);
}
