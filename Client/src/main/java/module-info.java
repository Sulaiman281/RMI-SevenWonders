module com.wonders.seven_wonders {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.rmi;

    exports com.wonders.client.controller.board_assets;
    opens com.wonders.client.controller.board_assets to javafx.fxml;
    exports com.wonders.client.controller.game_board;
    opens com.wonders.client.controller.game_board to javafx.fxml;
    exports com.wonders.client.controller;
    opens com.wonders.client.controller to javafx.fxml;
    exports com.wonders.client;
    opens com.wonders.client to javafx.fxml;
    exports com.wonders;
    opens com.wonders to javafx.fxml;
    exports com.wonders.client.controller.trash;
    opens com.wonders.client.controller.trash to javafx.fxml;
}