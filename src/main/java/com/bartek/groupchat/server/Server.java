package com.bartek.groupchat.server;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Server {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Provide password: ");
        if (scanner.nextLine().equals(decryptPassword(getPassword())))
            System.out.println("Successful authorization");
        else {
            System.out.println("Authorization failed!");
            System.exit(0);
        }
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
    private static String decryptPassword(String password){
        char xorKey = 'P';
        String outputString = "";
        int len = password.length();
        for (int i = 0; i < len; i++)
        {
            outputString = outputString +
                    (char) (password.charAt(i) ^ xorKey);
        }
        return outputString;
    }
    private static String getPassword(){
        File file = new File("src/main/resources/com/bartek/groupchat/Server/password.txt");
        try (Scanner scanner = new Scanner(file)) {
            return scanner.nextLine();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
