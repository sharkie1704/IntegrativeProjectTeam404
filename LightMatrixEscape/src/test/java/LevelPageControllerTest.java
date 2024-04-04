
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author 2280592
 */
public class LevelPageControllerTest {
 
    @FXML
    ImageView backgroundImageView, volumeImageView;

    @FXML
    Pane gamePane, actionPane;

    @FXML
    Text scoreText, usernameText, levelText;
    
    PlayerTest player;

    public void initialize() {
        
    }
    
    //user interacting with mirror while the mirror (MOUSE_DRAGGED)
    // mirrors can be moved by the previous event, but won't be able to settle on walls
    //
}