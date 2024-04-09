package controllers;

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

public class LoginPageController {

    @FXML
    Pane accountPane;

    @FXML
    TextField logInTextField, signUpTextField;

    @FXML
    Button continuebtn;
    LevelPageController levelPageController;
    
    Stage stage;
    private Player newPlayer;

    public void initialize() {
        
        continuebtn.getStyleClass().add("style.css");
        
        continuebtn.setOnAction((event) -> {

            // Assuming login text field contains username
            String username = signUpTextField.getText();
            // Create a new player with levelProgress 1 and score 0
            newPlayer = new Player(username, 1, 0);
            newPlayer.saveProgress();
//            levelPageController.setPlayer(newPlayer);

            // if (verification()){
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/fxml/game_page_layout.fxml")
            );
            LevelPageController fxmlController = new LevelPageController();
            loader.setController(fxmlController);
            
            Pane root = null;
            try {
                root = loader.load();
            } catch (IOException ex) {
                Logger.getLogger(LoginPageController.class.getName()).log(Level.SEVERE, null, ex);
            }
            //root.getStylesheets().add(getClass().getResource("style.css").toString());
            Scene scene = new Scene(root);
            stage.setScene(scene);

            //}
        });
    }

    public Stage giveStage(Stage stage) {
        return this.stage = stage;
    }

    //In this method, the username received from the textfield will be filtered
    public void verification(String name) {
        //if the username was typed in the signIn the method will look if it is in the username file
        //if it isn't there then it will show an error message

        //if the username was typed in the logIn, the method will look if it is also in the username file
        //if it is in it, then it will show an error message
        //the username was typed in the two textfield then it will show an error message   
    }
}
