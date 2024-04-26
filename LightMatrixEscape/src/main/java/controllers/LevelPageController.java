package controllers;

/**
 *
 * @author Hongyan Li & Ishrak Mellah
 */
import java.io.*;
import java.net.URL;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.media.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.text.*;

public class LevelPageController {

    @FXML
    Pane gamePane, actionPane;

    @FXML
    Text levelText;

    @FXML
    Label scoreLabel, usernameLabel;

    @FXML
    Button btnNextGame;

    @FXML
    Slider volumeSlider;

    @FXML
    MediaView mediaView;

    @FXML
    Image imageLevelUp;

    @FXML
    ImageView detectorImageView;

    @FXML
    Line lineWallOne, lineWallTwo, lineWallThree, lineWallFour;

    @FXML
    Line lineBorderUp, lineBorderLeft, lineBorderRight, lineBorderBottom;

    @FXML
    Ellipse lightBulb;

    @FXML
    LoginPageController loginPageController;

    @FXML
    Rectangle mirror1, mirror1Clone;
    
    double xCoord;
    double yCoord;
    
    Player player;

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

    //Refraction and reflection
    Reflection reflectionMethod = new Reflection();
    Refraction refractMethod = new Refraction();
    Detector detectorMethod = new Detector();
    Wall wallMethod = new Wall();

    public void initialize() throws FileNotFoundException, IOException {

//        double originalXCoord = mirror1.getTranslateX();
//        double originalYCoord = mirror1.getTranslateY();
       mirror1Clone.setVisible(false);
//        double adjustX = mirror1.getLayoutX()-mirror1Clone.getLayoutX();
//        double adjustY = mirror1.getLayoutY()-mirror1Clone.getLayoutY();
//        // - Takes coordinates of the object in the scene when pressed
//        mirror1.setOnMousePressed(event->{
//            xCoord = event.getSceneX()-mirror1.getTranslateX();
//            yCoord = event.getSceneY()-mirror1.getTranslateY();
//            
//        });
//        // - Changes coordinates of the object in the scene when dragged
//        mirror1.setOnMouseDragged(event->{
//            double moveX = event.getSceneX()- xCoord;
//            mirror1.setTranslateX(moveX);
//                System.out.println(xCoord);
//                System.out.println(mirror1.getBoundsInParent().getMinX());
//            if(mirror1.getBoundsInParent().getMinX()< 0){
//                //rect.setTranslateX(-(midPane.getLayoutBounds().getWidth())/2 + rect.getWidth()/2);
//                mirror1.setTranslateX(-(actionPane.getLayoutBounds().getWidth())/2+ mirror1.getWidth()/2-adjustX);
//       
//            }
//            
//            if(mirror1.getBoundsInParent().getMaxX()> actionPane.getLayoutBounds().getWidth()){
//                //rect.setTranslateX((midPane.getLayoutBounds().getWidth())/2 - rect.getWidth()/2); 
//                mirror1.setTranslateX((actionPane.getLayoutBounds().getWidth())/2 - mirror1.getWidth()/2-adjustX);
//
//            }
//                
//            
//            double moveY = event.getSceneY()- yCoord;
//            mirror1.setTranslateY(moveY);
//            
//            if(mirror1.getBoundsInParent().getMinY()< 0){
//                mirror1.setTranslateY(-(actionPane.getLayoutBounds().getHeight())/2 + mirror1.getHeight()/2-adjustY);
//            }
//            
//            if(mirror1.getBoundsInParent().getMaxY()> actionPane.getLayoutBounds().getHeight()){
//                mirror1.setTranslateY((actionPane.getLayoutBounds().getHeight())/2 - mirror1.getHeight()/2-adjustY);
//            }
//            
//        });
//        
//                
//        // - Puts back object in original coordnates if in contact with another object
//        mirror1.setOnMouseReleased(event->{
//        if(mirror1.getBoundsInParent().intersects(lineWallOne.getBoundsInParent()) ||
//                mirror1.getBoundsInParent().intersects(lineWallTwo.getBoundsInParent())||
//                mirror1.getBoundsInParent().intersects(lineWallThree.getBoundsInParent())||
//                mirror1.getBoundsInParent().intersects(lineWallFour.getBoundsInParent())
//                ){
//                    mirror1.setTranslateX(originalXCoord);
//                    mirror1.setTranslateY(originalYCoord);
//                }
//            
//        });
//        
        
        //btnNextGame.setVisible(false);
        //imageLevelUp = new Image(new FileInputStream(getClass().
               // getResource("/images/imageLevelUp.png").getFile()));

        //Audio Clips
        URL urlsoundClick = this.getClass().getClassLoader().getResource("sounds/soundClick.mp3");
        AudioClip clickAC = new AudioClip(urlsoundClick.toExternalForm());
        URL urlsoundLevelUp = this.getClass().getClassLoader().getResource("sounds/soundLevelUp.mp3");
        AudioClip levelUpAC = new AudioClip(urlsoundLevelUp.toExternalForm());

        //Method to play game music and adjust volume with volume slider
        File gameMusic = new File("gameMusic.mp3");
        Media media = new Media(gameMusic.toURI().toString());
        MediaPlayer mp = new MediaPlayer(media);
        mediaView = new MediaView(mp);
    
        mp.setAutoPlay(true);
        volumeSlider = new Slider(0, 100, 50);
        volumeSlider.setValue(50);
//        volumeSlider.valueProperty().addListener(new ChangeListener<Number>() {
//           @Override
//           public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
//               mp.volumeProperty().bind();}
//        }
        mp.volumeProperty().bind(volumeSlider.valueProperty().divide(100));

        //Method to set the usernameText to the username of the player and update score
        //player.importProgress();
        //usernameLabel = new Label(player.getUsername());
        //System.out.println(player.getUsername());
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

        if (level == 1) {
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
                                levelUpAC.play();
//                                btnNextGame.setVisible(true);
                                circleForImageLevelUp.setFill(new ImagePattern(imageLevelUp));
                                System.out.println("yes " + reflectRay.getBoundsInParent().getWidth());
                            }

                        }
                    }
                }

                System.out.println("event.getX() " + event.getX() + " ; event.getY() " + event.getY());

            });

        }

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

