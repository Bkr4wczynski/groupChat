package com.bartek.groupchat.user.client;

import com.bartek.groupchat.utils.Packet;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class ClientReceiver implements Runnable{
    private final ObjectInputStream objectInputStream;

    public ClientReceiver(ObjectInputStream objectInputStream) {
        this.objectInputStream = objectInputStream;
    }

    @Override
    public void run() {
        while (true){
            try {
                Packet received = (Packet) objectInputStream.readObject();
                System.out.println(received);
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
