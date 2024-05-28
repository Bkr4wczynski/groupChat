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
                if (received.getType() == PacketType.MESSAGE){
                    sendMessageToAllClients(new Packet(PacketType.MESSAGE, received.getContent()));
                }
                else if (received.getType() == PacketType.COMMAND){
                    if (received.getContent().equalsIgnoreCase("exit")){
                        clientHandlerList.remove(this);
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
}
