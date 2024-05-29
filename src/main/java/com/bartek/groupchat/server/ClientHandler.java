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
                if (received.getType() == PacketType.MESSAGE){
                    sendMessageToAllClients(new Packet(PacketType.MESSAGE, received.getContent()));
                }
                else if (received.getType() == PacketType.COMMAND){
                    switch (received.getContent().toLowerCase()){
                        case "exit":
                            clientHandlerList.remove(this);
                            flag = false;
                            break;
                    }
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
