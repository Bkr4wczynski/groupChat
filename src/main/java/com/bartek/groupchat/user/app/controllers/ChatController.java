package com.bartek.groupchat.user.app.controllers;

import com.bartek.groupchat.user.app.GUI_GroupChat;
import com.bartek.groupchat.user.client.Client;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class ChatController implements Initializable {
    public TextField messageEntry;
    public TextArea messagesArea;
    private final Client client = GUI_GroupChat.CLIENT;

    public void sendMessage(){
        String message = messageEntry.getText();
        messageEntry.setText("");
        client.sendMessage(message);
    }
    public void receiveMessage(String message){
        messagesArea.setText(messagesArea.getText()+"\n"+message);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        client.clientReceiver.setChatController(this);
    }
}
