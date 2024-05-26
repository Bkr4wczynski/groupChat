package com.bartek.groupchat.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler implements Runnable{
    private final Socket socket;
    private final DataInputStream dataInputStream;
    private final DataOutputStream dataOutputStream;

    public ClientHandler(Socket socket) throws IOException {
        this.socket = socket;
        this.dataInputStream = new DataInputStream(socket.getInputStream());
        this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
    }

    @Override
    public void run() {
        while (true){
            try {
                String received = dataInputStream.readUTF();
                dataOutputStream.writeUTF(received);
                System.out.println(received);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
