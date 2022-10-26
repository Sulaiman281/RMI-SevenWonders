package com.wonders.client.controller.trash;

import com.wonders.PlayerRemote;
import com.wonders.Settings;
import com.wonders.client.controller.Controller;
import com.wonders.client.pattern.Singleton;
import com.wonders.model.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Authentication implements Controller {

    @FXML
    private TextField name_field;

    @FXML
    private PasswordField pass_field;

    @FXML
    private Label name_error_msg;

    @FXML
    private Label pass_error_msg;

    @FXML
    void back_menu(ActionEvent event) {
        Singleton.getInstance().changeView("Connection!", "views/connection.fxml");
    }

    @FXML
    void login_account(ActionEvent event) {
//        if(name_field.getText().isEmpty() || pass_field.getText().isEmpty()) return;
//        name_error_msg.setText("");
//
//        try {
//            PlayerRemote handling = (PlayerRemote) Singleton.getInstance().client.rmiRequest(Settings.PLAYER_DATA);
//            int result = handling.login_player(name_field.getText(), pass_field.getText());
//            if(result == 0){
//                Singleton.getInstance().active_player = new Player(name_field.getText(), pass_field.getText());
//                Singleton.getInstance().changeView("Connection!", "views/connection.fxml");
//            }else if(result == -2){
//                name_error_msg.setText("User does not exist!");
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
