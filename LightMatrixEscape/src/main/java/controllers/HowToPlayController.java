
package controllers;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author Ishrak Mellah
 */
public class HowToPlayController {

    @FXML
    ImageView howToPlayImageView;

    Stage stage;

    public void initialize() {
        howToPlayImageView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/game_page_layout.fxml"));
            LevelPageOneController fxmlController = new LevelPageOneController();
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
        });
    }

    public Stage giveStage(Stage stage) {
        return this.stage = stage;
    }
}
