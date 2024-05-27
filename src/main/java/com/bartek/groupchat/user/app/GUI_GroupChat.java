package com.bartek.groupchat.user.app;

import com.bartek.groupchat.user.client.Client;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.ConnectException;
import java.net.InetAddress;

public class GUI_GroupChat extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GUI_GroupChat.class.getResource("/com/bartek/groupchat/FXML/entry.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Group chat");
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(windowEvent -> {
            if (logout(windowEvent))
                stage.close();
        });
    }
    private boolean logout(WindowEvent windowEvent){
        windowEvent.consume();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("You are about to logout!");
        alert.setContentText("Are you sure?");
        return (alert.showAndWait().get() == ButtonType.OK);
    }

    public static void main(String[] args) throws IOException {
        try{
            Client client = new Client(InetAddress.getByName("localhost"));
        }
        catch (ConnectException e){
            System.out.println("Server is offline!");
            System.exit(0);
        }
        launch();
    }

}
