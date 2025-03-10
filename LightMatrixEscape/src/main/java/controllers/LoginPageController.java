package controllers;

/**
 *
 * @author Ntela & Ishrak
 */
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.beans.binding.Bindings;
import javafx.scene.control.Alert;

public class LoginPageController {

    @FXML
    TextField usernameTextField;

    @FXML
    Button continuebtn;

    Stage stage;
    String permitedChar = "1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIGKLMNOPQRSTUVWXYZ_.";
    private Player newPlayer;
    String username;

    public void initialize() {
        continuebtn.setOnAction((event) -> {
            username = usernameTextField.getText();
            // Create a new player with levelProgress 1 and score 0
            newPlayer = new Player(username, 1, 0);
            System.out.println("Username: " + newPlayer.getUsername());
            newPlayer.saveProgress();

            try {
                if (verification()) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/how_to_play_layout.fxml"));
                    HowToPlayController fxmlController = new HowToPlayController();
                    loader.setController(fxmlController);
                    fxmlController.giveStage(stage);

                    Pane root = null;
                    try {
                        root = loader.load();
                    } catch (IOException ex) {
                        Logger.getLogger(LoginPageController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    continuebtn.styleProperty().bind(Bindings.when(continuebtn.hoverProperty())
                            .then("-fx-background-color: #FFC125;")
                            .otherwise("-fx-background-color: #FFDAB9;"));
                }
            } catch (IOException ex) {
                Logger.getLogger(LoginPageController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    public Stage giveStage(Stage stage) {
        return this.stage = stage;
    }

    //In this method, the username received from the textfield will be filtered
    public boolean verification() throws IOException {
        for (int i = 0; i < username.length(); i++) {
            String letter = Character.toString(username.charAt(i));
            if (!permitedChar.contains(letter)) {
                showError("invalid");
                return false;
            }
        }

        if (username.isEmpty()) {
            showError("empty");
            return false;
        }

        if (username.length() > 12) {
            showError("size");
            return false;
        }

        // If none of the conditions are met, return true
        return true;
    }

    private void showError(String problem) {
        Alert error = new Alert(Alert.AlertType.ERROR);
        String header = "";
        String content = "";
        switch (problem) {
            case "empty" -> { //both textfields are empty
                header = "Nothing has been inputed";
                content = "The game won't start if you don't put a username";
            }

            case "invalid" -> { //the username has at least one unauthorized character
                header = "The username contains unauthorized characters";
                content = "You can put in your username lower and upper case English letters, periods and underscores.";
            }

            case "size" -> { //the username exceeds 12 characters
                header = "The username is too long";
                content = "You have a limit of 12 characters for your username";
            }

            case "long_and_invalid" -> {
                header = "The username is too long and contains unauthorized characters";
                content = "Please enter a valid username";
            }
        }

        //this.username = null;
        error.setHeaderText(header);
        error.setContentText(content);
        error.showAndWait();
    }
}
