module com.bartek.groupchat {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens com.bartek.groupchat.user.app to javafx.fxml;
    exports com.bartek.groupchat.user.app;
    opens com.bartek.groupchat.user.app.controllers to javafx.fxml;
    exports com.bartek.groupchat.user.app.controllers;
    exports com.bartek.groupchat.utils;
    opens com.bartek.groupchat.utils to javafx.fxml;
}