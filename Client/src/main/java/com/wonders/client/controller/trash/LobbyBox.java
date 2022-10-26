package com.wonders.client.controller.trash;

import com.wonders.MenuRemote;
import com.wonders.Settings;
import com.wonders.client.controller.Controller;
import com.wonders.client.pattern.Singleton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class LobbyBox implements Controller {

    @FXML
    private HBox root;

    @FXML
    private Label lobby_name;

    @FXML
    private Label players;

    @FXML
    void join_lobby(ActionEvent event) {
//        try {
//            MenuRemote remote = (MenuRemote) Singleton.getInstance().client.rmiRequest(Settings.MENU);
//            int result = remote.join_lobby(Singleton.getInstance().active_player.getName(), lobby_name.getText());
//            if (result == 0) {
//                Singleton.getInstance().changeView("Lobby By " + lobby_name.getText(), "views/lobby.fxml");
//                Lobby controller = (Lobby) Singleton.getInstance().active_controller;
//                controller.setHostName(lobby_name.getText());
//                controller.add_player(false);
//            } else if (result == 1) {
//                Singleton.getInstance().showDialog("Houseful", "Lobby is full");
//            } else {
//                Singleton.getInstance().showDialog("!Lobby", "Lobby is not in the server.");
//            }
//        } catch (MalformedURLException | NotBoundException | RemoteException e) {
//            System.err.println(e.getMessage());
//        }
    }

    public void setData(String name, String players) {
        lobby_name.setText(name);
        this.players.setText(players);
    }

    public String getLobbyName() {
        return lobby_name.getText();
    }

    public HBox getRoot() {
        return root;
    }

    @Override
    public void server_updates() throws MalformedURLException, NotBoundException, RemoteException {

    }
}
