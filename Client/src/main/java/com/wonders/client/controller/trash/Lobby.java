package com.wonders.client.controller.trash;

import com.wonders.client.controller.Controller;
import com.wonders.client.game.Board;
import com.wonders.client.pattern.Singleton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class Lobby implements Controller {

    @FXML
    private VBox root;

    @FXML
    private Label lobby_name;

    @FXML
    private Label total_players;

    public ArrayList<OpponentBox> opponents;

    @FXML
    void initialize() {
        opponents = new ArrayList<>();
    }

    @FXML
    void back_dashboard(ActionEvent event) {
//        try {
//            MenuRemote remote = (MenuRemote) Singleton.getInstance().client.rmiRequest(Settings.MENU);
//            remote.leave_lobby(Singleton.getInstance().active_player.getName());
//        } catch (MalformedURLException | NotBoundException | RemoteException e) {
//            e.printStackTrace();
//        }
//        Singleton.getInstance().changeView("Dashboard", "views/dashboard.fxml");
    }

    public void add_player(boolean isHost) {
        FXMLLoader fxml = Singleton.getInstance().fxmlLoader("views/room_player_box.fxml");
        RoomPlayerBox player = fxml.getController();
        if (isHost) {
            lobby_name.setText(Singleton.getInstance().active_player.getName());
            player.host();
        } else {
            // add the name of the host.
            player.join(lobby_name.getText());
        }
        root.getChildren().add(fxml.getRoot());
    }

    public void setHostName(String name) {
        lobby_name.setText(name);
    }

    public void add_opponent(String name, boolean isReady) {
        // check if opponent already exists
        for (OpponentBox opponent : opponents) {
            if (opponent.getName().equals(name)) {
                opponent.update(isReady);
                return;
            }
        }
        // add the opponent.
        FXMLLoader fxml = Singleton.getInstance().fxmlLoader("views/opponent_box.fxml");
        OpponentBox controller = fxml.getController();
        controller.add_opponent(name, isReady);
        root.getChildren().add(fxml.getRoot());
        opponents.add(controller);
    }

    boolean lobbyClosed = false;
    void startGame(String id) {
        if(lobbyClosed) return;
        lobbyClosed = true;
        Singleton.getInstance().main_stage.setScene(new Board(id).getScene());
        Singleton.getInstance().main_stage.setTitle("Game Board: "+Singleton.getInstance().active_player.getName());
//        Singleton.getInstance().main_stage.setFullScreen(true);
        Singleton.getInstance().main_stage.centerOnScreen();
    }

    void update_player_text(int size) {
        total_players.setText(String.format("Players ( %d / 4 )", size));
    }

    @Override
    public void server_updates() {
//        try {
//            MenuRemote remote = (MenuRemote) Singleton.getInstance().client.rmiRequest(Settings.MENU);
//            String result = remote.lobby_status(lobby_name.getText());
//            if (result == null) return;
//            String[] status = result.split("&");
//            if (!status[0].isEmpty()) {
//                startGame(status[0]);
//            }
//            String[] lobby_members = status[1].split(",");
//            if (opponents.size() + 1 > lobby_members.length) {
//                opponents.removeIf(op -> {
//                    if (!status[1].contains(op.getName())) {
//                        root.getChildren().remove(op.getRoot());
//                        return true;
//                    }
//                    return false;
//                });
//            }
//            int size = 0;
//            for (String s : lobby_members) {
//                String[] op = s.split(":");
//                if (op.length < 2) return;
//                size++;
//                if (op[0].equals(Singleton.getInstance().active_player.getName())) continue;
//                add_opponent(op[0], Boolean.parseBoolean(op[1]));
//            }
//            update_player_text(size);
//
//        } catch (MalformedURLException | NotBoundException | RemoteException e) {
//            e.printStackTrace();
//        }
    }
}
