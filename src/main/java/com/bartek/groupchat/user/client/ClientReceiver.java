package com.bartek.groupchat.user.client;

import com.bartek.groupchat.utils.Packet;

import java.io.IOException;
import java.io.ObjectInputStream;

public class ClientReceiver implements Runnable{
    private final ObjectInputStream objectInputStream;
    private String username;

    public ClientReceiver(ObjectInputStream objectInputStream) {
        this.objectInputStream = objectInputStream;
    }

    @Override
    public void run() {
        while (true){
            try {
                Packet received = (Packet) objectInputStream.readObject();
                String message = formatReceivedMessage(received.getContent());
                System.out.println(message);
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
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
    public void close() throws IOException {
        objectInputStream.close();
        System.exit(0);
    }
}
