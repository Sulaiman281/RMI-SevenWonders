package com.wonders.client.controller;

import com.wonders.GameRemote;
import com.wonders.Settings;
import com.wonders.client.Main;
import com.wonders.client.controller.board_assets.*;
import com.wonders.client.game.Board;
import com.wonders.client.game.Card;
import com.wonders.client.pattern.Singleton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class Player_View implements Controller {

    public enum PlayerState {
        CARD_REQUEST,
        CARD_PLACING,
        CARD_PLACED,
        CARD_PASSING
    }

    public PlayerState state;

    @FXML
    private Pane root;

    @FXML
    private HBox cards;

    @FXML
    private FlowPane city_buildings;

    @FXML
    private HBox stages_box;

    @FXML
    private Label discarded_cards;

    @FXML
    private Label age_name;

    @FXML
    private Label passing_side;

    @FXML
    private Button game_status;

    @FXML
    private Label player_coins;

    @FXML
    private Label shields_count;

    public Board board;

    public ArrayList<OpponentView> opponents;

    public ArrayList<StageTemplate> stages;

    public ArrayList<CityCard> cityCards;

    @FXML
    void initialize() {
        state = PlayerState.CARD_REQUEST;
        stages = new ArrayList<>();
        opponents = new ArrayList<>();
        cityCards = new ArrayList<>();

        update_age();
        waitingStage = new Stage();
        waitingStage.initStyle(StageStyle.TRANSPARENT);
    }

    Stage waitingStage;

    private void update_age() {
        passing_side.setText(Singleton.getInstance().age.passing_side == 0 ? "Clockwise" : "AntiClockwise");
        age_name.setText(Singleton.getInstance().age.name);
        discarded_cards.setText(String.format("%02d", Singleton.getInstance().age.discards));
    }

    @FXML
    void music_toggle(ActionEvent event) {

    }

    public void playerUpdates() {
        player_coins.setText(String.format("%02d", Singleton.getInstance().active_player.getGameState().getCoins()));
        shields_count.setText(String.format("%01d", Singleton.getInstance().active_player.getGameState().getShields()));
    }

    public void setup(Board board) {
        this.board = board;
        // setup background image according to city name
        try {
            root.setStyle("-fx-background-image: url(" + Main.class.getResource("images/").toURI() +
                    Singleton.getInstance().active_player.getGameState().getCity_name() + ".png);\n" +
                    "-fx-background-size: cover;\n" +
                    "-fx-background-position: center;");
        } catch (URISyntaxException ignored) {

        }

        String[] stages = Singleton.getInstance().active_player.getGameState().getStages().split(",");
        for (String stage : stages) {
            FXMLLoader fxml = Singleton.getInstance().fxmlLoader("views/board_assets/stages_template.fxml");
            StageTemplate cont = fxml.getController();
            cont.setStage(stage);
            this.stages_box.getChildren().add(fxml.getRoot());
            this.stages.add(cont);
        }
        // default resources
        String dr = Singleton.getInstance().active_player.getGameState().getDefault_resource();
        FXMLLoader fxml = Singleton.getInstance().fxmlLoader("views/board_assets/cityCard.fxml");
        CityCard cont = fxml.getController();
        cont.setCard(dr);
        city_buildings.getChildren().add(fxml.getRoot());
    }

    void waitingAfterMove(Node node) {
        state = PlayerState.CARD_PASSING;
        Singleton.getInstance().active_player.getCards().clear();
        cards.getChildren().clear();
        VBox box = new VBox();
        box.setAlignment(Pos.TOP_CENTER);
        box.setSpacing(10);
        Label label = new Label("Waiting for other to play their move");
        label.setWrapText(true);
        label.setFont(Font.font("Arial", 18));
        box.getChildren().add(label);
        box.getChildren().add(node);
        waitingStage.setScene(new Scene(box, 160, 240));
        waitingStage.show();
    }

    public void updating_cards() {
        // updating the cards players have
        cards.getChildren().clear();
        for (Card card : Singleton.getInstance().active_player.getCards()) {
            FXMLLoader fxml = Singleton.getInstance().fxmlLoader("views/board_assets/card_template.fxml");
            CardTemplate controller = fxml.getController();
            controller.setCard(card);
            ContextMenu contextMenu = new ContextMenu();
            // confirm if this card can be place to stage or not.

            contextMenu.getItems().add(
                    createMenuItem("Sell to Bank", e -> {
                        // Sell the card to the bank
                        String result = cardAction("SELL#" + card);
                        if (result.equals("0")) waitingAfterMove(controller.getCard_root());
                        else {
                            Singleton.getInstance().showDialog("result " + result, "couldn't sell the card to the bank there must be error.");
                        }
                        //SELL#CARD
                    })
            );

            contextMenu.getItems().add(
                    createMenuItem("Place In City", e -> {
                        // place the card into city.
                        if (card.getPlaceable().equals("RED")) {
                            Singleton.getInstance().showDialog("RED Border", "You don't have resources to play this card.");
                            return;
                        }
                        System.out.println(card);
                        String result = cardAction("CITY#" + card.toString() + "#" + card.getPlaceable());
                        if (result.equals("0")) waitingAfterMove(controller.getCard_root());
                        else {
                            Singleton.getInstance().showDialog("result ", result);
                        }
                        //CITY#CARD#COST // cost will be what we have in placeable variable
                    })
            );

            contextMenu.getItems().add(
                    createMenuItem("Place In Stage", e -> {
                        // place the card into stage.
                        String result = cardAction("STAGE#" + card.toString());
                        if (result.equals("0")) waitingAfterMove(controller.getCard_root());
                        else {
                            Singleton.getInstance().showDialog("result ", result);
                        }
                        //STAGE#CARD
                    })
            );

            controller.setContextMenu(contextMenu);

            if (card.getPlaceable().equals("RED")) {
                controller.getCard_root().setStyle("""
                        -fx-border-color: red;
                        -fx-border-width: 4;
                        """);
            } else if (card.getPlaceable().contains("COIN")) {
                controller.getCard_root().setStyle("""
                        -fx-border-color: yellow;
                        -fx-border-width: 4;
                        """);


            } else {
                controller.getCard_root().setStyle("""
                        -fx-border-color: lightgreen;
                        -fx-border-width: 4;
                        """);

            }

            cards.getChildren().add(fxml.getRoot());
        }
    }

    String cardAction(String action) {
        try {
            GameRemote remote = (GameRemote) Singleton.getInstance().client.rmiRequest(Settings.GAME_HANDLING);
            return remote.card_action_place(board.match_id, Singleton.getInstance().active_player.getName(),
                    action);
        } catch (MalformedURLException | NotBoundException | RemoteException malformedURLException) {
            malformedURLException.printStackTrace();
            return "TIMEOUT";
        }
    }

    MenuItem createMenuItem(String str, EventHandler<ActionEvent> event) {
        MenuItem item = new MenuItem(str);
        item.setOnAction(event);
        return item;
    }

    void updateStages(String stage) {
        String[] stages = stage.split(",");
        for (String s : stages) {
            String[] z = s.split("_");
            for (StageTemplate st : this.stages) {
                if (z[0].equals(st.eff)) {
                    st.update(Boolean.parseBoolean(z[1]));
                }
            }
        }
    }

    public void setCityCards(String cityCard) {
        String[] cards = cityCard.split(",");
        for (String card : cards) {
            boolean cond = false;
            for (CityCard cc : cityCards) {
                if (cc.getName().equals(card.split("_")[1])) {
                    cond = true;
                    break;
                }
            }
            if (cond) continue;

            FXMLLoader fxml = Singleton.getInstance().fxmlLoader("views/board_assets/cityCard.fxml");
            CityCard cont = fxml.getController();
            cont.setCard(card);
            cityCards.add(cont);
            city_buildings.getChildren().add(fxml.getRoot());
        }

    }

    @Override
    public void server_updates() throws MalformedURLException, NotBoundException, RemoteException {
        switch (state) {
            case CARD_REQUEST -> {
                // CARD#PLACABLE#DISCOUNT
                GameRemote remote = (GameRemote) Singleton.getInstance().client.rmiRequest(Settings.GAME_HANDLING);
                String result = remote.player_CardRequest(board.match_id, Singleton.getInstance().active_player.getName());
                if (result == null || result.isEmpty()) return;
                playersCards(result);
                result = remote.playerUpdateState(board.match_id, Singleton.getInstance().active_player.getName());
                String[] up = result.split("#");
                Singleton.getInstance().active_player.getGameState().setCoins(Integer.parseInt(up[0]));
                Singleton.getInstance().active_player.getGameState().setShields(Integer.parseInt(up[1]));
                updateStages(up[2]);
                if (up.length == 4)
                    setCityCards(up[3]);
                playerUpdates();
                for (OpponentView opponent : opponents) {
                    opponent.update_opponent();
                }
                state = PlayerState.CARD_PLACING;
            }
            case CARD_PASSING -> {
                GameRemote remote = (GameRemote) Singleton.getInstance().client.rmiRequest(Settings.GAME_HANDLING);
                String result = remote.passingRequest(board.match_id, Singleton.getInstance().active_player.getName());
                if (result == null) return;
                if (result.equals("WAIT")) return;
                if (result.contains("CONFLICT")) {
                    waitingStage.close();
                    System.out.println("Military Attack.");
                    FXMLLoader fxml = Singleton.getInstance().fxmlLoader("views/board_assets/conflicts.fxml");
                    Conflicts cont = fxml.getController();
                    cont.setup(result.split("#")[1]);
                    Singleton.getInstance().active_controller = cont;
                    Stage stage = new Stage();
                    stage.setScene(new Scene(fxml.getRoot()));
                    stage.show();
                    cont.closingTimer(this);
                    return;
                }
                waitingStage.close();
                playersCards(result);

                state = PlayerState.CARD_PLACED;
            }
            case CARD_PLACED -> {
                for (OpponentView opponent : opponents) {
                    opponent.update_opponent();
                }
                GameRemote remote = (GameRemote) Singleton.getInstance().client.rmiRequest(Settings.GAME_HANDLING);
                String result = remote.game_status(board.match_id);
                if (result == null) return;
                Singleton.getInstance().age.setAge(result);
                update_age();
                result = remote.playerUpdateState(board.match_id, Singleton.getInstance().active_player.getName());
                String[] up = result.split("#");
                Singleton.getInstance().active_player.getGameState().setCoins(Integer.parseInt(up[0]));
                Singleton.getInstance().active_player.getGameState().setShields(Integer.parseInt(up[1]));
                updateStages(up[2]);
                if (up.length == 4)
                    setCityCards(up[3]);
                playerUpdates();
                state = PlayerState.CARD_PLACING;
            }
        }
    }

    public void playersCards(String result) {
        System.out.println(result);
        if (result.isEmpty()) {
            System.out.println("EMPTY CARD");
            return;
        }
        String[] cards = result.split(",");
        for (String card : cards) {
            String[] c = card.split("#");
            Card newCard = new Card(c[0]);
            newCard.setPlaceable(c[1]);
            Singleton.getInstance().active_player.getCards().add(newCard);
        }
        updating_cards();
    }
}
