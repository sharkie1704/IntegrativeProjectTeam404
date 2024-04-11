package controllers;

/**
 *
 * @author Hongyan Li & sharkie
 */
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

public class LevelPageController {

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
    Image imageLevelUp;

    @FXML
    ImageView detectorImageView;

    @FXML
    Line lineWallOne, lineWallTwo, lineWallThree;

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
    double level = 1;

    public void initialize() throws FileNotFoundException {
        btnNextGame.setVisible(false);
        imageLevelUp = new Image(new FileInputStream(getClass().
                getResource("/images/imageLevelUp.png").getFile()));

        //Aduio Clips
        URL urlsoundClick = this.getClass().getClassLoader().getResource("sounds/soundClick.mp3");
        AudioClip clickAC = new AudioClip(urlsoundClick.toExternalForm());
        URL urlsoundLevelUp = this.getClass().getClassLoader().getResource("sounds/soundLevelUp.mp3");
        AudioClip levelUpAC = new AudioClip(urlsoundLevelUp.toExternalForm());

        volumeSlider = new Slider(0, 100, 30);
        volumeSlider.setValue(50);
        // mediaPlayer.volumeProperty().bind(volumeSlider.valueProperty().divide(100))

//        scoreText.setText("Score: " + player.getScore());
//        player.scoreProperty().addListener((obs, oldScore, newScore) -> {
//            scoreText.setText("Score: " + newScore.intValue());
        //Refraction and reflection
        Reflection reflectionMethod = new Reflection();
        Refraction refractMethod = new Refraction();
        Detector detectorMethod = new Detector();
        Wall wallMethod = new Wall();
//        Lines lineMethod = new Lines();
        // create ray
        Line lightRay = new Line(441, 463, 800, 400);
        lightRay.setStroke(Color.BLACK);
        gamePane.getChildren().add(lightRay);

        // create mirror one
        Line mirrorOne = new Line(845, 365, 845, 200);
        mirrorOne.setStroke(Color.BLUE);
        gamePane.getChildren().add(mirrorOne);

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

        if (level == 1) {
//         Click the mouse to change the incident light and reflected light
            gamePane.setOnMouseClicked(event -> {
                clickAC.play();

                // Update the end point of the light
                double[] startingPointOfRay = {lightRay.getStartX(), lightRay.getStartY()}; // The starting point of ray
                double[] endingPointOfRay = {event.getX(), event.getY()}; // The end point of ray
                double[] startingPointOfMirrorOne = {mirrorOne.getStartX(), mirrorOne.getStartY()}; // The starting point of wall
                double[] endingPointOfMirrorOne = {mirrorOne.getEndX(), mirrorOne.getEndY()}; // The end point of line segment CD (wall

                
                //determine if the line touch each wall
                double[] interesctPointWithWallOne = wallMethod.findIntersectionWithWall(
                        startingPointOfRay, endingPointOfRay,
                        lineWallOne);
                double[] interesctPointWithWallTwo = wallMethod.findIntersectionWithWall(
                        startingPointOfRay, endingPointOfRay, lineWallTwo);
                double[] interesctPointWithWallThree = wallMethod.findIntersectionWithWall(
                        startingPointOfRay, endingPointOfRay, lineWallThree);
                

//              
                boolean isTouchingTheWallOne = wallMethod.wallTouched(startingPointOfRay,
                        endingPointOfRay, lineWallOne);
                boolean isTouchingTheWallTwo = wallMethod.wallTouched(startingPointOfRay,
                        endingPointOfRay, lineWallTwo);
                boolean isTouchingTheWallThree = wallMethod.wallTouched(startingPointOfRay,
                        endingPointOfRay, lineWallThree);
//                System.out.println("isTouchingTheWallOne "+isTouchingTheWallOne);
                if (isTouchingTheWallOne || isTouchingTheWallTwo || isTouchingTheWallThree) {
                    
                    if (isTouchingTheWallOne) {
                        lightRay.setEndX(interesctPointWithWallOne[0]);
                        lightRay.setEndY(interesctPointWithWallOne[1]);
                        System.out.println("interesctPointWithWallOne "
                                +interesctPointWithWallOne[0]+"+"+interesctPointWithWallOne[1]);
                    }
                    if (isTouchingTheWallTwo) {
                        lightRay.setEndX(interesctPointWithWallTwo[0]);
                        lightRay.setEndY(interesctPointWithWallTwo[1]);
                    }
                    if (isTouchingTheWallThree) {
                        lightRay.setEndX(interesctPointWithWallTwo[0]);
                        lightRay.setEndY(interesctPointWithWallTwo[1]);
                    } else {
                        System.out.println("Error happened");

                    }


                    //if the ray does not intersect with the mirror
                } else {
                    if (endingPointOfRay[0] <= 441) {
                        lightRay.setEndX(event.getX());
                        lightRay.setEndY(event.getY());
                        reflectRay.setStroke(Color.TRANSPARENT);

                    } else {
                        //Intersection of lightray and the mirror One
                        double[] intersection = reflectionMethod.findIntersection(
                                startingPointOfRay, endingPointOfRay,
                                startingPointOfMirrorOne, endingPointOfMirrorOne);

                        if (intersection[1] > 365 || intersection[1] < 200) {
                            lightRay.setEndX(event.getX());
                            lightRay.setEndY(event.getY());
                            reflectRay.setStroke(Color.TRANSPARENT);
                        } else {
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
                            reflectRay.setStroke(Color.BLACK);

                            boolean isInteresct = detectorMethod.isIntersecting(intersection[0],
                                    intersection[1], reflectedRayEndX, reflectedRayEndY, 359, 32, 20);

                            if (isInteresct) {
                                levelUpAC.play();
                                btnNextGame.setVisible(true);
                                circleForImageLevelUp.setFill(new ImagePattern(imageLevelUp));
                                System.out.println("yes " + reflectRay.getBoundsInParent().getWidth());
                            }

                        }
                    }
                }

                System.out.println("event.getX() " + event.getX() + " ; event.getY() " + event.getY());

            });

        }

        //Next Game button for Going to next level
        //idea: make the button only visible after finishing last level
        btnNextGame.setOnAction((event) -> {

            level = 2;
            System.out.println("level is " + level);
            btnNextGame.disableProperty();
            //clear all the things from last game.
            gamePane.getChildren().removeAll(mirrorOne, reflectRay, lightRay);

        });

        // create ray in level 2
        Line lightRayLevelTwo = new Line(50, 25 + 100 * Math.sqrt(3),
                50, 25 + 100 * Math.sqrt(3));
        lightRayLevelTwo.setStroke(Color.TRANSPARENT);
        gamePane.getChildren().add(lightRayLevelTwo);

        // create left line of Prism
        Line leftLineOfPrism = new Line(200, 25,
                100, 25 + 100 * Math.sqrt(3)); //  /  
        leftLineOfPrism.setStroke(Color.TRANSPARENT);

        gamePane.getChildren().add(leftLineOfPrism);

        //create right line of Prism
        Line rightLineOfPrism = new Line(200, 25,
                300, 25 + 100 * Math.sqrt(3));  // \  
        rightLineOfPrism.setStroke(Color.TRANSPARENT);
        gamePane.getChildren().add(rightLineOfPrism);

        //create bottom line of Prism
        Line bottomLineOfPrism = new Line(100, 25 + 100 * Math.sqrt(3),
                300, 25 + 100 * Math.sqrt(3));  // _  
        bottomLineOfPrism.setStroke(Color.TRANSPARENT);
        gamePane.getChildren().add(bottomLineOfPrism);

        Line firstRefractRay = new Line(200, 50, 200, 350);
        firstRefractRay.setStroke(Color.TRANSPARENT);
        gamePane.getChildren().addAll(firstRefractRay);

        Line secondRefractRay = new Line(200, 50, 200, 350);
        secondRefractRay.setStroke(Color.TRANSPARENT);
        gamePane.getChildren().addAll(secondRefractRay);

        Line firstNormal = new Line(50, 200, 50, 200); //random place
        firstNormal.setStroke(Color.TRANSPARENT);
        gamePane.getChildren().add(firstNormal);

        Line secondNormal = new Line(50, 200, 50, 200); //random place
        secondNormal.setStroke(Color.TRANSPARENT);
        gamePane.getChildren().add(secondNormal);

        if (level == 2) {

            gamePane.setOnMouseClicked(event -> {
                System.out.println("Yes");//Try
                lightRayLevelTwo.setStroke(Color.BLACK);
                leftLineOfPrism.setStroke(Color.GRAY);
                rightLineOfPrism.setStroke(Color.GRAY);
                bottomLineOfPrism.setStroke(Color.GRAY);

                //Click the mouse to change the incident light and Refracted light
                double[] startingPointOfRay = {50, 200}; // The starting point of ray
                double[] endingPointOfRay = {event.getX(), event.getY()}; // The end point of ray
                double[] startingPointOfLeftLineOfPrism = {leftLineOfPrism.getStartX(),
                    leftLineOfPrism.getStartY()}; // The starting point of /
                double[] endingPointOfLeftLineOfPrism = {leftLineOfPrism.getEndX(),
                    leftLineOfPrism.getEndY()}; // The end point of line /
                double[] startingPointOfRightLineOfPrism = {rightLineOfPrism.getStartX(),
                    rightLineOfPrism.getStartY()}; // The starting point of line \
                double[] endingPointOfRightLineOfPrism = {rightLineOfPrism.getEndX(),
                    rightLineOfPrism.getEndY()}; // The end point of line \

                double[] reflectRayStartPoint = refractMethod.findIntersection(
                        startingPointOfRay,
                        endingPointOfRay,
                        startingPointOfLeftLineOfPrism,
                        endingPointOfLeftLineOfPrism);

                double refractedAngleOne = refractMethod.FindrefractedAngle(
                        startingPointOfRay, endingPointOfRay,
                        startingPointOfLeftLineOfPrism,
                        endingPointOfLeftLineOfPrism, "Glass");

                double[] firstNoramlStartPoint = refractMethod.calculateNormalStartPoint(
                        startingPointOfLeftLineOfPrism,
                        endingPointOfLeftLineOfPrism,
                        reflectRayStartPoint);

                double[] firstNoramlEndPoint = refractMethod.calculateNormalEndPoint(
                        startingPointOfLeftLineOfPrism,
                        endingPointOfLeftLineOfPrism,
                        reflectRayStartPoint);

                double[] firstRefractedRayEndPoint = refractMethod.calculateRefractRayEndPoint(
                        reflectRayStartPoint, refractedAngleOne,
                        200, firstNoramlStartPoint,
                        firstNoramlEndPoint);

                lightRayLevelTwo.setEndX(reflectRayStartPoint[0]);
                lightRayLevelTwo.setEndY(reflectRayStartPoint[1]);

                //make sure the ray only reflect when touching the prism
                if (reflectRayStartPoint[1] < leftLineOfPrism.getEndY()
                        && reflectRayStartPoint[1] > leftLineOfPrism.getStartY()) {
                    //First Reflection
                    refractMethod.reflectThings(startingPointOfRay, endingPointOfRay,
                            startingPointOfLeftLineOfPrism, endingPointOfLeftLineOfPrism, lightRayLevelTwo,
                            firstNormal, firstRefractRay, "Glass");
                    System.out.println("Yes");

                    if (firstRefractedRayEndPoint[1] < rightLineOfPrism.getEndY()
                            && firstRefractedRayEndPoint[1] > rightLineOfPrism.getStartY()) {
                        //Second Reflection
                        refractMethod.reflectThings(reflectRayStartPoint,
                                firstRefractedRayEndPoint, startingPointOfRightLineOfPrism,
                                endingPointOfRightLineOfPrism, lightRayLevelTwo, secondNormal,
                                secondRefractRay, "Air");

                        // Find intersection between ReflectRay and PrismTwoC-D
                        double[] intersectionSecond = refractMethod.findIntersection(
                                reflectRayStartPoint,
                                firstRefractedRayEndPoint,
                                startingPointOfRightLineOfPrism, endingPointOfRightLineOfPrism);

                        firstRefractRay.setEndX(intersectionSecond[0]);
                        firstRefractRay.setEndY(intersectionSecond[1]);
                    } else { //if the lightTwo in wrong place
                        secondRefractRay.setStroke(Color.TRANSPARENT);
                        secondNormal.setStroke(Color.TRANSPARENT);
                    }
                } //if the lightOne in wrong place
                else {
                    lightRayLevelTwo.setEndX(event.getX());
                    lightRayLevelTwo.setEndY(event.getY());
                    firstRefractRay.setStroke(Color.TRANSPARENT);
                    secondRefractRay.setStroke(Color.TRANSPARENT);
                    firstNormal.setStroke(Color.TRANSPARENT);
                    secondNormal.setStroke(Color.TRANSPARENT);
//                System.out.println("No");
                }

            });
        }

    }
}


