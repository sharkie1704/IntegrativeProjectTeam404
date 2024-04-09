
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
import org.junit.jupiter.api.Test;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author 2280592
 */
public class LoginPageControllerTest {

    @FXML
    Pane accountPane;

    @FXML
    TextField logInTextField, signInTextField;

    @FXML
    Button continuebtn;

    Stage stage;
    String permitedChar = "1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIGKLMNOPQRSTUVWXYZ_.";
    String login = null;
    String signin = null;
    PlayerTest player;

    @Test
    public void initialize() {

        continuebtn.setOnAction((event) -> {

            login = logInTextField.getText();

            signin = signInTextField.getText();

            //if (verification(login,signin)){
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/fxml/account_page_layout.fxml")
            );

            LevelPageControllerTest fxmlController = new LevelPageControllerTest();
            loader.setController(fxmlController);

            Pane root = null;
            try {
                root = loader.load();
            } catch (IOException ex) {
                Logger.getLogger(LoginPageControllerTest.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
            Scene scene = new Scene(root);
            stage.setScene(scene);
            //}
        });

    }

    public Stage giveStage(Stage stage) {
        return this.stage = stage;
    }

    //In this method, the username received from the textfield will be filtered
    @Test
    public boolean verification(String log, String sign) throws IOException {
        
        //Makes sure that one of the two string is empty
        if (( (log.isEmpty()||log == null) ^ (sign.isEmpty()|| sign == null) )) {
            //!(log.isEmpty() && sign.isEmpty()) && (log.isEmpty() || sign.isEmpty())
            // use the one above if the code doesn't work

            if (!log.isEmpty()) {
                if (login.length() > 12) {
                    showError("size");
                    return false;
                    
                } else {
                    for (int i = 0; i < log.length(); i++) {
                        String letter = Character.toString(log.charAt(i));
                        if (!permitedChar.contains(letter)) {
                            //calls error method that shows that a character is not accepted
                            showError("invalid");
                            return false;
                        }
                    }
                    
                     //here put some code that reads the file and make sure that it exists
                     return searchUsername(log);
                }

               
            } else { //the signin option
                if (login.length() > 12) {
                    showError("size");
                    return false;
                } else { 
                    for (int i = 0; i < sign.length(); i++) {
                    String letter = Character.toString(sign.charAt(i));
                        if (!permitedChar.contains(letter)) {
                        //calls error method that shows that a character is not accepted
                        showError("invalid");
                        return false;
                        }
                    }
                }
                    //here put some code that reads the file and make sure that it isn't already used
                    return searchUsername(sign) != true;
                    
            }

        } else if ((log.isEmpty()||log == null ) && (sign.isEmpty()||sign == null)) {
            showError("empty");
            return false;
        }
        showError("bothfull");
        return false;

        //if the username was typed in the signIn the method will look if it is in the username file
        //if it isn't there then it will show an error message
//        for (int i =0;i<name.length();i++){
//            String letter = Character.toString(name.charAt(i));
//            if(s.contains(letter)!= true){
//                
//            }
//        }
    }

    @Test
    public static boolean searchUsername(String name) throws IOException{ //change the filname to the actual file
        ArrayList namesList = new ArrayList();
        String username ="";
        try (BufferedReader bufferedReader = new BufferedReader
            (new FileReader( new File("/data/progress.txt")))){
            String line;
            while ((line = bufferedReader.readLine()) != null){
               username =  line.substring(11, line.indexOf("'"));
               namesList.add(username);
            }
            
            for (int i = 0; i< namesList.size();i++){
                if(namesList.contains(name)){
                return true;
                }
            } 
        }
        
        return false;
    }
    
    @Test
    private void showError(String problem) {
        Alert error = new Alert(Alert.AlertType.ERROR);
        String header = "";
        String content = "";
        switch (problem) {
            case "empty": //both textfields are empty
                header = "Nothing has been inputed";
                content = "The game won't start if you don't put a username";

            case "bothfull": //both textfields are used
                header = "Both textfield are used";
                content = "You can only enter your username in login or sign in";

            case "used": //the username has already been used (sign in)
                header = "This username is already taken";
                content = "Either log in or find another username";

            case "new": //the username has never been used (log in)
                header = "This username is not registered";
                content = "Either sign in with this username or login with an used username";

            case "invalid": //the username has at least one unauthorized character
                header = "The username contains unauthorized characters";
                content = "You can put in your username lower and upper case English letters, periods and underscores.";

            case "size": //the username exceeds 12 characters
                header = "The username is too long";
                content = "You have a limit of 12 characters for your username";

        }
        
        this.login = null;
        this.signin = null;
        
        error.setHeaderText(header);
        error.setContentText(content);
        error.showAndWait();
    }
}
