package properties;

import controllers.MainAppController;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;

public class mainClass extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/fxml/main_page_layout.fxml")
        );
        MainAppController fxmlController = new MainAppController();
        loader.setController(fxmlController);
        fxmlController.giveStage(primaryStage);

        Pane root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setTitle("The Light Matrix Escape");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
