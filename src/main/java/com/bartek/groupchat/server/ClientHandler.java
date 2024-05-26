package com.bartek.groupchat.server;

import com.bartek.groupchat.utils.Packet;
import com.bartek.groupchat.utils.Type;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class ClientHandler implements Runnable{
    private final Socket socket;
    private final ObjectInputStream objectInputStream;
    private final ObjectOutputStream objectOutputStream;
    private List<ClientHandler> clientHandlerList;

    public ClientHandler(Socket socket, List<ClientHandler> clientHandlerList) throws IOException {
        this.socket = socket;
        this.clientHandlerList = clientHandlerList;
        this.objectInputStream = new ObjectInputStream(socket.getInputStream());
        this.objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
    }

    @Override
    public void run() {
        while (true){
            try {
                Packet received = (Packet) objectInputStream.readObject();
                sendMessageToAllClients(new Packet(Type.MESSAGE, received.getContent()));
                objectOutputStream.flush();
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
    private void sendMessageToAllClients(Packet packet) throws IOException {
        for (ClientHandler client : clientHandlerList){
            client.objectOutputStream.writeObject(packet);
            client.objectOutputStream.flush();
        }
    }
}
