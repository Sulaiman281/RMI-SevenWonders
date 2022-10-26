package com.wonders.client.controller.trash;

import com.wonders.client.controller.Controller;
import com.wonders.client.pattern.Singleton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class LobbiesList implements Controller {

    ArrayList<LobbyBox> lobbyBoxes;

    @FXML
    private VBox root;

    @FXML
    void initialize() {
        lobbyBoxes = new ArrayList<>();
    }

    void add_box(String name, String players) {
        for (LobbyBox lobbyBox : lobbyBoxes) {
            if (lobbyBox.getLobbyName().equals(name)) {
                lobbyBox.setData(name, players);
                return;
            }
        }
        FXMLLoader fxml = Singleton.getInstance().fxmlLoader("views/lobby_box.fxml");
        LobbyBox box = fxml.getController();
        box.setData(name, players);
        root.getChildren().add(fxml.getRoot());
        lobbyBoxes.add(box);
    }


    @FXML
    private void dashboard(ActionEvent actionEvent) {
        Singleton.getInstance().changeView("Dashboard","views/dashboard.fxml");
    }

    @Override
    public void server_updates() throws MalformedURLException, NotBoundException, RemoteException {
//        MenuRemote remote = (MenuRemote) Singleton.getInstance().client.rmiRequest(Settings.MENU);
//        String result = remote.getRooms();
//        String[] lobbies = result.split(",");
//        if (lobbyBoxes.size() > lobbies.length) {
//            lobbyBoxes.removeIf(box -> {
//                if (!result.contains(box.getLobbyName())){
//                    root.getChildren().remove(box.getRoot());
//                    return true;
//                }
//                return false;
//            });
//        }
//
//        for (String s : lobbies) {
//            String[] data = s.split(":");
//            if (data.length < 2) return;
//            add_box(data[0], data[1]);
//        }
    }

}
