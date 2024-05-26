package com.bartek.groupchat.user.app;

import com.bartek.groupchat.user.client.Client;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        Client client = new Client(InetAddress.getByName("localhost"));
        String input;
        System.out.println("Enter your username: ");
        String username = reformatUsername(scanner.nextLine());
        client.setUsername(username);
        client.sendWelcomeMessage();
        while (true){
            input = scanner.nextLine();
            if (input.equalsIgnoreCase("exit")){
                client.sendExitRequest();
                break;
            }
            else {
                client.sendMessage(input);
            }
        }
    }
    private static String reformatUsername(String username){
        return username.replace(":", "");
    }
}
