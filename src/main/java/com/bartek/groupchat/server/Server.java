package com.bartek.groupchat.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(5000)){
            System.out.println("Waiting for clients...");
            while (true){
                Socket socket = serverSocket.accept();
                System.out.println("New client has connected: "+socket);
                ClientHandler clientHandler = new ClientHandler(socket);
                System.out.println("Assigning new thread for client...");
                Thread clientThread = new Thread(clientHandler);
                clientThread.start();
                System.out.println("Successfully assigned new thread for client!");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
