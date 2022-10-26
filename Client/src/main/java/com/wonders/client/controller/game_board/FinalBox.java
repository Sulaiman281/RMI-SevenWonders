package com.wonders.client.controller.game_board;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class FinalBox {

    @FXML
    private HBox root;

    @FXML
    private Label player_name;

    @FXML
    private Label vp_points;

    public void set_player(String player){
        String[] data = player.split("#");
        player_name.setText(data[0]);
        vp_points.setText(data[1]);
    }

    public int points(){
        return Integer.parseInt(vp_points.getText());
    }

    public HBox getRoot() {
        return root;
    }
}
