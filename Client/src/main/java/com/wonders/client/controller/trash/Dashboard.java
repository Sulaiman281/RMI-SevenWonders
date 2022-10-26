package com.wonders.client.controller.trash;

import com.wonders.MenuRemote;
import com.wonders.Settings;
import com.wonders.client.controller.Controller;
import com.wonders.client.pattern.Singleton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Dashboard implements Controller {

    @FXML
    private Label player_name;

    @FXML
    private Label total_players;

    @FXML
    private Button join_room_btn;

    @FXML
    void initialize() {
        player_name.setText(Singleton.getInstance().active_player.getName());
    }

    @FXML
    void credit(ActionEvent event) {

    }

    @FXML
    void help(ActionEvent event) {

    }

    @FXML
    void host_room(ActionEvent event) {
        // todo request the server to create a room.
        // todo load the newly created room and place the user in it.
        // todo disable join room button until he close the host room.

//        String active_player_name = Singleton.getInstance().active_player.getName();
//        try {
//            MenuRemote remote = (MenuRemote) Singleton.getInstance().client.rmiRequest(Settings.MENU);
//            int result = remote.create_lobby(active_player_name);
//            if (result == 1) {
//                Singleton.getInstance().changeView("Lobby By " + active_player_name, "views/lobby.fxml");
//                Lobby controller = (Lobby) Singleton.getInstance().active_controller;
//                controller.add_player(true);
//                join_room_btn.setDisable(true);
//            }
//        } catch (MalformedURLException | NotBoundException | RemoteException e) {
//            System.err.println(e.getMessage());
//        }
    }

    @FXML
    void join_room(ActionEvent event) {
        // todo list of rooms to join for game. and how the number of players in it.
        Singleton.getInstance().changeView("Rooms ", "views/lobbiesList.fxml");
    }

    @FXML
    void settings(ActionEvent event) {
        // todo update the player password
        // todo on / off music
    }

    @Override
    public void server_updates() throws MalformedURLException, NotBoundException, RemoteException {
//        MenuRemote remote = (MenuRemote) Singleton.getInstance().client.rmiRequest(Settings.MENU);
//        total_players.setText(String.format("%02d", remote.totalPlayers()));
//        int rooms = remote.totalRooms();
//        if (rooms == 0) {
//            join_room_btn.setDisable(true);
//            return;
//        } else join_room_btn.setDisable(false);
//        join_room_btn.setText(String.format("Join Room (%02d)", rooms));
    }
}
