/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
 * @author Ntela
 */
public class MainAppController {
    @FXML
    ImageView titleImageView,startImageView;
    
    Stage stage;
    //don't forget to make it so that when you click on the image you'll open a new scene
    public void initialize(){
        //Image title = new Image(getClass().getResourceAsStream("insert the image address"));
        //Image start = new Image(getClass().getResourceAsStream("insert the image address"));
        //titleImageView.setImage(title);
        //startImageView.setImage(start);  
        
        startImageView.addEventHandler(MouseEvent.MOUSE_CLICKED, event->{
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
        });
    }
    
    
    public Stage giveStage(Stage stage){
        return this.stage = stage;
    }

}
