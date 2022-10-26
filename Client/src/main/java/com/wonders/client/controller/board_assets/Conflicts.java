package com.wonders.client.controller.board_assets;

import com.wonders.GameRemote;
import com.wonders.Settings;
import com.wonders.client.controller.Controller;
import com.wonders.client.controller.Player_View;
import com.wonders.client.controller.game_board.GameResult;
import com.wonders.client.pattern.Singleton;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Conflicts implements Controller {

    @FXML
    private AnchorPane root;

    @FXML
    private Label left_player;

    @FXML
    private Label right_player;

    @FXML
    private Label player;

    @FXML
    private Label left_shield;

    @FXML
    private Label shields;

    @FXML
    private Label right_shield;

    @FXML
    private Label left_conflict;

    @FXML
    private Label right_conflict;

    Player_View view;

    public void setup(String conflicts){
        String[] players = conflicts.split("_");
        // setup left neighbour opponent
        String[] left_player = players[0].split(":");
        this.left_player.setText(left_player[0]);
        this.left_shield.setText(left_player[1]);
        this.left_conflict.setText(left_player[2]);
        // player
        this.player.setText(Singleton.getInstance().active_player.getName());
        this.shields.setText(players[1]);
        // right neighbour opponent
        String[] right = players[2].split(":");
        this.right_player.setText(right[0]);
        this.right_shield.setText(right[1]);
        this.right_conflict.setText(right[2]);
    }

    public void closingTimer(Player_View pView) {
        this.view = pView;
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(10), root);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(1);
        fadeTransition.play();
    }

    @Override
    public void server_updates() throws MalformedURLException, NotBoundException, RemoteException {
        GameRemote remote = (GameRemote) Singleton.getInstance().client.rmiRequest(Settings.GAME_HANDLING);
        String result = remote.nextAge(view.board.match_id, Singleton.getInstance().active_player.getName());
        System.out.println(result);
        if(result.contains("WAIT")) return;
        if (result.equals("GAMEOVER")) {
            // game over scene
            result = remote.gameResult(view.board.match_id);
            GameResult gameResult = new GameResult();
            gameResult.setResult(result);
            Singleton.getInstance().main_stage.setScene(gameResult.getScene());
            ((Stage)root.getScene().getWindow()).close();
            Singleton.getInstance().timeline.stop();
        } else {
            Singleton.getInstance().active_controller = view;
            view.playersCards(result);
            FadeTransition fadeTransition = new FadeTransition(Duration.seconds(20), root);
            fadeTransition.setFromValue(1);
            fadeTransition.setToValue(0.5);
            fadeTransition.play();
            fadeTransition.setOnFinished(e->{
                ((Stage)root.getScene().getWindow()).close();
            });
            view.state = Player_View.PlayerState.CARD_PLACED;
        }
    }
}
