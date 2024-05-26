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
    private String username;
    private ClientReceiver clientReceiver;

    public Client(InetAddress ip) throws IOException {
        this.ip = ip;
        this.socket = new Socket(ip, 5000);
        this.objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        clientReceiver = new ClientReceiver(new ObjectInputStream(socket.getInputStream()));
        Thread clientReceiverThread = new Thread(clientReceiver);
        clientReceiverThread.start();
    }
    private void sendPacket(Type type, String content) throws IOException {
        objectOutputStream.writeObject(new Packet(type, content));
        objectOutputStream.flush();
    }
    public void sendMessage(String message){
        try {
            sendPacket(Type.MESSAGE, username + ": " + message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void sendWelcomeMessage(){
        try {
            sendPacket(Type.MESSAGE, username + " Has entered the chat!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
        clientReceiver.setUsername(username);
    }
}
