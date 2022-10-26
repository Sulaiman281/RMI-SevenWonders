package com.wonders.client.controller.trash;

import com.wonders.client.controller.Controller;
import com.wonders.client.pattern.Singleton;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.util.Duration;

public class Loading_Screen implements Controller {

    @FXML
    private ProgressBar progress_bar;
    @FXML
    private Label msg_box;
    private Timeline timeline;

    @FXML
    void initialize() {
        timeline = new Timeline();
    }

    public void nextScene(String title, String path) {
        msg_box.setText("Loading " + title + " Screen");

        KeyFrame keyFrame = new KeyFrame(Duration.seconds(2), e -> Singleton.getInstance().changeView(title, path),
                new KeyValue(progress_bar.progressProperty(), 1));

        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
    }

    @Override
    public void server_updates() {

    }
}
