package com.bartek.groupchat.user.client;

import java.io.DataInputStream;
import java.io.IOException;

public class ClientReceiver implements Runnable{
    private final DataInputStream dataInputStream;

    public ClientReceiver(DataInputStream dataInputStream) {
        this.dataInputStream = dataInputStream;
    }

    @Override
    public void run() {
        while (true){
            try {
                String received = dataInputStream.readUTF();
                System.out.println(received);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
