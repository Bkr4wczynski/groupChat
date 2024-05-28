package com.bartek.groupchat.user.app.controllers;

import com.bartek.groupchat.user.app.GUI_GroupChat;
import com.bartek.groupchat.user.client.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class EntryController {
    public TextField nameEntry;
    public Label warningsLabel;
    private final Client client = GUI_GroupChat.CLIENT;

    public void enterGroupChat(ActionEvent event){
        String enteredName = nameEntry.getText();
        if (!validateUsername(enteredName)){
            warningsLabel.setText("Username is not valid");
            return;
        }
        try {
            enterToServerGroupChat(enteredName);
            redirectToChatPage(event);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void clearWarningsLabel(){
        warningsLabel.setText("");
    }
    private boolean validateUsername(String username){
        return username.matches("[$a-zA-Z0-9-_]{3,19}");
    }
    private void redirectToChatPage(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(FXMLLoader.load(GUI_GroupChat.class.getResource("/com/bartek/groupchat/FXML/chat.fxml")));
        stage.setScene(scene);
        stage.show();
    }
    private void enterToServerGroupChat(String username){
        client.setUsername(username);
        client.sendWelcomeMessage();
    }
}
