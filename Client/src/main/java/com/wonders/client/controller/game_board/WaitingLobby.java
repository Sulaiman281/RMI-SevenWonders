package com.wonders.client.controller.game_board;

import com.wonders.MenuRemote;
import com.wonders.Settings;
import com.wonders.client.controller.Controller;
import com.wonders.client.pattern.Singleton;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class WaitingLobby extends VBox implements Controller {

    private Label timer;
    private Label msg;
    private Timeline timeline;
    private IntegerProperty seconds;

    public boolean startGame = false;

    public WaitingLobby() {

        setStyle("""
                -fx-background-color: #806438;
                -fx-background-radius: 10;
                -fx-border-radius: 10;
                """);

        this.setAlignment(Pos.CENTER);
        this.setSpacing(20);

        new Scene(this, 300,300);
        timer = new Label();
        timeline = new Timeline();
        seconds = new SimpleIntegerProperty(0);

        timeline.setCycleCount(Animation.INDEFINITE);
        KeyFrame frame = new KeyFrame(Duration.seconds(1), e -> {
            seconds.set(seconds.get() + 1);
            timer.setText(String.format("Total Seconds: %03d", seconds.get()));
        });

        this.getChildren().add(timer);

        timeline.getKeyFrames().add(frame);
        timeline.play();

        msg = new Label("Connecting to the Server.");
        msg.setAlignment(Pos.CENTER);
        msg.setFont(Font.font("Arial", 22));
        msg.setTextFill(Color.WHITE);
        msg.setWrapText(true);
        this.getChildren().add(msg);
    }

    @Override
    public void server_updates() throws MalformedURLException, NotBoundException, RemoteException {
        MenuRemote remote = (MenuRemote) Singleton.getInstance().client.rmiRequest(Settings.MENU);
        int result = remote.play_request(Singleton.getInstance().active_player.getName());
        if (result == 0) {
            msg.setText("Starting the game.");
            startGame = true;
            timeline.stop();
            ((Stage)this.getScene().getWindow()).close();
        } else if (result == 1) msg.setText("waiting for the players.");
        else if (result == 2)
            msg.setText("found lobby waiting for players.");
        else if (result == 3)
            msg.setText("created lobby waiting for players.");
    }
}
