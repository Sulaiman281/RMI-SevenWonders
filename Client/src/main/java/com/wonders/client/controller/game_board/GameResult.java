package com.wonders.client.controller.game_board;

import com.wonders.client.pattern.Singleton;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class GameResult extends VBox {

    public GameResult(){
        setPrefSize(450,500);
        setAlignment(Pos.TOP_CENTER);
        new Scene(this);
        setSpacing(5);
    }

    public void setResult(String result){
        String[] players = result.split(",");
        ArrayList<FinalBox> boxes = new ArrayList<>();
        for (String player : players) {
            if(player.isEmpty()) return;
            FXMLLoader loader = Singleton.getInstance().fxmlLoader("views/game_board/finalBox.fxml");
            FinalBox cont = loader.getController();
            cont.set_player(player);
            boxes.add(cont);
        }

        // sorts
        FXCollections.observableArrayList(boxes).sort((a, b )-> Integer.compare(b.points(), a.points()));
        for (FinalBox box : boxes) {
            getChildren().add(box.getRoot());
        }
    }
}
