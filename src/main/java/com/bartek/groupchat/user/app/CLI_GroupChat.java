package com.bartek.groupchat.user.app;

import com.bartek.groupchat.utils.AppType;
import com.bartek.groupchat.user.client.Client;

import java.io.IOException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.util.Scanner;

public class CLI_GroupChat {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        Client client = null;
        try{
            client = new Client(InetAddress.getByName("localhost"), AppType.CLI);
        }
        catch (ConnectException e){
            System.out.println("Server is offline!");
            System.exit(0);
        }
        String input;
        String username;
        boolean availability = false;
        do {
            System.out.println("Enter your username: ");
            username = reformatUsername(scanner.nextLine());
            availability = client.isUsernameAvailable(username);
            if (!availability)
                System.out.println("Username is not available!");
        } while (!availability);
        client.setUsername(username);
        client.sendWelcomeMessage();
        client.startClientReceiver();
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
