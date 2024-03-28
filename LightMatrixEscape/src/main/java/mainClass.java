
import controllers.MainAppController;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

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

//      Pane root = new Pane();
        Scene scene = new Scene(root);
        primaryStage.setTitle("Refraction Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}