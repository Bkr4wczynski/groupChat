package com.bartek.groupchat.server;

import com.bartek.groupchat.utils.Packet;
import com.bartek.groupchat.utils.PacketType;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class ClientHandler implements Runnable{
    private final Socket socket;
    private final ObjectInputStream objectInputStream;
    private final ObjectOutputStream objectOutputStream;
    private List<ClientHandler> clientHandlerList;
    private String username;

    public ClientHandler(Socket socket, List<ClientHandler> clientHandlerList) throws IOException {
        this.socket = socket;
        this.clientHandlerList = clientHandlerList;
        this.objectInputStream = new ObjectInputStream(socket.getInputStream());
        this.objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public void run() {
        boolean flag = true;
        while (flag){
            try {
                Packet received = (Packet) objectInputStream.readObject();
                switch (received.getType()){
                    case PacketType.MESSAGE:
                        sendMessageToAllClients(new Packet(PacketType.MESSAGE, received.getContent()));
                        break;
                    case PacketType.EXIT:
                        clientHandlerList.remove(this);
                        sendMessageToAllClients(new Packet(PacketType.MESSAGE, received.getContent()+ " has left the chat"));
                        flag = false;
                        break;
                    case PacketType.SET_USERNAME:
                        if (isUsernameAvailable(received.getContent()))
                            setUsername(received.getContent());
                        break;
                    case PacketType.USERNAME_AVAILABILITY:
                        objectOutputStream.writeObject(new Packet(PacketType.USERNAME_AVAILABILITY,
                                ""+isUsernameAvailable(received.getContent())));
                        objectOutputStream.flush();
                        break;
                }
            }
            catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                try {
                    closeStreams();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }
    private void sendMessageToAllClients(Packet packet) throws IOException {
        for (ClientHandler client : clientHandlerList){
            client.objectOutputStream.writeObject(packet);
            client.objectOutputStream.flush();
        }
    }
    private void closeStreams() throws IOException {
        objectInputStream.close();
        objectOutputStream.close();
        socket.close();
    }
    private boolean isUsernameAvailable(String username){
        for (ClientHandler clientHandler : clientHandlerList){
            if (clientHandler.getUsername() != null && clientHandler.getUsername().equals(username))
                return false;
        }
        return true;
    }
}
