package com.wonders.client.controller.board_assets;

import com.wonders.GameRemote;
import com.wonders.Settings;
import com.wonders.client.controller.Controller;
import com.wonders.client.game.Board;
import com.wonders.client.game.Card;
import com.wonders.client.game.GameState;
import com.wonders.client.pattern.Singleton;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class OpponentView {

    @FXML
    private Label lN_Name;

    @FXML
    private Label lN_Shields;

    @FXML
    private Label lN_Coins;

    @FXML
    private Label city_name;

    @FXML
    private HBox stages_box;

    @FXML
    private HBox city_dResource;

    @FXML
    private ComboBox<Pane> cityBuilds;

    GameState states;

    Board board;

    ArrayList<CityCard> cityBuildings;

    @FXML
    void initialize() {
        cityBuildings = new ArrayList<>();
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public void setOpponent(String name) {
        String[] oppo = name.split("#");

        lN_Name.setText(oppo[0]);
        city_name.setText(oppo[1]);
        city_dResource.getChildren().add(getIcon(oppo[2].split("_")[2].split(":")[0], 40, 40));
        states = new GameState();
    }

    void update_opponent(String result) {
        String[] states = result.split("#");
        this.lN_Shields.setText(states[0]);
        this.lN_Coins.setText(states[1]);
        if (states.length == 4) {
            cityBuildings.clear();
            this.cityBuilds.getItems().clear();
            String[] cityBuilds = states[3].split(",");
            for (String cityBuild : cityBuilds) {
                boolean cond = false;
                for (CityCard cityBuilding : this.cityBuildings) {
                    if (cityBuilding.getName().equals(cityBuild.split("_")[1])) {
                        cond = true;
                        break;
                    }
                }
                if (cond) continue;

                FXMLLoader fxml = Singleton.getInstance().fxmlLoader("views/board_assets/cityCard.fxml");
                CityCard cont = fxml.getController();
                cont.setCard(cityBuild);
                cityBuildings.add(cont);
                this.cityBuilds.getItems().add(fxml.getRoot());
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
            System.err.println(file+" image not found");
        }
        imageView.setFitWidth(radius*2);
        imageView.setFitHeight(radius*2);
        imageView.setLayoutX(0);
        imageView.setLayoutY(0);
        Label label = new Label(text);
        label.setFont(Font.font("Algerian", FontWeight.BOLD, 16));
        label.setTextFill(Color.BLACK);
        label.setPrefSize(radius*2, radius*2);
        label.setAlignment(Pos.CENTER);
        pane.getChildren().add(imageView);
        pane.getChildren().add(label);
        return pane;
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

    public void update_opponent() throws MalformedURLException, NotBoundException, RemoteException {
        GameRemote remote = (GameRemote) Singleton.getInstance().client.rmiRequest(Settings.GAME_HANDLING);
        String result = remote.opponentsUpdateState(board.match_id, lN_Name.getText());
        if (result == null) return;
        update_opponent(result);
    }

    public String getName() {
        return lN_Name.getText();
    }
}
