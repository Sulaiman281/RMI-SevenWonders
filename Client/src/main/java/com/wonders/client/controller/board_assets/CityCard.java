package com.wonders.client.controller.board_assets;

import com.wonders.client.pattern.Singleton;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.net.URISyntaxException;

public class CityCard {

    @FXML
    private Rectangle type;

    @FXML
    private Label card_name;

    @FXML
    private HBox effects;

    // TYPE_NAME_EFFECT
    public void setCard(String card) {
        String[] data = card.split("_");
        type.setFill(getTemplateFill(data[0]));
        card_name.setText(data[1]);
        setEffects(data[0], data[2]);
    }

    void setEffects(String type, String effects) {
        if (type.equals("YC") || type.equals("PC")) {
            // target player sign - target card is BC or GC and prize is COIN 1 means
            // target players sign - target none and coin 5 prize
            // target players sign target none price = resources
            //
        } else {
            if (effects.contains("/")) {
                String[] effect = effects.split("/");
                for (String s : effect) {
                    String[] value = s.split(":");
                    int amount = Integer.parseInt(value[1]);
                    if (value[0].equals("VP")) {
                        this.effects.getChildren().add(getIcon(value[0], amount + "", 30, 30));
                    }else {
                        for (int i = 0; i < amount; i++) {
                            this.effects.getChildren().add(getIcon(value[0], 30, 30));
                        }
                    }
                }
            } else {
                String[] value = effects.split(":");
                int amount = Integer.parseInt(value[1]);
                if (value[0].equals("VP")) {
                    this.effects.getChildren().add(getIcon(value[0], amount + "", 30, 30));
                }else {
                    for (int i = 0; i < amount; i++) {
                        this.effects.getChildren().add(getIcon(value[0], 30, 30));
                    }
                }
            }
        }
    }

    public ImageView getIcon(String file, double width, double height) {
        String path = "images/icons/" + file;
        ImageView imageView = new ImageView();
        try {
            imageView.setImage(Singleton.getInstance().loadImage(path));
        } catch (URISyntaxException e) {
            System.err.println(file + " image not found");
        }
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        return imageView;
    }

    public Pane getIcon(String file, String text, double width, double height) {
        String path = "images/icons/" + file;
        Pane pane = new Pane();
        pane.setPrefSize(width, height);
        ImageView imageView = new ImageView();
        try {
            imageView.setImage(Singleton.getInstance().loadImage(path));
        } catch (URISyntaxException e) {
            System.err.println(file + " image not found");
        }
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        imageView.setLayoutX(0);
        imageView.setLayoutY(0);
        Label label = new Label(text);
        label.setFont(Font.font("Algerian", FontWeight.BOLD, 16));
        label.setTextFill(Color.WHITE);
        label.setPrefSize(width, height);
        label.setAlignment(Pos.CENTER);
        pane.getChildren().add(imageView);
        pane.getChildren().add(label);
        return pane;
    }

    private Paint getTemplateFill(String type) {
        switch (type) {
            case "BC" -> {
                return Color.BROWN;
            }
            case "GC" -> {
                return Color.GREY;
            }
            case "PC" -> {
                return Color.PURPLE;
            }
            case "RC" -> {
                return Color.RED;
            }
            case "YC" -> {
                return Color.ORANGE;
            }
            case "$C" -> {
                return Color.GREEN;
            }
            case "HC" -> {
                return Color.BLUE;
            }

        }
        return Color.WHITE;
    }

    public String getName(){
        return card_name.getText();
    }
}
