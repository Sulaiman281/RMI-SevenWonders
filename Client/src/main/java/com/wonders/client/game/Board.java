package com.wonders.client.game;

import com.wonders.GameRemote;
import com.wonders.Settings;
import com.wonders.client.controller.Cities_Selection;
import com.wonders.client.controller.Controller;
import com.wonders.client.controller.Player_View;
import com.wonders.client.controller.board_assets.OpponentView;
import com.wonders.client.pattern.Singleton;
import com.wonders.model.Age;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Screen;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Board extends BorderPane implements Controller {

    public enum Match_States{
        CITY_SELECTION,
        AGE_I_CARD,
        AGE_I_PHASE_1,
        AGE_I_PHASE_2,
        AGE_I_PHASE_3,
        AGE_I_PHASE_4,
        AGE_I_PHASE_5,
        AGE_I_PHASE_6,
        AGE_I_PHASE_7, // Military Conflicts
        AGE_II_CARD,
        AGE_II_PHASE_1,
        AGE_II_PHASE_2,
        AGE_II_PHASE_3,
        AGE_II_PHASE_4,
        AGE_II_PHASE_5,
        AGE_II_PHASE_6,
        AGE_II_PHASE_7, // Military Conflicts
        AGE_III_CARD,
        AGE_III_PHASE_1,
        AGE_III_PHASE_2,
        AGE_III_PHASE_3,
        AGE_III_PHASE_4,
        AGE_III_PHASE_5,
        AGE_III_PHASE_6,
        AGE_III_PHASE_7, // Military Conflicts
        GAME_RESULT, // end of the game showing game results
    }

    public Match_States game_state;

    public String match_id;

    public Board(String match) {
        match_id = match;
//        Rectangle2D bounds = Screen.getPrimary().getBounds();
        this.setPrefSize(1200, 800);
        new Scene(this);

        this.setStyle("-fx-background-color: linear-gradient(#ccba7a, #5c471a);");

        heading_msg("Cities Selection", "Choose Your City before your opponent picks it.");

        FXMLLoader fxml = Singleton.getInstance().fxmlLoader("views/cities_selection.fxml");
        Cities_Selection controller = fxml.getController();
        controller.setBoard(this);
        Singleton.getInstance().active_controller = controller;
        this.setCenter(fxml.getRoot());
        game_state = Match_States.CITY_SELECTION;
    }

    public void heading_msg(String heading, String msg) {
        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);
        box.getChildren().add(
                getLabel(heading, Font.font("BELL MT", 32), 800, 60)
        );
        box.getChildren().add(
                getLabel(msg, Font.font("BELL MT", 18), 700, 30)
        );
        box.setPrefSize(500, 200);
        this.setTop(box);
    }

    void setup_board_players(String opponents){
        this.setTop(null);
        FXMLLoader loader = Singleton.getInstance().fxmlLoader("views/player_view.fxml");
        Player_View controller = loader.getController();
        this.setCenter(loader.getRoot());
        controller.setup(this);
        HBox box = new HBox();
        box.setAlignment(Pos.CENTER);
        box.setSpacing(10);
        box.setPadding(new Insets(10));
        box.setStyle("-fx-background-color: rgb(0,0,0,.7)");
        for (String s : opponents.split(",")) {
            FXMLLoader fxml = Singleton.getInstance().fxmlLoader("views/board_assets/opponentView.fxml");
            OpponentView c = fxml.getController();
            c.setBoard(this);
            c.setOpponent(s);
            box.getChildren().add(fxml.getRoot());
            controller.opponents.add(c);
        }
        setTop(box);
        Singleton.getInstance().active_controller = controller;
    }

    Label getLabel(String str, Font font, double width, double height) {
        Label label = new Label(str);
        label.setFont(font);
        label.setAlignment(Pos.CENTER);
        label.setTextFill(Color.BLACK);
        label.setPrefSize(width, height);
        return label;
    }

    @Override
    public void server_updates() throws MalformedURLException, NotBoundException, RemoteException {
        switch (game_state){
            case AGE_I_CARD ->{
                GameRemote remote = (GameRemote) Singleton.getInstance().client.rmiRequest(Settings.GAME_HANDLING);
                String result = remote.game_status(match_id);
                if(result == null) return;
                Singleton.getInstance().age = new Age(result);
                result = remote.playerSetup(match_id, Singleton.getInstance().active_player.getName());
                if(result == null) return;
                String[] setup = result.split("#");
                Singleton.getInstance().active_player.getGameState().setCity_name(setup[0]);
                Singleton.getInstance().active_player.getGameState().setStages(setup[1]);
                Singleton.getInstance().active_player.getGameState().setDefault_resource(setup[2]);
                // getting opponents names.
                result = remote.player_Opponents(match_id, Singleton.getInstance().active_player.getName());
                if(result == null) return;
                if(result.split(",").length != 3) return;
                game_state = Match_States.AGE_I_PHASE_1;
                setup_board_players(result);
            }
        }
    }
}
