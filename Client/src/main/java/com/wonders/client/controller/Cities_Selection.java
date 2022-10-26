package com.wonders.client.controller;

import com.wonders.GameRemote;
import com.wonders.Settings;
import com.wonders.client.Images;
import com.wonders.client.game.Board;
import com.wonders.client.pattern.Singleton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Cities_Selection implements Controller {

    @FXML
    private Rectangle rhodos_img;

    @FXML
    private Button rhodos_selection;

    @FXML
    private Rectangle olympia_img;

    @FXML
    private Button olympia_selection;

    @FXML
    private Rectangle babylon_img;

    @FXML

    private Button babylon_selection;

    @FXML
    private Rectangle ephesos_img;

    @FXML
    private Button ephesos_selection;

    @FXML
    private Rectangle halikarnassos_img;

    @FXML
    private Button halikarnassos_selection;

    @FXML
    private Rectangle alexandria_img;

    @FXML
    private Button alexandria_selection;

    @FXML
    private Rectangle gizah_img;

    @FXML
    private Button gizah_selection;

    Board board;

    @FXML
    void initialize() {
        alexandria_img.setFill(new ImagePattern(Images.alexandria_city));
        babylon_img.setFill(new ImagePattern(Images.babylon_city));
        ephesos_img.setFill(new ImagePattern(Images.ephesos_city));
        gizah_img.setFill(new ImagePattern(Images.gizah_city));
        halikarnassos_img.setFill(new ImagePattern(Images.halicrnassus_city));
        olympia_img.setFill(new ImagePattern(Images.olympia_city));
        rhodos_img.setFill(new ImagePattern(Images.rhodos_city));

        alexandria_selection.setOnAction(event("alexandria"));
        babylon_selection.setOnAction(event("babylon"));
        ephesos_selection.setOnAction(event("ephesos"));
        gizah_selection.setOnAction(event("gizah"));
        halikarnassos_selection.setOnAction(event("halicarnassus"));
        olympia_selection.setOnAction(event("olympia"));
        rhodos_selection.setOnAction(event("rhodos"));
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    void disableAll(){
        alexandria_selection.setDisable(true);
        babylon_selection.setDisable(true);
        ephesos_selection.setDisable(true);
        gizah_selection.setDisable(true);
        halikarnassos_selection.setDisable(true);
        olympia_selection.setDisable(true);
        rhodos_selection.setDisable(true);
    }

    EventHandler<ActionEvent> event(String cityName) {
        return e -> {
            // pass the city name and player name to game remote class to select the city.
            try {
                GameRemote remote = (GameRemote) Singleton.getInstance().client.rmiRequest(Settings.GAME_HANDLING);
                int result = remote.select_city(board.match_id, Singleton.getInstance().active_player.getName(), cityName);
                if (result == 0) {
                    Button source = (Button) e.getSource();
                    source.setText("chosen by: you");
                    disableAll();
                } else if (result == 1) {
                    Singleton.getInstance().showDialog("Already Selected", "City is Already Selected!\n Select other city");
                }
            } catch (MalformedURLException | NotBoundException | RemoteException ex) {
                ex.printStackTrace();
            }
        };
    }

    void update_selections(String city_name, String player_name) {
        switch (city_name) {
            case "alexandria" -> {
                alexandria_selection.setText("chosen by: " + player_name);
                alexandria_selection.setDisable(true);
            }
            case "babylon" -> {
                babylon_selection.setText("chosen by: " + player_name);
                babylon_selection.setDisable(true);
            }
            case "ephesos" -> {
                ephesos_selection.setText("chosen by: " + player_name);
                ephesos_selection.setDisable(true);
            }
            case "gizah" -> {
                gizah_selection.setText("chosen by: " + player_name);
                gizah_selection.setDisable(true);
            }
            case "halicarnassus" -> {
                halikarnassos_selection.setText("chosen by: " + player_name);
                halikarnassos_selection.setDisable(true);
            }
            case "olympia" -> {
                olympia_selection.setText("chosen by: " + player_name);
                olympia_selection.setDisable(true);
            }
            case "rhodos" -> {
                rhodos_selection.setText("chosen by: " + player_name);
                rhodos_selection.setDisable(true);
            }
        }
    }

    @Override
    public void server_updates() throws MalformedURLException, NotBoundException, RemoteException {
        try {
            GameRemote remote = (GameRemote) Singleton.getInstance().client.rmiRequest(Settings.GAME_HANDLING);
            String result = remote.selectedCities(board.match_id);
            if(result == null) return;
            for (String s : result.split(",")) {
                String[] data = s.split(":");
                if (data[0].equals(Singleton.getInstance().active_player.getName())) continue;
                if(!data[1].equals("null")) {
                    update_selections(data[1], data[0]);
                }
            }

            boolean cond = remote.allSelectedCity(board.match_id);
            if(cond){
                board.game_state = Board.Match_States.AGE_I_CARD;
                Singleton.getInstance().active_controller = board;
            }

        } catch (MalformedURLException | NotBoundException | RemoteException ex) {
            ex.printStackTrace();
        }
    }
}
