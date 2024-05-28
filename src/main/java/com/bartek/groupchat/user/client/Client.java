package com.bartek.groupchat.user.client;

import com.bartek.groupchat.utils.AppType;
import com.bartek.groupchat.utils.Packet;
import com.bartek.groupchat.utils.PacketType;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
    private Socket socket;
    private InetAddress ip;
    private ObjectOutputStream objectOutputStream;
    private String username;
    public ClientReceiver clientReceiver;

    public Client(InetAddress ip, AppType appType) throws IOException {
        this.ip = ip;
        this.socket = new Socket(ip, 5000);
        this.objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        clientReceiver = new ClientReceiver(new ObjectInputStream(socket.getInputStream()), appType);
        Thread clientReceiverThread = new Thread(clientReceiver);
        clientReceiverThread.start();
    }
    private void sendPacket(PacketType packetType, String content) throws IOException {
        objectOutputStream.writeObject(new Packet(packetType, content));
        objectOutputStream.flush();
    }
    public void sendExitRequest(){
        try {
            sendPacket(PacketType.MESSAGE, username + " has left the chat");
            sendPacket(PacketType.COMMAND, "exit");
            closeStreams();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void sendMessage(String message){
        try {
            sendPacket(PacketType.MESSAGE, username + ": " + message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void sendWelcomeMessage(){
        try {
            sendPacket(PacketType.MESSAGE, username + " Has entered the chat!");
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
    private void closeStreams() throws IOException {
        objectOutputStream.close();
        clientReceiver.close();
        socket.close();
    }
}
