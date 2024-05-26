module com.bartek.groupchat {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens com.bartek.groupchat.user.app to javafx.fxml;
    exports com.bartek.groupchat.user.app;
}