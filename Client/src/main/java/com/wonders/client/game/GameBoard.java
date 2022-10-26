package com.wonders.client.game;

import com.wonders.MenuRemote;
import com.wonders.Settings;
import com.wonders.client.controller.Controller;
import com.wonders.client.controller.game_board.WaitingLobby;
import com.wonders.client.pattern.Singleton;
import javafx.animation.FadeTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class GameBoard extends BorderPane implements Controller {

    public GameBoard(){
        new Scene(this);
        this.setPrefSize(1200, 800);

        FXMLLoader loader = Singleton.getInstance().fxmlLoader("views/game_board/connection_bar.fxml");
        setTop(loader.getRoot());
    }

    public void connectionEstablish() {
        change_top(getTop(), null);
        Button play_button = new Button("PLAY");
        play_button.setFont(Font.font("Algerian", 40));
        play_button.setStyle("""
                -fx-background-color: #ff4b1f;
                -fx-background-radius: 10;
                -fx-border-radius: 10;
                """);
        play_button.hoverProperty().addListener((observer, old, newValue) ->{
            if(newValue){
                play_button.setStyle("""
                -fx-background-color: #ff8769;
                -fx-background-radius: 10;
                -fx-border-radius: 10;
                """);
            }else{
                play_button.setStyle("""
                -fx-background-color: #ff4b1f;
                -fx-background-radius: 10;
                -fx-border-radius: 10;
                """);
            }
        });
        play_button.setOnAction(e->{
            Stage stage = new Stage();
            stage.initStyle(StageStyle.TRANSPARENT);
            WaitingLobby waitingLobby = new WaitingLobby();
            stage.setScene(waitingLobby.getScene());
            Singleton.getInstance().active_controller = waitingLobby;
            stage.showAndWait();
            if(waitingLobby.startGame){
                String id = null;
                try{
                    MenuRemote remote = (MenuRemote) Singleton.getInstance().client.rmiRequest(Settings.MENU);
                    id = remote.createMatch(Singleton.getInstance().active_player.getName());
                } catch (MalformedURLException | NotBoundException | RemoteException malformedURLException) {
                    malformedURLException.printStackTrace();
                }
                Singleton.getInstance().main_stage.setScene(new Board(id).getScene());
                Singleton.getInstance().main_stage.setTitle("Game Board: "+Singleton.getInstance().active_player.getName());
//        Singleton.getInstance().main_stage.setFullScreen(true);
                Singleton.getInstance().main_stage.centerOnScreen();
            }
        });
        play_button.setPrefSize(200, 200);
        setCenter(play_button);
    }

    public void change_top(Node a, Node b){
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(2),a);
        fadeTransition.setToValue(0);
        fadeTransition.setFromValue(1);
        fadeTransition.play();
        fadeTransition.setOnFinished(e->{
            this.setTop(b);
        });
    }

    @Override
    public void server_updates() throws MalformedURLException, NotBoundException, RemoteException {

    }
}
