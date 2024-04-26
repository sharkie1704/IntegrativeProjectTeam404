package controllers;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainAppController {

    @FXML
    ImageView startImageView;

    @FXML
    Label myLabel;

    Stage stage;

    //don't forget to make it so that when you click on the image you'll open a new scene
    public void initialize() {

        startImageView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/fxml/account_page_layout.fxml")
            );
            LoginPageController fxmlController = new LoginPageController();
            loader.setController(fxmlController);

            fxmlController.giveStage(stage);
            Pane root = null;
            try {
                root = loader.load();
            } catch (IOException ex) {
                Logger.getLogger(MainAppController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            Scene scene = new Scene(root);
            stage.setScene(scene);
            myLabel.styleProperty().bind(Bindings.when(myLabel.hoverProperty())
                    .then("-fx-background-color: #FFC125;")
                    .otherwise("-fx-background-color: #FFDAB9;"));
        });
    }

    public Stage giveStage(Stage stage) {
        return this.stage = stage;
    }
}
