package com.bartek.groupchat.user.client;

import com.bartek.groupchat.utils.Packet;
import com.bartek.groupchat.utils.Type;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
    private Socket socket;
    private InetAddress ip;
    private ObjectOutputStream objectOutputStream;

    public Client(InetAddress ip) throws IOException {
        this.ip = ip;
        this.socket = new Socket(ip, 5000);
        this.objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        ClientReceiver clientReceiver = new ClientReceiver(new ObjectInputStream(socket.getInputStream()));
        Thread clientReceiverThread = new Thread(clientReceiver);
        clientReceiverThread.start();
    }
    public void sendMessage(String message){
        try {
            objectOutputStream.writeObject(new Packet(Type.MESSAGE, message));
            objectOutputStream.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
