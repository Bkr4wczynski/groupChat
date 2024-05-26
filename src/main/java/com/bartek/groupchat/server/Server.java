package com.bartek.groupchat.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    public static void main(String[] args) {
        List<ClientHandler> clientHandlers = new ArrayList<>();
        try (ServerSocket serverSocket = new ServerSocket(5000)){
            System.out.println("Waiting for clients...");
            while (true){
                Socket socket = serverSocket.accept();
                System.out.println("New client has connected: "+socket);

                ClientHandler clientHandler = new ClientHandler(socket, clientHandlers);
                System.out.println("Assigning new thread for client...");
                clientHandlers.add(clientHandler);
                Thread clientThread = new Thread(clientHandler);
                clientThread.start();

                System.out.println("Successfully assigned new thread for client!");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
