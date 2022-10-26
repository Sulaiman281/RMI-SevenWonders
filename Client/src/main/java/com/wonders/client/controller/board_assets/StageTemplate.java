package com.wonders.client.controller.board_assets;

import com.wonders.client.pattern.Singleton;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.net.URISyntaxException;

public class StageTemplate {

    @FXML
    private HBox root;

    @FXML
    private FlowPane costs;

    @FXML
    private HBox effect;

    public String eff;
    public String cost;

    public void setStage(String stage) {
        String[] s = stage.split("_");
        loadEffects(s[0]);
        loadCost(s[1]);
    }

    public void update(boolean isComplete) {
        if (isComplete) {
            costs.getChildren().clear();
            effect.setLayoutX(0);
            effect.setLayoutY(0);
            effect.setPrefWidth(root.getPrefWidth());
            effect.setPrefHeight(root.getPrefHeight());
            effect.setAlignment(Pos.CENTER);
            effect.setStyle("""
                            -fx-background-color: white;
                            """);
        }
    }

    void loadCost(String cost) {
        this.cost = cost;
        if (cost.contains("&")) {
            String[] c = cost.split("&");
            for (String s : c) {
                String[] r = s.split(":");
                int amount = Integer.parseInt(r[1]);
                for (int i = 0; i < amount; i++) {
                    costs.getChildren().add(getIcon(r[0], "", 10));
                }
            }
        } else {
            String[] r = cost.split(":");
            int amount = Integer.parseInt(r[1]);
            for (int i = 0; i < amount; i++) {
                costs.getChildren().add(getIcon(r[0], "", 10));
            }
        }
    }

    void loadEffects(String effect) {
        this.eff = effect;
        if (effect.contains("&")) {
            String[] e = effect.split("&");
            for (String s : e) {
                String[] r = s.split(":");
                int amount = Integer.parseInt(r[1]);
                if (r[0].equals("VP") || r[0].equals("SHIELD") || r[0].equals("COIN")) {
                    this.effect.getChildren().add(getIcon(r[0], amount + "", 20));
                    continue;
                }
                for (int i = 0; i < amount; i++) {
                    this.effect.getChildren().add(getIcon(r[0], "", 20));
                }
            }
        } else if (effect.contains("/")) {
            String[] e = effect.split("/");
            for (String s : e) {
                String[] r = s.split(":");
                int amount = Integer.parseInt(r[1]);
                if (r[0].equals("VP") || r[0].equals("SHIELD") || r[0].equals("COIN")) {
                    this.effect.getChildren().add(getIcon(r[0], amount + "", 20));
                    continue;
                }
                for (int i = 0; i < amount; i++) {
                    this.effect.getChildren().add(getIcon(r[0], "", 20));
                }
            }
        } else {
            String[] r = effect.split(":");
            int amount = Integer.parseInt(r[1]);
            if (r[0].equals("VP") || r[0].equals("SHIELD") || r[0].equals("COIN")) {
                this.effect.getChildren().add(getIcon(r[0], amount + "", 20));
                return;
            }
            for (int i = 0; i < amount; i++) {
                this.effect.getChildren().add(getIcon(r[0], "", 20));
            }
        }
    }

    public Pane getIcon(String file, String text, double radius) {
        String path = "images/icons/" + file;
        Pane pane = new Pane();
        pane.setPrefSize(radius * 2, radius * 2);
        ImageView imageView = new ImageView();
        try {
            imageView.setImage(Singleton.getInstance().loadImage(path));
        } catch (URISyntaxException e) {
            System.err.println(file + " image not found");
        }
        imageView.setFitWidth(radius * 2);
        imageView.setFitHeight(radius * 2);
        imageView.setLayoutX(0);
        imageView.setLayoutY(0);
        Label label = new Label(text);
        label.setFont(Font.font("Algerian", FontWeight.BOLD, 16));
        label.setTextFill(Color.BLACK);
        label.setPrefSize(radius * 2, radius * 2);
        label.setAlignment(Pos.CENTER);
        pane.getChildren().add(imageView);
        pane.getChildren().add(label);
        return pane;
    }
}
