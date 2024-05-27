package com.bartek.groupchat.user.app.controllers;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class entryController {
    public TextField nameEntry;
    public Label warningsLabel;

    public void enterGroupChat(){
        String enteredName = nameEntry.getText();
        if (!validateUsername(enteredName)){
            warningsLabel.setText("Username is not valid");
            return;
        }
        System.out.println("Entering");
    }
    public void clearWarningsLabel(){
        warningsLabel.setText("");
    }
    private boolean validateUsername(String username){
        return username.matches("[$a-zA-Z0-9-_]{3,19}");
    }
}
