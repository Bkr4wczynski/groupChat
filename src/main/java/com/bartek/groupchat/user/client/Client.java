package com.bartek.groupchat.user.client;

import com.bartek.groupchat.user.app.controllers.ChatController;
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
    private ObjectInputStream objectInputStream;
    private String username;
    public ClientReceiver clientReceiver;
    private AppType appType;

    public Client(InetAddress ip, AppType appType) throws IOException {
        this.ip = ip;
        this.appType = appType;
        this.socket = new Socket(ip, 5000);
        this.objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        this.objectInputStream = new ObjectInputStream(socket.getInputStream());
    }
    public void startClientReceiver(){
        clientReceiver = new ClientReceiver(objectInputStream, appType);
        Thread clientReceiverThread = new Thread(clientReceiver);
        clientReceiverThread.start();
        clientReceiver.setUsername(username);
    }
    private void sendPacket(PacketType packetType, String content) throws IOException {
        objectOutputStream.writeObject(new Packet(packetType, content));
        objectOutputStream.flush();
    }
    public void sendExitRequest(){
        try {
            sendPacket(PacketType.EXIT, username);
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
    public boolean isUsernameAvailable(String username){
        try {
            sendPacket(PacketType.USERNAME_AVAILABILITY, username);
            Packet packet = (Packet) objectInputStream.readObject();
            return packet.getContent().equalsIgnoreCase("true");
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
        try {
            sendPacket(PacketType.SET_USERNAME, username);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void closeStreams() throws IOException {
        objectOutputStream.close();
        clientReceiver.close();
        socket.close();
    }
}
