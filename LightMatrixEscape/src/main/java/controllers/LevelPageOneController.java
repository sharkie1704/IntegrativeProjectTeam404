package controllers;

/**
 *
 * @author Hongyan Li & sharkie
 */
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LevelPageOneController {

    @FXML
    Pane gamePane, actionPane;

    @FXML
    Text scoreText, usernameText, levelText;

    @FXML
    Button btnNextGame;

    @FXML
    Slider volumeSlider;
    Player player;

    @FXML
    Image imageLevelUp, imageDoorOpened;

    @FXML
    ImageView detectorImageView, doorImageView;

    @FXML
    Line lineWallOne, lineWallTwo, lineWallThree, lineWallFour;

    @FXML
    Line lineBorderUp, lineBorderLeft, lineBorderRight, lineBorderBottom;

    @FXML
    Ellipse lightBulb;
    Stage stage= new Stage();
//    Stage stage;

//    @FXML
//    LoginPageController loginPageController;
//    private Player newPlayer;
    // Method to set the player
//    public void setPlayer(Player player) {
//       // loginPageController.initialize();
//        this.newPlayer = player;
//        if (player != null) {
//            updateScoreText(); // Update score text when player is set
//        }
//    }
//
//    // Method to update the score text
//       private void updateScoreText() {
//        scoreText.setText("Score: " + newPlayer.getScore());
//    }
    
    

    //Refraction and reflection
    Reflection reflectionMethod = new Reflection();
    
    Detector detectorMethod = new Detector();
    Wall wallMethod = new Wall();

    
    public void initialize() throws FileNotFoundException {
        
        //btnNextGame.setVisible(false);
        imageLevelUp = new Image(new FileInputStream(getClass().
                getResource("/images/imageLevelUp.png").getFile()));
        imageDoorOpened = new Image(new FileInputStream(getClass().
                getResource("/images/imageDoorOpened.png").getFile()));

        //Aduio Clips
        URL urlsoundClick = this.getClass().getClassLoader().getResource("sounds/soundClick.mp3");
        AudioClip clickAC = new AudioClip(urlsoundClick.toExternalForm());
        URL urlsoundLevelUp = this.getClass().getClassLoader().getResource("sounds/soundLevelUp.mp3");
        AudioClip levelUpAC = new AudioClip(urlsoundLevelUp.toExternalForm());

        URL URLMusic = this.getClass().getClassLoader().getResource("sounds/gameMusic.mp3");
        AudioClip MusicGame = new AudioClip(URLMusic.toExternalForm());
//        Media media = new Media(MusicGame);
//        MediaPlayer mediaPlayer = new MediaPlayer(media);
        volumeSlider = new Slider(0, 100, 30);
        volumeSlider.setValue(50);
        clickAC.volumeProperty().bind(volumeSlider.valueProperty().divide(50));
        MusicGame.volumeProperty().bind(volumeSlider.valueProperty());
        MusicGame.play();
//        mediaPlayer.volumeProperty().bind(volumeSlider.valueProperty().divide(100));

//        scoreText.setText("Score: " + player.getScore());
//        player.scoreProperty().addListener((obs, oldScore, newScore) -> {
//            scoreText.setText("Score: " + newScore.intValue());
//        Lines lineMethod = new Lines();
        // create ray
        Line lightRay = new Line(lightBulb.getLayoutX(), lightBulb.getLayoutY(), 800, 400);
        lightRay.setStroke(Color.YELLOW);
        gamePane.getChildren().add(lightRay);

        // create mirror one
        Line lineMirrorOne = new Line(845, 380, 845, 210);
        lineMirrorOne.setStroke(Color.BLUE);
        gamePane.getChildren().add(lineMirrorOne);

        Line reflectRay = new Line(0, 0, 0, 0);
        //whatever position and length of reflectRay, since this will change later.
        reflectRay.setStroke(Color.TRANSPARENT);
        gamePane.getChildren().addAll(reflectRay);

        Circle circleForImageLevelUp = new Circle();
        circleForImageLevelUp.setCenterX(450.0f);
        circleForImageLevelUp.setCenterY(200.0f);
        circleForImageLevelUp.setRadius(100.0f);
        circleForImageLevelUp.setFill(Color.TRANSPARENT);
//        circleForImageLevelUp.setFill(new ImagePattern(imageLevelUp));
        gamePane.getChildren().add(circleForImageLevelUp);

        btnNextGame.setVisible(false);

//         Click the mouse to change the incident light and reflected light
        gamePane.setOnMouseClicked(event -> {
            clickAC.play();

            // Update the end point of the light
            double[] startingPointOfRay = {lightRay.getStartX(), lightRay.getStartY()}; // The starting point of ray
            double[] endingPointOfRay = {event.getX(), event.getY()}; // The end point of ray
            double[] eventEndingPointOfRay = {event.getX(), event.getY()}; // The event point of ray
            double[] startingPointOfMirrorOne = {lineMirrorOne.getStartX(), lineMirrorOne.getStartY()}; // The starting point of wall
            double[] endingPointOfMirrorOne = {lineMirrorOne.getEndX(), lineMirrorOne.getEndY()}; // The end point of line segment CD (wall

            touchingTheBorder(startingPointOfRay, eventEndingPointOfRay, lightRay, reflectRay);

            //determine if the line touch each wall
            double[] interesctPointWithWallOne = wallMethod.findIntersectionWithWall(
                    startingPointOfRay, endingPointOfRay,
                    lineWallOne);
            double[] interesctPointWithWallTwo = wallMethod.findIntersectionWithWall(
                    startingPointOfRay, endingPointOfRay, lineWallTwo);
            double[] interesctPointWithWallThree = wallMethod.findIntersectionWithWall(
                    startingPointOfRay, endingPointOfRay, lineWallThree);
            double[] interesctPointWithWallFour = wallMethod.findIntersectionWithWall(
                    startingPointOfRay, endingPointOfRay, lineWallFour);

//              
            boolean isTouchingTheWallOne = wallMethod.wallTouched(startingPointOfRay,
                    endingPointOfRay, lineWallOne);
            boolean isTouchingTheWallTwo = wallMethod.wallTouched(startingPointOfRay,
                    endingPointOfRay, lineWallTwo);
            boolean isTouchingTheWallThree = wallMethod.wallTouched(startingPointOfRay,
                    endingPointOfRay, lineWallThree);
            boolean isTouchingTheWallFour = wallMethod.wallTouched(startingPointOfRay,
                    endingPointOfRay, lineWallFour);
//                System.out.println("isTouchingTheWallOne "+isTouchingTheWallOne);
            System.out.println(" wall :1 " + isTouchingTheWallOne + " 2 " + isTouchingTheWallTwo + " 3 " + isTouchingTheWallThree);

            if (isTouchingTheWallOne || isTouchingTheWallTwo
                    || isTouchingTheWallThree || isTouchingTheWallFour) {
                reflectRay.setStroke(Color.TRANSPARENT);

                if (isTouchingTheWallOne
                        && eventEndingPointOfRay[0] < lightBulb.getLayoutX()
                        && eventEndingPointOfRay[1] < lightBulb.getLayoutY()) {
                    lightRay.setEndX(interesctPointWithWallOne[0]);
                    lightRay.setEndY(interesctPointWithWallOne[1]);
//                        System.out.println("interesctPointWithWallOne "
//                                +interesctPointWithWallOne[0]+"+"+interesctPointWithWallOne[1]);
//                        System.out.println("lightRay end x"+ lightRay.getEndX());
                } else if (isTouchingTheWallTwo
                        && eventEndingPointOfRay[0] <= Math.max(lineWallTwo.getStartX(), lineWallTwo.getEndX())
                        && eventEndingPointOfRay[0] >= Math.min(lineWallTwo.getStartX(), lineWallTwo.getEndX())
                        && eventEndingPointOfRay[1] < lightBulb.getLayoutY()) {
                    lightRay.setEndX(interesctPointWithWallTwo[0]);
                    lightRay.setEndY(interesctPointWithWallTwo[1] + 20);

//                        System.out.println("interesctPointWithWallTwo "
//                                +interesctPointWithWallTwo[0]+"+"+interesctPointWithWallTwo[1]);
                } else if (isTouchingTheWallThree
                        && eventEndingPointOfRay[0] > lightBulb.getLayoutX()
                        && eventEndingPointOfRay[1] < lightBulb.getLayoutY()) {
                    lightRay.setEndX(interesctPointWithWallThree[0]);
                    lightRay.setEndY(interesctPointWithWallThree[1] + 20);

                } else if (isTouchingTheWallFour
                        && eventEndingPointOfRay[0] > lightBulb.getLayoutX()
                        && eventEndingPointOfRay[1] < lightBulb.getLayoutY()) {
                    lightRay.setEndX(interesctPointWithWallFour[0]);
                    lightRay.setEndY(interesctPointWithWallFour[1]);

                }

                //if the ray does not intersect with the mirror
            } else {
                if (endingPointOfRay[0] <= lightBulb.getLayoutX() / 2) {

                    touchingTheBorder(startingPointOfRay, eventEndingPointOfRay, lightRay, reflectRay);
                    reflectRay.setStroke(Color.TRANSPARENT);

                } else {
                    //Intersection of lightray and the mirror One
                    double[] intersection = reflectionMethod.findIntersection(
                            startingPointOfRay, endingPointOfRay,
                            startingPointOfMirrorOne, endingPointOfMirrorOne);

                    if (intersection[1] > 365 || intersection[1] < 200) {

                        touchingTheBorder(startingPointOfRay, eventEndingPointOfRay, lightRay, reflectRay);
                        reflectRay.setStroke(Color.TRANSPARENT);
                    } else if (eventEndingPointOfRay[0] > lightBulb.getLayoutX()) {
                        lightRay.setEndX(intersection[0]);
                        lightRay.setEndY(intersection[1]);

                        // Calculate the incident angle of light
                        double incidentAngle = reflectionMethod.findIncidentAngle(
                                startingPointOfRay, endingPointOfRay,
                                startingPointOfMirrorOne, endingPointOfMirrorOne);

                        // Calculate the reflection angle of light
                        double reflectedAngle = incidentAngle;

                        // Calculate the end point coordinates of the reflected light, assuming the length is 200
                        double[] reflectedRayEndPoint = reflectionMethod.calculateReflectRayEndPoint(intersection,
                                reflectedAngle, 800, startingPointOfMirrorOne,
                                endingPointOfMirrorOne);
                        double reflectedRayEndX = reflectedRayEndPoint[0];
                        double reflectedRayEndY = reflectedRayEndPoint[1];

                        //Set ReflectRay proporties
                        reflectRay.setStartX(intersection[0]);
                        reflectRay.setStartY(intersection[1]);
                        reflectRay.setEndX(reflectedRayEndX);
                        reflectRay.setEndY(reflectedRayEndY);
                        reflectRay.setStroke(Color.YELLOW);

                        boolean isInteresct = detectorMethod.isIntersecting(intersection[0],
                                intersection[1], reflectedRayEndX,
                                reflectedRayEndY, detectorImageView.getLayoutX() + 25,
                                detectorImageView.getLayoutY() + 25, 20);

                        if (isInteresct) {

                            //rotate the door
                            RotateTransition rotateDoor = new RotateTransition(Duration.seconds(2), doorImageView);
                            rotateDoor.setFromAngle(0);
                            rotateDoor.setToAngle(360);
                            rotateDoor.play();

                            doorImageView.setImage(imageDoorOpened);

                            levelUpAC.play(); //audio
                            btnNextGame.setVisible(true);

//                                circleForImageLevelUp.setFill(new ImagePattern(imageLevelUp));
//                                System.out.println("yes " + reflectRay.getBoundsInParent().getWidth());
                        }

                    }
                }
            }

            System.out.println("event.getX() " + event.getX() + " ; event.getY() " + event.getY());

        });

        
        btnNextGame.setOnMouseClicked((event) -> {
            System.out.println("Going to next level");
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/fxml/game_page_layout_level2.fxml")
            );
            LevelPageTwoController fxmlController = new LevelPageTwoController();
            loader.setController(fxmlController);
//            fxmlController.giveStage(stage);
           

            Pane root =null;
            try {
                root = loader.load();
            } catch (IOException ex) {
                Logger.getLogger(LevelPageOneController.class.getName()).log(Level.SEVERE, null, ex);
                
            }
            //root.getStylesheets().add(getClass().getResource("style.css").toString());
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        });

    }

    public Stage giveStage(Stage stage) {
        return this.stage = stage;
    }

    public void touchingTheBorder(double[] startingPointOfRay, double[] eventEndingPointOfRay,
            Line lightRay, Line reflectRay) {
        //Make the ray touches the border
        double[] interesctPointWithBorderUp = wallMethod.findIntersectionWithWall(
                startingPointOfRay, eventEndingPointOfRay,
                lineBorderUp);
        double[] interesctPointWithBorderLeft = wallMethod.findIntersectionWithWall(
                startingPointOfRay, eventEndingPointOfRay,
                lineBorderLeft);
        double[] interesctPointWithBorderRight = wallMethod.findIntersectionWithWall(
                startingPointOfRay, eventEndingPointOfRay,
                lineBorderRight);
        double[] interesctPointWithBorderBottom = wallMethod.findIntersectionWithWall(
                startingPointOfRay, eventEndingPointOfRay,
                lineBorderBottom);

        boolean isTouchingTheBorderUp = wallMethod.wallTouched(startingPointOfRay,
                eventEndingPointOfRay, lineBorderUp);
        boolean isTouchingTheBorderLeft = wallMethod.wallTouched(startingPointOfRay,
                eventEndingPointOfRay, lineBorderLeft);
        boolean isTouchingTheBorderRight = wallMethod.wallTouched(startingPointOfRay,
                eventEndingPointOfRay, lineBorderRight);
        boolean isTouchingTheBorderBottom = wallMethod.wallTouched(startingPointOfRay,
                eventEndingPointOfRay, lineBorderBottom);

        if (isTouchingTheBorderUp || isTouchingTheBorderLeft
                || isTouchingTheBorderRight || isTouchingTheBorderBottom) {
            System.out.println("Touching border");
            reflectRay.setStroke(Color.TRANSPARENT);
            if (isTouchingTheBorderUp && eventEndingPointOfRay[1] <= lightBulb.getLayoutY()) {
                System.out.println("Touching borderUp");
                lightRay.setEndX(interesctPointWithBorderUp[0]);
                lightRay.setEndY(interesctPointWithBorderUp[1]);
//                        System.out.println("interesctPointWithWallOne "
//                                +interesctPointWithWallOne[0]+"+"+interesctPointWithWallOne[1]);
//                        System.out.println("lightRay end x"+ lightRay.getEndX());
            } else if (isTouchingTheBorderLeft && eventEndingPointOfRay[0] < lightBulb.getLayoutX()) {
                System.out.println("Touching borderLeft");
                lightRay.setEndX(interesctPointWithBorderLeft[0]);
                lightRay.setEndY(interesctPointWithBorderLeft[1]);

//                        System.out.println("interesctPointWithWallTwo "
//                                +interesctPointWithWallTwo[0]+"+"+interesctPointWithWallTwo[1]);
            } else if (isTouchingTheBorderRight && eventEndingPointOfRay[0] >= lightBulb.getLayoutX()) {
                lightRay.setEndX(interesctPointWithBorderRight[0]);
                lightRay.setEndY(interesctPointWithBorderRight[1]);

            } else if (isTouchingTheBorderBottom && eventEndingPointOfRay[1] > lightBulb.getLayoutY()) {
                lightRay.setEndX(interesctPointWithBorderBottom[0]);
                lightRay.setEndY(interesctPointWithBorderBottom[1]);

            }
        }

    }
}

