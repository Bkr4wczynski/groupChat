package com.bartek.groupchat.user.app;

import com.bartek.groupchat.user.client.Client;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        String input;
        Client client = new Client(InetAddress.getByName("localhost"));

        while (true){
            input = scanner.nextLine();
            client.sendMessage(input);
        }
    }
}
