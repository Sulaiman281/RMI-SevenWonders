package com.wonders.client.controller.trash;

import com.wonders.PlayerRemote;
import com.wonders.Settings;
import com.wonders.client.controller.Controller;
import com.wonders.model.Player;
import com.wonders.client.pattern.Singleton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Registration implements Controller {

    @FXML
    private TextField name_field;

    @FXML
    private TextField email_field;

    @FXML
    private PasswordField pass_field;

    @FXML
    private Label name_error_msg;

    @FXML
    private Label email_error_msg;

    @FXML
    private Label pass_error_msg;

    @FXML
    void initialize() {

    }

    @FXML
    void back_menu(ActionEvent event) {
        Singleton.getInstance().changeView("Connection!", "views/connection.fxml");
    }

    @FXML
    void register_account(ActionEvent event) {
        if(name_field.getText().isEmpty() || email_field.getText().isEmpty() || pass_field.getText().isEmpty()) return;
        name_error_msg.setText("");

//        try {
//            PlayerRemote handling = (PlayerRemote) Singleton.getInstance().client.rmiRequest(Settings.PLAYER_DATA);
//            int result = handling.register_player(name_field.getText(), email_field.getText(), pass_field.getText());
//            if(result == 0){
//                Singleton.getInstance().active_player = new Player(name_field.getText(), email_field.getText(), pass_field.getText());
//                Singleton.getInstance().changeView("Connection!", "views/connection.fxml");
//            }else if(result == 1){
//                name_error_msg.setText("Name is already picked by someone.");
//                name_error_msg.setTextFill(Color.RED);
//            }else{
//                name_error_msg.setText("Server didn't response.");
//            }
//        } catch (RemoteException | NotBoundException | MalformedURLException e) {
//            e.printStackTrace();
//        }

    }

    @Override
    public void server_updates() {

    }
}