//Next Game button for Going to next level
//idea: make the button only visible after finishing last level
//        btnNextGame.setOnAction((event) -> {
//
//            level = 2;
//            System.out.println("level is " + level);
//            btnNextGame.disableProperty();
//            //clear all the things from last game.
//            gamePane.getChildren().removeAll(mirrorOne, reflectRay, lightRay);
//
//        });
//
//        // create ray in level 2
//        Line lightRayLevelTwo = new Line(50, 25 + 100 * Math.sqrt(3),
//                50, 25 + 100 * Math.sqrt(3));
//        lightRayLevelTwo.setStroke(Color.TRANSPARENT);
//        gamePane.getChildren().add(lightRayLevelTwo);
//
//        // create left line of Prism
//        Line leftLineOfPrism = new Line(200, 25,
//                100, 25 + 100 * Math.sqrt(3)); //  /  
//        leftLineOfPrism.setStroke(Color.TRANSPARENT);
//
//        gamePane.getChildren().add(leftLineOfPrism);
//
//        //create right line of Prism
//        Line rightLineOfPrism = new Line(200, 25,
//                300, 25 + 100 * Math.sqrt(3));  // \  
//        rightLineOfPrism.setStroke(Color.TRANSPARENT);
//        gamePane.getChildren().add(rightLineOfPrism);
//
//        //create bottom line of Prism
//        Line bottomLineOfPrism = new Line(100, 25 + 100 * Math.sqrt(3),
//                300, 25 + 100 * Math.sqrt(3));  // _  
//        bottomLineOfPrism.setStroke(Color.TRANSPARENT);
//        gamePane.getChildren().add(bottomLineOfPrism);
//
//        Line firstRefractRay = new Line(200, 50, 200, 350);
//        firstRefractRay.setStroke(Color.TRANSPARENT);
//        gamePane.getChildren().addAll(firstRefractRay);
//
//        Line secondRefractRay = new Line(200, 50, 200, 350);
//        secondRefractRay.setStroke(Color.TRANSPARENT);
//        gamePane.getChildren().addAll(secondRefractRay);
//
//        Line firstNormal = new Line(50, 200, 50, 200); //random place
//        firstNormal.setStroke(Color.TRANSPARENT);
//        gamePane.getChildren().add(firstNormal);
//
//        Line secondNormal = new Line(50, 200, 50, 200); //random place
//        secondNormal.setStroke(Color.TRANSPARENT);
//        gamePane.getChildren().add(secondNormal);
//
//        if (level == 2) {
//
//            gamePane.setOnMouseClicked(event -> {
//                System.out.println("Yes");//Try
//                lightRayLevelTwo.setStroke(Color.BLACK);
//                leftLineOfPrism.setStroke(Color.GRAY);
//                rightLineOfPrism.setStroke(Color.GRAY);
//                bottomLineOfPrism.setStroke(Color.GRAY);
//
//                //Click the mouse to change the incident light and Refracted light
//                double[] startingPointOfRay = {50, 200}; // The starting point of ray
//                double[] endingPointOfRay = {event.getX(), event.getY()}; // The end point of ray
//                double[] startingPointOfLeftLineOfPrism = {leftLineOfPrism.getStartX(),
//                    leftLineOfPrism.getStartY()}; // The starting point of /
//                double[] endingPointOfLeftLineOfPrism = {leftLineOfPrism.getEndX(),
//                    leftLineOfPrism.getEndY()}; // The end point of line /
//                double[] startingPointOfRightLineOfPrism = {rightLineOfPrism.getStartX(),
//                    rightLineOfPrism.getStartY()}; // The starting point of line \
//                double[] endingPointOfRightLineOfPrism = {rightLineOfPrism.getEndX(),
//                    rightLineOfPrism.getEndY()}; // The end point of line \
//
//                double[] reflectRayStartPoint = refractMethod.findIntersection(
//                        startingPointOfRay,
//                        endingPointOfRay,
//                        startingPointOfLeftLineOfPrism,
//                        endingPointOfLeftLineOfPrism);
//
//                double refractedAngleOne = refractMethod.FindrefractedAngle(
//                        startingPointOfRay, endingPointOfRay,
//                        startingPointOfLeftLineOfPrism,
//                        endingPointOfLeftLineOfPrism, "Glass");
//
//                double[] firstNoramlStartPoint = refractMethod.calculateNormalStartPoint(
//                        startingPointOfLeftLineOfPrism,
//                        endingPointOfLeftLineOfPrism,
//                        reflectRayStartPoint);
//
//                double[] firstNoramlEndPoint = refractMethod.calculateNormalEndPoint(
//                        startingPointOfLeftLineOfPrism,
//                        endingPointOfLeftLineOfPrism,
//                        reflectRayStartPoint);
//
//                double[] firstRefractedRayEndPoint = refractMethod.calculateRefractRayEndPoint(
//                        reflectRayStartPoint, refractedAngleOne,
//                        200, firstNoramlStartPoint,
//                        firstNoramlEndPoint);
//
//                lightRayLevelTwo.setEndX(reflectRayStartPoint[0]);
//                lightRayLevelTwo.setEndY(reflectRayStartPoint[1]);
//
//                //make sure the ray only reflect when touching the prism
//                if (reflectRayStartPoint[1] < leftLineOfPrism.getEndY()
//                        && reflectRayStartPoint[1] > leftLineOfPrism.getStartY()) {
//                    //First Reflection
//                    refractMethod.reflectThings(startingPointOfRay, endingPointOfRay,
//                            startingPointOfLeftLineOfPrism, endingPointOfLeftLineOfPrism, lightRayLevelTwo,
//                            firstNormal, firstRefractRay, "Glass");
//                    System.out.println("Yes");
//
//                    if (firstRefractedRayEndPoint[1] < rightLineOfPrism.getEndY()
//                            && firstRefractedRayEndPoint[1] > rightLineOfPrism.getStartY()) {
//                        //Second Reflection
//                        refractMethod.reflectThings(reflectRayStartPoint,
//                                firstRefractedRayEndPoint, startingPointOfRightLineOfPrism,
//                                endingPointOfRightLineOfPrism, lightRayLevelTwo, secondNormal,
//                                secondRefractRay, "Air");
//
//                        // Find intersection between ReflectRay and PrismTwoC-D
//                        double[] intersectionSecond = refractMethod.findIntersection(
//                                reflectRayStartPoint,
//                                firstRefractedRayEndPoint,
//                                startingPointOfRightLineOfPrism, endingPointOfRightLineOfPrism);
//
//                        firstRefractRay.setEndX(intersectionSecond[0]);
//                        firstRefractRay.setEndY(intersectionSecond[1]);
//                    } else { //if the lightTwo in wrong place
//                        secondRefractRay.setStroke(Color.TRANSPARENT);
//                        secondNormal.setStroke(Color.TRANSPARENT);
//                    }
//                } //if the lightOne in wrong place
//                else {
//                    lightRayLevelTwo.setEndX(event.getX());
//                    lightRayLevelTwo.setEndY(event.getY());
//                    firstRefractRay.setStroke(Color.TRANSPARENT);
//                    secondRefractRay.setStroke(Color.TRANSPARENT);
//                    firstNormal.setStroke(Color.TRANSPARENT);
//                    secondNormal.setStroke(Color.TRANSPARENT);
////                System.out.println("No");
//                }
//
//            });
//        }
