package com.wonders.client.controller.trash;

import com.wonders.client.ClientRemote;
import com.wonders.client.controller.Controller;
import com.wonders.client.pattern.Singleton;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Connection implements Controller {

    @FXML
    private TextField server_field;
    @FXML
    private Button register_user;

    @FXML
    private Button login_user;

    @FXML
    private Label auth_label;

    @FXML
    private Label connection_label;

    @FXML
    private Button connect_server;

    @FXML
    private Button menu_btn;

    @FXML
    void initialize() {
        ClientRemote clientRemote = new ClientRemote();
        Singleton.getInstance().client = clientRemote;

        menu_btn.setDisable(true);
        try {
            server_field.setText(InetAddress.getLocalHost().getHostAddress());
        } catch (UnknownHostException ignore) {

        }
        connect_server.setOnAction(e -> {
            if (server_field.getText().isEmpty()) return;

            boolean cond = clientRemote.connect_to_server(server_field.getText());
            if (cond) {
                Singleton.getInstance().client.server_address = server_field.getText();
                connection_label.setText("Successfully Connected to the Server");
                connection_label.setTextFill(Color.GREEN);
                connect_server.setDisable(true);
                register_user.setDisable(Singleton.getInstance().client.server_address.isEmpty() || Singleton.getInstance().active_player != null);
                login_user.setDisable(Singleton.getInstance().client.server_address.isEmpty() || Singleton.getInstance().active_player != null);
                if (Singleton.getInstance().active_player != null)
                    menu_btn.setDisable(false);
            } else {
                connection_label.setTextFill(Color.RED);
                connection_label.setText("Failed to Connect with Server.");
            }
        });

        register_user.setOnAction(e -> Singleton.getInstance().
                changeView("Registration", "views/Registration.fxml"));
        login_user.setOnAction(e -> Singleton.getInstance()
                .changeView("Authentication", "views/Authentication.fxml"));

        connect_server.setDisable(!Singleton.getInstance().client.server_address.isEmpty());
        register_user.setDisable(Singleton.getInstance().client.server_address.isEmpty() || Singleton.getInstance().active_player != null);
        login_user.setDisable(Singleton.getInstance().client.server_address.isEmpty() || Singleton.getInstance().active_player != null);

        if (Singleton.getInstance().active_player != null) {
            auth_label.setTextFill(Color.GREEN);
            auth_label.setText("User Authenticated");
            connection_label.setText("Successfully Connected to the Server");
            connection_label.setTextFill(Color.GREEN);
            connect_server.setDisable(true);
            menu_btn.setDisable(false);
        }

        menu_btn.setOnAction(e -> {
            // load the menu here.
            Singleton.getInstance().loadNextScene("Dashboard!", "views/dashboard.fxml");
            Singleton.getInstance().createTimeline();
            Singleton.getInstance().timeline.play();
        });
    }

    @Override
    public void server_updates() {

    }
}
