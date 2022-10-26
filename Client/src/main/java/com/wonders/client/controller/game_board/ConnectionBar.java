package com.wonders.client.controller.game_board;

import com.wonders.PlayerRemote;
import com.wonders.Settings;
import com.wonders.client.ClientRemote;
import com.wonders.client.pattern.Singleton;
import com.wonders.model.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class ConnectionBar {

    @FXML
    private TextField name_input;

    @FXML
    private TextField server_address;

    @FXML
    void initialize(){
        try {
            server_address.setText(InetAddress.getLocalHost().getHostAddress());
        } catch (UnknownHostException ignore) {

        }
    }

    @FXML
    void connect_server(ActionEvent event) {
        if(name_input.getText().isEmpty()) return;
        if(Singleton.getInstance().client == null) Singleton.getInstance().client = new ClientRemote();
        Singleton.getInstance().client.server_address = server_address.getText();

        try {
            PlayerRemote remote = (PlayerRemote) Singleton.getInstance().client.rmiRequest(Settings.PLAYER_DATA);
            int result = remote.connect_player(name_input.getText());
            if(result == 0){
                Singleton.getInstance().active_player = new Player(name_input.getText());
                Singleton.getInstance().board.connectionEstablish();
            }else{
                Singleton.getInstance().showDialog("User Already Exists"," Change Your Name Please");
            }
        } catch (MalformedURLException | NotBoundException | RemoteException e) {
            e.printStackTrace();
        }
    }

}
