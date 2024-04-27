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
    Pane gamePaneFOne;

    @FXML
    Text scoreTextFOne, usernameTextFOne, levelTextFOne;

    @FXML
    Button btnNextGame;

    @FXML
    Slider volumeSliderFOne;

    @FXML
    Image imageLevelUp, imageDoorOpened;

    @FXML
    ImageView detectorImageViewFOne, doorImageViewFOne;

    @FXML
    Line lineWallOne, lineWallTwo, lineWallThree, lineWallFour;

    @FXML
    Line lineBorderUpFOne, lineBorderLeftFOne, lineBorderRightFOne, lineBorderBottomFOne;

    @FXML
    Ellipse lightBulbFOne;

    Player newPlayer;
    Stage stage;
//
//    LoginPageController loginPageController;
//
//    // Method to set the player
//    public void setPlayer(Player player) {
//        loginPageController.initialize();
//        this.newPlayer = player;
//        if (player != null) {
//            // updateScoreText(); // Update score text when player is set
//        }
//    }
//
//    // Method to update the score text
//       private void updateScoreText() {
//        scoreText.setText("Score: " + newPlayer.getScore());
//    }

    //Reflection, dectector, and wall methods
    Reflection reflectionMethod = new Reflection();
    Detector detectorMethod = new Detector();
    Wall wallMethod = new Wall();

    public void initialize() throws FileNotFoundException {
        //btnNextGame.setVisible(false);
//        imageLevelUp = new Image(new FileInputStream(getClass().
//                getResource("/images/imageLevelUp.png").getFile()));
//        imageDoorOpened = new Image(new FileInputStream(getClass().
//                getResource("/images/imageDoorOpened.png").getFile()));

        //Audio clips
        URL urlsoundClick = this.getClass().getClassLoader().getResource("sounds/soundClick.mp3");
        AudioClip clickAC = new AudioClip(urlsoundClick.toExternalForm());
        URL urlsoundLevelUp = this.getClass().getClassLoader().getResource("sounds/soundLevelUp.mp3");
        AudioClip levelUpAC = new AudioClip(urlsoundLevelUp.toExternalForm());

        URL URLMusic = this.getClass().getClassLoader().getResource("sounds/gameMusic.mp3");
        AudioClip MusicGame = new AudioClip(URLMusic.toExternalForm());
//        Media media = new Media(MusicGame);
//        MediaPlayer mediaPlayer = new MediaPlayer(media);
        volumeSliderFOne = new Slider(0, 100, 30);
        volumeSliderFOne.setValue(50);
        clickAC.volumeProperty().bind(volumeSliderFOne.valueProperty().divide(50));
        MusicGame.volumeProperty().bind(volumeSliderFOne.valueProperty());
        MusicGame.play();
//        System.out.println(newPlayer.getUsername());
//        mediaPlayer.volumeProperty().bind(volumeSlider.valueProperty().divide(100));

//        scoreText.setText("Score: " + player.getScore());
//        player.scoreProperty().addListener((obs, oldScore, newScore) -> {
//            scoreText.setText("Score: " + newScore.intValue());
//        Lines lineMethod = new Lines();
        // create ray
        Line lightRay = new Line(lightBulbFOne.getLayoutX(), lightBulbFOne.getLayoutY(), 100, 400);
        lightRay.setStroke(Color.YELLOW);
        lightRay.setStrokeWidth(5);
        gamePaneFOne.getChildren().add(lightRay);

        // create mirror one
        Line lineMirrorOne = new Line(845, 380, 845, 255);
        lineMirrorOne.setStroke(Color.BLUE);
        gamePaneFOne.getChildren().add(lineMirrorOne);

        Line reflectRay = new Line(0, 0, 0, 0);
        //whatever position and length of reflectRay, since this will change later.
        reflectRay.setStroke(Color.TRANSPARENT);
        reflectRay.setStrokeWidth(5);
        gamePaneFOne.getChildren().addAll(reflectRay);

        Circle circleForImageLevelUp = new Circle();
        circleForImageLevelUp.setCenterX(450.0f);
        circleForImageLevelUp.setCenterY(200.0f);
        circleForImageLevelUp.setRadius(100.0f);
        circleForImageLevelUp.setFill(Color.TRANSPARENT);
//        circleForImageLevelUp.setFill(new ImagePattern(imageLevelUp));
        gamePaneFOne.getChildren().add(circleForImageLevelUp);

        btnNextGame.setVisible(false);

//         Click the mouse to change the incident light and reflected light
        gamePaneFOne.setOnMouseClicked(event -> {
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
//            System.out.println(" wall :1 " + isTouchingTheWallOne + " 2 " + isTouchingTheWallTwo + " 3 " + isTouchingTheWallThree);

            if (isTouchingTheWallOne || isTouchingTheWallTwo
                    || isTouchingTheWallThree || isTouchingTheWallFour) {
                reflectRay.setStroke(Color.TRANSPARENT);

                if (isTouchingTheWallOne
                        && eventEndingPointOfRay[0] < lightBulbFOne.getLayoutX()
                        && eventEndingPointOfRay[1] < lightBulbFOne.getLayoutY()) {
                    lightRay.setEndX(interesctPointWithWallOne[0]);
                    lightRay.setEndY(interesctPointWithWallOne[1]);
//                        System.out.println("interesctPointWithWallOne "
//                                +interesctPointWithWallOne[0]+"+"+interesctPointWithWallOne[1]);
//                        System.out.println("lightRay end x"+ lightRay.getEndX());
                } else if (isTouchingTheWallTwo
                        && eventEndingPointOfRay[0] <= Math.max(lineWallTwo.getStartX(), lineWallTwo.getEndX())
                        && eventEndingPointOfRay[0] >= Math.min(lineWallTwo.getStartX(), lineWallTwo.getEndX())
                        && eventEndingPointOfRay[1] < lightBulbFOne.getLayoutY()) {
                    lightRay.setEndX(interesctPointWithWallTwo[0]);
                    lightRay.setEndY(interesctPointWithWallTwo[1]);

//                        System.out.println("interesctPointWithWallTwo "
//                                +interesctPointWithWallTwo[0]+"+"+interesctPointWithWallTwo[1]);
                } else if (isTouchingTheWallThree
                        && eventEndingPointOfRay[0] > lightBulbFOne.getLayoutX()
                        && eventEndingPointOfRay[1] < lightBulbFOne.getLayoutY()) {
                    lightRay.setEndX(interesctPointWithWallThree[0]);
                    lightRay.setEndY(interesctPointWithWallThree[1]);

                } else if (isTouchingTheWallFour
                        && eventEndingPointOfRay[0] > lightBulbFOne.getLayoutX()
                        && eventEndingPointOfRay[1] < lightBulbFOne.getLayoutY()) {
                    lightRay.setEndX(interesctPointWithWallFour[0]);
                    lightRay.setEndY(interesctPointWithWallFour[1]);

                }

                //if the ray does not intersect with the mirror
            } else {
                if (endingPointOfRay[0] <= lightBulbFOne.getLayoutX() / 2) {

//                    touchingTheBorder(startingPointOfRay, eventEndingPointOfRay, lightRay, reflectRay);
                    reflectRay.setStroke(Color.TRANSPARENT);

                } else {
                    //Intersection of lightray and the mirror One
                    double[] intersection = reflectionMethod.findIntersection(
                            startingPointOfRay, endingPointOfRay,
                            startingPointOfMirrorOne, endingPointOfMirrorOne);

                    if (intersection[1] > 390 || intersection[1] < 225) {

//                        touchingTheBorder(startingPointOfRay, eventEndingPointOfRay, lightRay, reflectRay);
                        reflectRay.setStroke(Color.TRANSPARENT);
                    } else if (eventEndingPointOfRay[0] > lightBulbFOne.getLayoutX()) {
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
                                reflectedAngle, 1300, startingPointOfMirrorOne,
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
                                reflectedRayEndY, detectorImageViewFOne.getLayoutX() + 25,
                                detectorImageViewFOne.getLayoutY() + 25, 20);

                        if (isInteresct) {

                            //rotate the door
                            RotateTransition rotateDoor = new RotateTransition(Duration.seconds(2), doorImageViewFOne);
                            rotateDoor.setFromAngle(0);
                            rotateDoor.setToAngle(360);
                            rotateDoor.play();

//                            doorImageViewFOne.setImage(imageDoorOpened);
                            levelUpAC.play(); //audio
                            btnNextGame.setVisible(true);

//                                circleForImageLevelUp.setFill(new ImagePattern(imageLevelUp));
//                                System.out.println("yes " + reflectRay.getBoundsInParent().getWidth());
                        }
                    }
                }
            }
//            System.out.println("event.getX() " + event.getX() + " ; event.getY() " + event.getY());
        });

        btnNextGame.setOnMouseClicked((event) -> {
            System.out.println("Going to next level");
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/fxml/game_page_layout_level2.fxml")
            );
            LevelPageTwoController fxmlController = new LevelPageTwoController();
            loader.setController(fxmlController);
//            fxmlController.giveStage(stage);

            Pane root = null;
            fxmlController.giveStage(stage);
            try {
                root = loader.load();
            } catch (IOException ex) {

                Logger.getLogger(LevelPageOneController.class.getName()).log(Level.SEVERE, null, ex);
            }
            //root.getStylesheets().add(getClass().getResource("style.css").toString());
            Scene scene = new Scene(root);
            stage.setScene(scene);
//            stage.show();
        });
    }

    public Stage giveStage(Stage stage) {
        return this.stage = stage;
    }

    public void touchingTheBorder(double[] startingPointOfRay, double[] eventEndingPointOfRay,
            Line lightRay, Line reflectRay) {
        //Make the ray touches the border
        double[] interesctPointWithBorderUp = wallMethod.findIntersectionWithWall(startingPointOfRay, eventEndingPointOfRay,
                lineBorderUpFOne);
        double[] interesctPointWithBorderLeft = wallMethod.findIntersectionWithWall(startingPointOfRay, eventEndingPointOfRay,
                lineBorderLeftFOne);
        double[] interesctPointWithBorderRight = wallMethod.findIntersectionWithWall(startingPointOfRay, eventEndingPointOfRay,
                lineBorderRightFOne);
        double[] interesctPointWithBorderBottom = wallMethod.findIntersectionWithWall(startingPointOfRay, eventEndingPointOfRay,
                lineBorderBottomFOne);

        boolean isTouchingTheBorderUp = wallMethod.wallTouched(startingPointOfRay,
                eventEndingPointOfRay, lineBorderUpFOne);
        boolean isTouchingTheBorderLeft = wallMethod.wallTouched(startingPointOfRay,
                eventEndingPointOfRay, lineBorderLeftFOne);
        boolean isTouchingTheBorderRight = wallMethod.wallTouched(startingPointOfRay,
                eventEndingPointOfRay, lineBorderRightFOne);
        boolean isTouchingTheBorderBottom = wallMethod.wallTouched(startingPointOfRay,
                eventEndingPointOfRay, lineBorderBottomFOne);

        if (isTouchingTheBorderUp || isTouchingTheBorderLeft
                || isTouchingTheBorderRight || isTouchingTheBorderBottom) {
//            System.out.println("Touching border");
            reflectRay.setStroke(Color.TRANSPARENT);
            if (isTouchingTheBorderUp && eventEndingPointOfRay[1] <= lightBulbFOne.getLayoutY()) {
//                System.out.println("Touching borderUp");
                lightRay.setEndX(interesctPointWithBorderUp[0]);
                lightRay.setEndY(interesctPointWithBorderUp[1]);
//                        System.out.println("interesctPointWithWallOne "
//                                +interesctPointWithWallOne[0]+"+"+interesctPointWithWallOne[1]);
//                        System.out.println("lightRay end x"+ lightRay.getEndX());
            } else if (isTouchingTheBorderLeft && eventEndingPointOfRay[0] < lightBulbFOne.getLayoutX()) {
//                System.out.println("Touching borderLeft");
                lightRay.setEndX(interesctPointWithBorderLeft[0]);
                lightRay.setEndY(interesctPointWithBorderLeft[1]);

//                        System.out.println("interesctPointWithWallTwo "
//                                +interesctPointWithWallTwo[0]+"+"+interesctPointWithWallTwo[1]);
            } else if (isTouchingTheBorderRight && eventEndingPointOfRay[0] >= lightBulbFOne.getLayoutX()) {
                lightRay.setEndX(interesctPointWithBorderRight[0]);
                lightRay.setEndY(interesctPointWithBorderRight[1]);

            } else if (isTouchingTheBorderBottom && eventEndingPointOfRay[1] > lightBulbFOne.getLayoutY()) {
                lightRay.setEndX(interesctPointWithBorderBottom[0]);
                lightRay.setEndY(interesctPointWithBorderBottom[1]);
            }
        }
    }
}
