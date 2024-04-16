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
    LevelPageController levelPageController;

    Stage stage;
    //String permitedChar = "1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIGKLMNOPQRSTUVWXYZ_.";
    char[] permittedChars = {1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
        'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '_', '.',
        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    String login;
    String signUp;
    private Player newPlayer;

    public void initialize() {
        login = logInTextField.getText();

        signUp = signUpTextField.getText();
        continuebtn.getStyleClass().add("style.css");

        continuebtn.setOnAction((event) -> {
            // Assuming login text field contains username
            String username = signUpTextField.getText();
            // Create a new player with levelProgress 1 and score 0
            newPlayer = new Player(username, 1, 0);
            newPlayer.saveProgress();
//            levelPageController.setPlayer(newPlayer);
            try {
                if (verification(login, signUp)) {
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

                }
            } catch (IOException ex) {
                Logger.getLogger(LoginPageController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    public Stage giveStage(Stage stage) {
        return this.stage = stage;
    }

    public boolean verification(String log, String sign) throws IOException {
        String REGEX = "^[a-zA-Z0-9_]*$";
        Pattern pattern = Pattern.compile(REGEX);
        Matcher matcher = pattern.matcher(log);
        boolean result = matcher.matches();

        //Makes sure that one of the two string is empty
       // if (((log.isEmpty() || log == null) ^ (sign.isEmpty() || sign == null))) {
            //!(log.isEmpty() && sign.isEmpty()) && (log.isEmpty() || sign.isEmpty())
            // use the one above if the code doesn't work
            if (!log.isEmpty()) {

                if (log.length() > 12) {
                    showError("size");
                    //return false;
                } else if (log.length() <= 12) {
//                    for (int i = 0; i < permittedChars.length; i++) {
//                        for (int j = 0; j < log.length(); j++) {
                    if (!result) {
                        //char letter = Character.toString(login.charAt(i));
                        //if (!permittedChars.) {
                        //calls error method that shows that a character is not accepted
                        showError("invalid");
                        //return false
//                            }
//                        }
                    }
                    //here put some code that reads the file and make sure that it exists
                    return searchUsername(log);
                }

            } else { //the signUp option
                if (sign.length() > 12) {
                    showError("size");
                    //return false;
//                } else {
//                    for (int i = 0; i < sign.length(); i++) {
//                        String letter = Character.toString(login.charAt(i));
//                        if (!permitedChar.contains(letter)) {
//                            //calls error method that shows that a character is not accepted
//                            showError("invalid");
//                            //return false;
//                        }
//                    }
                }
                //here put some code that reads the file and make sure that it isn't already used
               // return searchUsername(sign) != true;
            } 
   

//        } else if ((login.isEmpty() || log == null) && (signUp.isEmpty() || sign == null)) {
//            showError("empty");
//           // return false;
//        }
//        showError("bothfull");
//        return false;

        //if the username was typed in the signIn the method will look if it is in the username file
        //if it isn't there then it will show an error message
//        for (int i =0;i<name.length();i++){
//            String letter = Character.toString(name.charAt(i));
//            if(s.contains(letter)!= true){
//                
//            }
//        }
        return true;
    
    }

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
            case "empty" -> { //both textfields are empty
                header = "Nothing has been inputed";
                content = "The game won't start if you don't put a username";
            }

            case "bothfull" -> { //both textfields are used
                header = "Both textfield are used";
                content = "You can only enter your username in login or sign in";
            }

            case "used" -> { //the username has already been used (sign in)
                header = "This username is already taken";
                content = "Either log in or find another username";
            }

            case "new" -> { //the username has never been used (log in)
                header = "This username is not registered";
                content = "Either sign in with this username or login with an used username";
            }

            case "invalid" -> {//the username has at least one unauthorized character
                header = "The username contains unauthorized characters";
                content = "You can put in your username lower and upper case English letters, numbers, periods and underscores.";
            }

            case "size" -> {//the username exceeds 12 characters
                header = "The username is too long";
                content = "You have a limit of 12 characters for your username";
            }
        }

//        this.login = null;
//        this.signUp = null;
        error.setHeaderText(header);
        error.setContentText(content);
        error.showAndWait();
    }
}
