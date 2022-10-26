package com.wonders.client.pattern;

import com.wonders.client.ClientRemote;
import com.wonders.client.Main;
import com.wonders.client.controller.Controller;
import com.wonders.client.controller.trash.Loading_Screen;
import com.wonders.client.game.GameBoard;
import com.wonders.model.Age;
import com.wonders.model.Player;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Singleton {

    public ClientRemote client;

    private static Singleton _instance;

    public Stage main_stage;

    public Controller active_controller;

    public Player active_player;

    public Timeline timeline;

    public GameBoard board;

    public Age age;

    private Singleton() {
    }

    public static Singleton getInstance() {
        if (_instance == null) _instance = new Singleton();
        return _instance;
    }

    public void changeView(String title, String path) {
        FXMLLoader fxmlLoader = fxmlLoader(path);
        active_controller = fxmlLoader.getController();
        switchScene(title, new Scene(fxmlLoader.getRoot()));
    }

    public void switchScene(String title, Scene scene) {
        if (main_stage == null) return;

        main_stage.setTitle(title);
        main_stage.setScene(scene);
    }

    public FXMLLoader fxmlLoader(String path) {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource(path));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return loader;
    }

    public Image loadImage(String path) throws URISyntaxException {
        return new Image(Main.class.getResource(path.concat(".png")).toURI().toString());
    }

    public void createTimeline() {
        if(timeline != null) return;
        timeline = new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE);
        KeyFrame frame = new KeyFrame(Duration.millis(300), e -> {
            if(active_controller == null) return;
            try {
                active_controller.server_updates();
            } catch (MalformedURLException | NotBoundException | RemoteException ex) {
                System.err.println(ex.getMessage());
            }
        });
        timeline.getKeyFrames().add(frame);
    }

    public void showDialog(String heading, String msg) {
        Dialog<String> dialog = new Dialog<>();
        dialog.setHeaderText(heading);
        dialog.setContentText(msg);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.showAndWait();
    }

    public void loadNextScene(String title, String viewPath) {
        changeView("Loading Screen.", "views/loading_screen.fxml");
        ((Loading_Screen) active_controller).nextScene(title, viewPath);
    }
}
