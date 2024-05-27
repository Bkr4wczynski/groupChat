package com.bartek.groupchat.user.app.controllers;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class chatController {
    public TextField messageEntry;
    public TextArea messagesArea;

    public void sendMessage(){
        String message = messageEntry.getText();
        messageEntry.setText("");
        System.out.println(message);
        receiveMessage();
    }
    public void receiveMessage(){
        String message = "Example";
        messagesArea.setText(messagesArea.getText()+"\n"+message);
    }
}
