package com.training.server.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.training.server.core.processor.RequestProcessor;
import com.training.server.core.processor.RequestProcessorMessage;
import com.training.server.message.Request;
import com.training.server.message.Response;
import javax.inject.Provider;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class RootServer implements Runnable{

    private final int port;
    private final int threadCount;
    private final ObjectMapper objectMapper;
    private final Provider<List<RequestProcessorMessage>> provider;
    private ServerSocket serverSocket;
    private ExecutorService executorService;

    public RootServer(int port, int threadCount, ObjectMapper objectMapper, Provider<List<RequestProcessorMessage>> provider) {
        this.port = port;
        this.threadCount = threadCount;
        this.objectMapper = objectMapper;
        this.provider = provider;
    }

    @PostConstruct
    public void init() throws IOException {
        serverSocket = new ServerSocket(port);
        executorService = Executors.newFixedThreadPool(threadCount);
    }

    @PreDestroy
    public void destroy(){
        executorService.shutdownNow();
    }


    @Override
    public void run() {
        try {
            Socket socket = serverSocket.accept();
            handleClientSocket(socket);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void handleClientSocket(Socket socket) {
        executorService.submit(()-> {
            try {
                DataInputStream inFromClient = new DataInputStream(socket.getInputStream());
                DataOutputStream outToClient = new DataOutputStream(socket.getOutputStream());

                String clientLine = inFromClient.readUTF();
                Request request = objectMapper.readValue(clientLine, Request.class);

                List<RequestProcessorMessage> requestProcessors = provider.get();

                Response response = null;

                for (RequestProcessor processor : requestProcessors){
                    if (processor.accept(request)){
                        response = processor.process(request);
                        break;
                    }
                }
                String serverLine = objectMapper.writeValueAsString(response);
                outToClient.writeUTF(serverLine);
                outToClient.flush();

                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
