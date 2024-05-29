package com.bartek.groupchat.user.client;

import com.bartek.groupchat.utils.AppType;
import com.bartek.groupchat.user.app.controllers.ChatController;
import com.bartek.groupchat.utils.Packet;
import com.bartek.groupchat.utils.PacketType;

import java.io.IOException;
import java.io.ObjectInputStream;

public class ClientReceiver implements Runnable{
    private final ObjectInputStream objectInputStream;
    private String username;
    private ChatController chatController;
    private AppType appType;

    public ClientReceiver(ObjectInputStream objectInputStream, AppType appType) {
        this.objectInputStream = objectInputStream;
        this.appType = appType;
    }

    @Override
    public void run() {
        while (true){
            try {
                Packet received = (Packet) objectInputStream.readObject();
                switch (received.getType()){
                    case PacketType.MESSAGE:
                        String message = formatReceivedMessage(received.getContent());
                        displayMessage(message);
                        break;
                }

            } catch (IOException | ClassNotFoundException e) {
                System.exit(0);
            }
        }
    }
    private void displayMessage(String message){
        if (appType == AppType.CLI)
            System.out.println(message);
        else chatController.receiveMessage(message);
    }
    private String formatReceivedMessage(String message){
        if (message.contains(":") && message.substring(0, message.indexOf(':')).equals(username)){
            return "You" + message.substring(message.indexOf(':'));
        }
        return message;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setChatController(ChatController chatController) {
        this.chatController = chatController;
    }

    public void close() throws IOException {
        objectInputStream.close();
        System.exit(0);
    }

}
