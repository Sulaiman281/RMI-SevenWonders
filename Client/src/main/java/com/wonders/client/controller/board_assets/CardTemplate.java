package com.wonders.client.controller.board_assets;

import com.wonders.client.Images;
import com.wonders.client.game.Card;
import com.wonders.client.pattern.Singleton;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.net.URISyntaxException;

public class CardTemplate {

    @FXML
    private Pane card_root;
    @FXML
    private Rectangle type;

    @FXML
    private Label name;

    @FXML
    private VBox cost_list;

    @FXML
    private HBox effect_box;

    @FXML
    private Rectangle img;

    Card card;

    @FXML
    void initialize(){
        img.setFill(new ImagePattern(Images.getWonder()));
    }

    public void setCard(Card card) {
        this.card = card;
        // load the card template image according to the type.
        try {
            type.setFill(getTemplateFill(card.getType()));
        } catch (URISyntaxException e) {
            System.err.println("Card Pattern couldn't load");
        }
        name.setText(card.getName());
        set_cost_list(card.getCost());
        setEffects(card.getType(), card.getEffect());
    }

    public void setContextMenu(ContextMenu menu){
        card_root.setOnContextMenuRequested(e->{
            menu.show(card_root, Side.RIGHT, 0 , 0);
        });
    }

    void setEffects(String type, String effects) {
        if (type.equals("YC") || type.equals("PC")) {
            // target player sign - target card is BC or GC and prize is COIN 1 means
            // target players sign - target none and coin 5 prize
            // target players sign target none price = resources
            if(effects.equals("M-NONE=COIN:5")){
                effect_box.getChildren().add(getIcon("COIN", "5", 40, 40));
                effect_box.setAlignment(Pos.CENTER);
            }else if(effects.equals("LN-BC=COIN:1")){
                effect_box.getChildren().add(getIcon("LN_BC_C1", 100, 50));
            }else if(effects.equals("RN-BC=COIN:1")){
                effect_box.getChildren().add(getIcon("RN_BC_C1", 100, 50));
            }else if(effects.equals("ABN-BC=COIN:1")){
                effect_box.getChildren().add(getIcon("ABN_BC_COIN", 100, 50));
            }else if(effects.equals("ABN-GC=COIN:2")){
                effect_box.getChildren().add(getIcon("ABN_GC_COIN", 100, 50));
            }else if (effects.equals("BN-GC=COIN:1")){
                effect_box.getChildren().add(getIcon("BN_GC", 100, 50));
            }else if(effects.contains("M-NONE")){
                String e = effects.split("=")[1];
                if (e.contains("/")) {
                    String[] effect = e.split("/");
                    for (String s : effect) {
                        String[] value = s.split(":");
                        int amount = Integer.parseInt(value[1]);
                        effect_box.getChildren().add(getIcon(value[0], amount == 1 ? "" : "" + amount, 35, 35));
                        // add (/) icons after if there is next
                    }
                } else {
                    String[] value = e.split(":");
                    int amount = Integer.parseInt(value[1]);
                    if (value[0].equals("VP") || value[0].equals("SHIELD") || value[0].equals("COIN")) {
                        this.effect_box.getChildren().add(getIcon(value[0], amount == 1 ? "" : amount + "", 35, 35));
                        return;
                    }
                    for (int i = 0; i < amount; i++) {
                        effect_box.getChildren().add(getIcon(value[0], 35, 35));
                    }
                }
            }

        } else {
            if (effects.contains("/")) {
                String[] effect = effects.split("/");
                for (String s : effect) {
                    String[] value = s.split(":");
                    int amount = Integer.parseInt(value[1]);
                    effect_box.getChildren().add(getIcon(value[0], amount == 1 ? "" : "" + amount, 35, 35));
                    // add (/) icons after if there is next
                }
            } else {
                String[] value = effects.split(":");
                int amount = Integer.parseInt(value[1]);
                if (value[0].equals("VP") || value[0].equals("SHIELD") || value[0].equals("COIN")) {
                    this.effect_box.getChildren().add(getIcon(value[0], amount == 1 ? "" : amount + "", 35, 35));
                    return;
                }
                for (int i = 0; i < amount; i++) {
                    effect_box.getChildren().add(getIcon(value[0], 35, 35));
                }
            }
        }
    }


    void set_cost_list(String costs) {
        if (costs.equals("FREE")) return;
        if (costs.contains("&")) {
            String[] cost = costs.split("&");
            for (String s : cost) {
                String[] value = s.split(":");
                int amount = Integer.parseInt(value[1]);
                for (int i = 0; i < amount; i++) {
                    cost_list.getChildren().add(getIcon(value[0], 20, 20));
                }
            }
        } else {
            String[] value = costs.split(":");
            int amount = Integer.parseInt(value[1]);
            if (value[0].equals("COIN")) {
                // load coin image and put amount in it.
                cost_list.getChildren().add(getIcon(value[0], amount + "", 20, 20));
            } else {
                for (int i = 0; i < amount; i++) {
                    cost_list.getChildren().add(getIcon(value[0], 20, 20));
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

    public StackPane getCardIcon(String type, String value){
        try {
            StackPane pane = new StackPane();

            ImageView img = new ImageView(Singleton.getInstance().loadImage("images/icons/"+type));
            img.setFitWidth(25);
            img.setFitHeight(50);
            pane.getChildren().add(img);

            Label label = new Label(value);
            label.setFont(Font.font("Arial", 14));
            label.setAlignment(Pos.CENTER);
            pane.getChildren().add(label);
            return pane;

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Paint getTemplateFill(String type) throws URISyntaxException {
        return new ImagePattern(Singleton.getInstance().loadImage("images/cards/"+type));
    }

    public Card getCard() {
        return card;
    }

    public Pane getCard_root() {
        return card_root;
    }
}
