package com.bartek.groupchat.user.client;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
    private Socket socket;
    private InetAddress ip;
    private DataOutputStream dataOutputStream;

    public Client(InetAddress ip) throws IOException {
        this.ip = ip;
        this.socket = new Socket(ip, 5000);
        this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
        ClientReceiver clientReceiver = new ClientReceiver(new DataInputStream(socket.getInputStream()));
        Thread clientReceiverThread = new Thread(clientReceiver);
        clientReceiverThread.start();
    }
    public void sendMessage(String message){
        try {
            dataOutputStream.writeUTF(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
