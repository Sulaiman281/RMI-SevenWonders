package com.wonders.client.controller.trash;

import com.wonders.client.controller.Controller;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class OpponentBox implements Controller {

    @FXML
    private HBox root;

    @FXML
    private Label player_name;

    @FXML
    private CheckBox is_ready;


    public void add_opponent(String name, boolean ready){
        player_name.setText(name);
        is_ready.setSelected(ready);
    }

    public void update(boolean ready){
        is_ready.setSelected(ready);
    }

    public String getName(){
        return player_name.getText();
    }

    public HBox getRoot() {
        return root;
    }

    public boolean isReady(){
        return is_ready.isSelected();
    }

    @Override
    public void server_updates() {

    }
}
