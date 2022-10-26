package com.wonders.client;

import com.wonders.client.game.GameBoard;
import com.wonders.client.pattern.Singleton;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {


    @Override
    public void start(Stage stage) {
        Singleton.getInstance().main_stage = stage;
//        Singleton.getInstance().loadNextScene("Connection!", "views/connection.fxml");
        stage.setTitle("Seven Wonders Game");
        GameBoard board = new GameBoard();
        Singleton.getInstance().board = board;
        stage.setScene(board.getScene());
        stage.show();
        Singleton.getInstance().createTimeline();
        Singleton.getInstance().timeline.play();
    }

    public static void main(String[] args) {
        Images.loadCityImages();
        launch();
    }
}