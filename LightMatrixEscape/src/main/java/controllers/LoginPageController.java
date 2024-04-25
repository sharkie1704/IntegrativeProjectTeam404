package controllers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.util.regex.*;

public class LoginPageController {

    @FXML
    Pane accountPane;

    @FXML
    TextField logInTextField, signUpTextField;

    @FXML
    Button continuebtn;
    LevelPageOneController levelPageController;

    Stage stage;
    String permitedChar = "1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIGKLMNOPQRSTUVWXYZ_.";
//    char[] permittedChars = {1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
//        'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '_', '.',
//        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    String logIn;
    String signUp;
    private Player newPlayer;

    public void initialize() {
        
        
        
        continuebtn.getStyleClass().add("style.css");

        continuebtn.setOnAction((event) -> {
            logIn = logInTextField.getText();
            signUp = signUpTextField.getText();
            // Assuming login text field contains username
            String username = signUpTextField.getText();
            // Create a new player with levelProgress 1 and score 0
            newPlayer = new Player(username, 1, 0);
            newPlayer.saveProgress();
//            levelPageController.setPlayer(newPlayer);
//            try {
//                if (verification()) {
                    FXMLLoader loader = new FXMLLoader(
//                            getClass().getResource("/fxml/game_page_layout.fxml")
                            getClass().getResource("/fxml/game_page_layout_level2.fxml")
                    );
//                    LevelPageOneController fxmlController = new LevelPageOneController();
                    LevelPageTwoController fxmlController = new LevelPageTwoController();
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

//                }
//            } catch (IOException ex) {
//                Logger.getLogger(LoginPageController.class.getName()).log(Level.SEVERE, null, ex);
//            }
        });
    }

     public Stage giveStage(Stage stage) {
        return this.stage = stage;
    }

    //In this method, the username received from the textfield will be filtered
    public boolean verification() throws IOException {

        //Makes sure that one of the two string is empty
        if (((logIn.isEmpty() || logIn == null) ^ (signUp.isEmpty() || signUp == null))) {
            //!(log.isEmpty() && sign.isEmpty()) && (log.isEmpty() || sign.isEmpty())
            // use the one above if the code doesn't work

            if (!signUp.isEmpty()) {
                if (logIn.length() > 12) {
                    showError("size");
                    return false;

                } else {
                    for (int i = 0; i < logIn.length(); i++) {
                        String letter = Character.toString(logIn.charAt(i));
                        if (!permitedChar.contains(letter)) {
                            //calls error method that shows that a character is not accepted
                            showError("invalid");
                            return false;
                        }
                    }
//                    
//                     //here put some code that reads the file and make sure that it exists
//                    if(!searchUsername(logIn)){
//                        showError("new");
//                        return false;
//                    }else{
//                        return true;
//                    }
                    
                }

            } else { //the signin option
                if (signUp.length() > 12) {
                    showError("size");
                    return false;
                } else {
                    for (int i = 0; i < signUp.length(); i++) {
                        String letter = Character.toString(signUp.charAt(i));
                        if (!permitedChar.contains(letter)) {
                            //calls error method that shows that a character is not accepted
                            showError("invalid");
                            return false;
                        }
                    }
//                    
//                    if(searchUsername(signUp)){
//                        //here put some code that reads the file and make sure that it isn't already used
//                        showError("used");
//                        return false;
//                    }else{
//                        return true;
//                    }
//                    
                }
                

            }

        } else if ((logIn.isEmpty() || logIn == null) && (signUp.isEmpty() || signUp == null)) {
            showError("empty");
            return false;
        }
        showError("bothfull");
        return false;

    }

    //MAJOR READING ISSUE
    public static boolean searchUsername(String name) throws IOException { //change the filname to the actual file
        ArrayList namesList = new ArrayList();
        String username = "";
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(new File("/data/progress.txt")))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                username = line.substring(11, line.indexOf("'"));
                namesList.add(username);
            }

            for (int i = 0; i < namesList.size(); i++) {
                if (namesList.contains(name)) {
                    return true;
                }
            }
        }

        return false;
    }

    
    private void showError(String problem) {
        Alert error = new Alert(Alert.AlertType.ERROR);
        String header = "";
        String content = "";
        switch (problem) {
            case "empty"->{ //both textfields are empty
                header = "Nothing has been inputed";
                content = "The game won't start if you don't put a username";
            }
            
            case "bothfull"->{ //both textfields are used
                header = "Both textfield are used";
                content = "You can only enter your username in login or sign in";
            }

            case "used"->{ //the username has already been used (sign in)
                header = "This username is already taken";
                content = "Either log in or find another username";
            }
                
            case "new"->{ //the username has never been used (log in)
                header = "This username is not registered";
                content = "Either sign in with this username or login with an used username";
            }
            
            case "invalid"->{ //the username has at least one unauthorized character
                header = "The username contains unauthorized characters";
                content = "You can put in your username lower and upper case English letters, periods and underscores.";  
            }
            
            case "size"->{ //the username exceeds 12 characters
                header = "The username is too long";
                content = "You have a limit of 12 characters for your username";
            }

        }

        this.logIn = null;
        this.signUp = null;

        error.setHeaderText(header);
        error.setContentText(content);
        error.showAndWait();
    }

}
