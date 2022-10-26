package com.wonders.client.controller.trash;

import com.wonders.client.controller.Controller;
import com.wonders.client.pattern.Singleton;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class RoomPlayerBox implements Controller {

    @FXML
    private Label player_name;

    @FXML
    private Button ready_btn;


    public void host() {
//        player_name.setText(Singleton.getInstance().active_player.getName());
//        ready_btn.setText("Start");
//        ready_btn.setOnAction(e->{
//            try {
//                MenuRemote remote = (MenuRemote) Singleton.getInstance().client.rmiRequest(Settings.MENU);
//                boolean result = remote.start_game(player_name.getText());
//                System.out.println(player_name.getText()+" request to the server. "+result);
//            } catch (MalformedURLException | NotBoundException | RemoteException malformedURLException) {
//                malformedURLException.printStackTrace();
//            }
//        });
    }

    public void join(String lobbyName) {
        player_name.setText(Singleton.getInstance().active_player.getName());
        ready_btn.setText("ready");
        ready_btn.setOnAction(e -> {
            // TODO send all players that you are ready.
//            try {
//                MenuRemote remote = (MenuRemote) Singleton.getInstance().client.rmiRequest(Settings.MENU);
//                boolean cond = remote.me_ready(lobbyName, Singleton.getInstance().active_player.getName());
//                ready_btn.setText(!cond ? "Ready" : "unready");
//            } catch (MalformedURLException | NotBoundException | RemoteException ex) {
//                ex.printStackTrace();
//            }

        });
    }

    @Override
    public void server_updates() {

    }
}
