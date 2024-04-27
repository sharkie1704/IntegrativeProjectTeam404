/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author Hongyan Li
 */
public class LevelPageTwoController {

    @FXML
    Pane gamePane;

    @FXML
    Slider volumeSlider;

    @FXML
    Rectangle mirrorOne, mirrorTwo;

    @FXML
    Line lineMirrorOne, lineMirrorTwo;

    @FXML
    Line wallOne, wallTwo, wallThree, wallFour;

    @FXML
    Line lightRay, leftLineOfPrism, rightLineOfPrism, bottomLineOfPrism;

    @FXML
    Line lineBorderUp, lineBorderLeft, lineBorderRight, lineBorderBottom;

    @FXML
    Ellipse lightBulb;

    @FXML
    ImageView doorImageView, detectorImageView, imageViewWin;

    @FXML
    Image imageDoorOpened;

    Stage stage;

    Refraction refractMethod = new Refraction();
    Reflection reflectionMethod = new Reflection();
    Detector detectorMethod = new Detector();
    Wall wallMethod = new Wall();
    Lines lineMethod = new Lines();

    public void initialize() throws FileNotFoundException {

//        imageDoorOpened = new Image(new FileInputStream(getClass().
//                getResource("/images/imageDoorOpened.png").getFile()));

        //Audio Clips
        URL urlsoundClick = this.getClass().getClassLoader().getResource("sounds/soundClick.mp3");
        AudioClip clickAC = new AudioClip(urlsoundClick.toExternalForm());
        URL URLMusic = this.getClass().getClassLoader().getResource("sounds/gameMusic.mp3");
        AudioClip MusicGame = new AudioClip(URLMusic.toExternalForm());
        volumeSlider = new Slider(0, 100, 30);
        volumeSlider.setValue(50);
        clickAC.volumeProperty().bind(volumeSlider.valueProperty().divide(50));
        MusicGame.volumeProperty().bind(volumeSlider.valueProperty());
        MusicGame.play();

        //Create lines of ray
        
        lightRay.setStrokeWidth(5);
        
        Line firstRefractRay = new Line(0, 0, 0, 0);
        firstRefractRay.setStroke(Color.TRANSPARENT);
        firstRefractRay.setStrokeWidth(5);
        gamePane.getChildren().addAll(firstRefractRay);

        Line secondRefractRay = new Line(0, 0, 0, 0);
        secondRefractRay.setStroke(Color.TRANSPARENT);
        secondRefractRay.setStrokeWidth(5);
        gamePane.getChildren().addAll(secondRefractRay);

        Line firstNormal = new Line(0, 0, 0, 0); //random place
        firstNormal.setStroke(Color.TRANSPARENT);
        gamePane.getChildren().add(firstNormal);

        Line secondNormal = new Line(0, 0, 0, 0); //random place
        secondNormal.setStroke(Color.TRANSPARENT);
        gamePane.getChildren().add(secondNormal);

        Line firstReflectRay = new Line(0, 0, 0, 0);
        firstReflectRay.setStroke(Color.TRANSPARENT);
        firstReflectRay.setStrokeWidth(5);
        gamePane.getChildren().add(firstReflectRay);

        Line secReflectRay = new Line(0, 0, 0, 0);
        secReflectRay.setStroke(Color.TRANSPARENT);
        secReflectRay.setStrokeWidth(5);
        gamePane.getChildren().add(secReflectRay);
        
        Line internalReflectRay = new Line(0, 0, 0, 0);
        internalReflectRay.setStroke(Color.TRANSPARENT);
        internalReflectRay.setStrokeWidth(5);
        gamePane.getChildren().add(internalReflectRay);
        

        gamePane.setOnMouseClicked(event -> {

            clickAC.play();
//            System.out.println("Yes");//Try
            lightRay.setStroke(Color.YELLOW);
            leftLineOfPrism.setStroke(Color.GRAY);
            rightLineOfPrism.setStroke(Color.GRAY);
            bottomLineOfPrism.setStroke(Color.GRAY);
            firstRefractRay.setStroke(Color.TRANSPARENT);
            secondRefractRay.setStroke(Color.TRANSPARENT);
            firstReflectRay.setStroke(Color.TRANSPARENT);
            secReflectRay.setStroke(Color.TRANSPARENT);
            internalReflectRay.setStroke(Color.TRANSPARENT);

            //reset position of ray
            secReflectRay.setStartX(0);
            secReflectRay.setEndX(0);
            firstReflectRay.setStartX(0);
            firstReflectRay.setEndX(0);
            internalReflectRay.setStartX(0);
            internalReflectRay.setEndX(0);

            //Click the mouse to change the incident light and Refracted light
            double[] startingPointOfRay = {lightRay.getStartX(), lightRay.getStartY()}; // The starting point of ray
            double[] endingPointOfRay = {event.getX(), event.getY()}; // The end point of ray
            double[] eventEndingPointOfRay = {event.getX(), event.getY()}; // The event point of ray

            double[] startingPointOfLeftLineOfPrism = {leftLineOfPrism.getStartX(),
                leftLineOfPrism.getStartY()}; // The starting point of /
            double[] endingPointOfLeftLineOfPrism = {leftLineOfPrism.getEndX(),
                leftLineOfPrism.getEndY()}; // The end point of line /
            double[] startingPointOfRightLineOfPrism = {rightLineOfPrism.getStartX(),
                rightLineOfPrism.getStartY()}; // The starting point of line \
            double[] endingPointOfRightLineOfPrism = {rightLineOfPrism.getEndX(),
                rightLineOfPrism.getEndY()}; // The end point of line \
            double[] startingPointOfBottomLineOfPrism = lineMethod.getTheStartPointOfLine(bottomLineOfPrism);
            double[] endingPointOfBottomLineOfPrism = lineMethod.getTheEndPointOfLine(bottomLineOfPrism);

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
                    150, firstNoramlStartPoint,
                    firstNoramlEndPoint);

            touchingTheBorder(startingPointOfRay, endingPointOfRay, lightRay, firstRefractRay);

            
            //make sure the ray only reflect when touching the prism
            //determine if the line touch each wall
            double[] interesctPointWithWallOne = wallMethod.findIntersectionWithWall(
                    startingPointOfRay, endingPointOfRay,
                    wallOne);
            double[] interesctPointWithWallTwo = wallMethod.findIntersectionWithWall(
                    startingPointOfRay, endingPointOfRay, wallTwo);
            double[] interesctPointWithWallThree = wallMethod.findIntersectionWithWall(
                    startingPointOfRay, endingPointOfRay, wallThree);
            double[] interesctPointWithWallFour = wallMethod.findIntersectionWithWall(
                    startingPointOfRay, endingPointOfRay, wallFour);

//              
            boolean isTouchingTheWallOne = wallMethod.wallTouched(startingPointOfRay,
                    endingPointOfRay, wallOne);
            boolean isTouchingTheWallTwo = wallMethod.wallTouched(startingPointOfRay,
                    endingPointOfRay, wallTwo);
            boolean isTouchingTheWallThree = wallMethod.wallTouched(startingPointOfRay,
                    endingPointOfRay, wallThree);
            boolean isTouchingTheWallFour = wallMethod.wallTouched(startingPointOfRay,
                    endingPointOfRay, wallFour);
//                System.out.println("isTouchingTheWallOne "+isTouchingTheWallOne);
//            System.out.println(" wall :1 " + isTouchingTheWallOne + " 2 " + isTouchingTheWallTwo + " 3 " + isTouchingTheWallThree);

            if (isTouchingTheWallOne || isTouchingTheWallTwo
                    || isTouchingTheWallThree || isTouchingTheWallFour) {
                firstRefractRay.setStroke(Color.TRANSPARENT);
                secondRefractRay.setStroke(Color.TRANSPARENT);

                if (isTouchingTheWallOne
                        && eventEndingPointOfRay[1] < lightBulb.getLayoutY()) {
                    lightRay.setEndX(interesctPointWithWallOne[0]);
                    lightRay.setEndY(interesctPointWithWallOne[1]);
//                        System.out.println("interesctPointWithWallOne "
//                                +interesctPointWithWallOne[0]+"+"+interesctPointWithWallOne[1]);
//                        System.out.println("lightRay end x"+ lightRay.getEndX());
                } else if (isTouchingTheWallTwo
                        && eventEndingPointOfRay[1] < lightBulb.getLayoutY()) {
                    lightRay.setEndX(interesctPointWithWallTwo[0]);
                    lightRay.setEndY(interesctPointWithWallTwo[1]);

//                        System.out.println("interesctPointWithWallTwo "
//                                +interesctPointWithWallTwo[0]+"+"+interesctPointWithWallTwo[1]);
                } else if (isTouchingTheWallThree
                        && eventEndingPointOfRay[1] < lightBulb.getLayoutY()) {
                    lightRay.setEndX(interesctPointWithWallThree[0]);
                    lightRay.setEndY(interesctPointWithWallThree[1]);

                } else if (isTouchingTheWallFour
                        && eventEndingPointOfRay[1] > lightBulb.getLayoutY()) {
                    lightRay.setEndX(interesctPointWithWallFour[0]);
                    lightRay.setEndY(interesctPointWithWallFour[1]);

                }
            } 

                //First reflection happens
                if (reflectRayStartPoint[1] < leftLineOfPrism.getEndY()
                        && reflectRayStartPoint[1] > leftLineOfPrism.getStartY()) {
                    //First Reflection
                    refractMethod.refractThings(startingPointOfRay, endingPointOfRay,
                            startingPointOfLeftLineOfPrism, endingPointOfLeftLineOfPrism, lightRay,
                            firstNormal, firstRefractRay, "Glass");
                    lightRay.setEndX(reflectRayStartPoint[0]);
                    lightRay.setEndY(reflectRayStartPoint[1]);

                    // Find intersection between ReflectRay and Prism right line
                    double[] intersectionWithRightPrismLine = refractMethod.findIntersection(
                            reflectRayStartPoint,
                            firstRefractedRayEndPoint,
                            startingPointOfRightLineOfPrism, endingPointOfRightLineOfPrism);

                    // Find intersection between ReflectRay and Prism right line
                    double[] intersectionBottomPrism = refractMethod.findIntersection(
                            reflectRayStartPoint,
                            firstRefractedRayEndPoint,
                            startingPointOfBottomLineOfPrism, endingPointOfBottomLineOfPrism);

                    if ((intersectionBottomPrism[0] < bottomLineOfPrism.getEndX()
                            && intersectionBottomPrism[0] > bottomLineOfPrism.getStartX())
                            ||(intersectionWithRightPrismLine[1] < rightLineOfPrism.getEndY()
                            && intersectionWithRightPrismLine[1] > rightLineOfPrism.getStartY())) {
                        
                    
                    //Second reflection (touch bottom line or right line)
                    if (intersectionBottomPrism[0] < bottomLineOfPrism.getEndX()
                            && intersectionBottomPrism[0] > bottomLineOfPrism.getStartX()) {
                        //Reflection internal(
//                        System.out.println("YYes");
                        

                            // Calculate the incident angle of light
                            double incidentAngleInternal = reflectionMethod.findIncidentAngle(
                                    reflectRayStartPoint, firstRefractedRayEndPoint,
                                    startingPointOfBottomLineOfPrism, endingPointOfBottomLineOfPrism);

                            // Calculate the end point coordinates of the reflected light, assuming the length is 1200
                            double[] internalReflectedRayEndPoint = reflectionMethod.calculateReflectRayEndPoint(intersectionBottomPrism,
                                    incidentAngleInternal, 1200, startingPointOfBottomLineOfPrism,
                                    endingPointOfBottomLineOfPrism);
                            double internalReflectedRayEndX = internalReflectedRayEndPoint[0];
                            double internalReflectedRayEndY = internalReflectedRayEndPoint[1];

                            //Set ReflectRay proporties
                            internalReflectRay.setStartX(intersectionBottomPrism[0]);
                            internalReflectRay.setStartY(intersectionBottomPrism[1]);
                            internalReflectRay.setEndX(internalReflectedRayEndX);
                            internalReflectRay.setEndY(internalReflectedRayEndY);
                            internalReflectRay.setStroke(Color.YELLOW);
                            firstRefractRay.setEndX(intersectionBottomPrism[0]);
                            firstRefractRay.setEndY(intersectionBottomPrism[1]);
                            
                            
                            //Refraction after internal reflection
                            refractMethod.refractThings(intersectionBottomPrism,
                                internalReflectedRayEndPoint, startingPointOfRightLineOfPrism,
                                endingPointOfRightLineOfPrism, lightRay, secondNormal,
                                secondRefractRay, "Air");
                            
                            double[] intersectionInternalWithRightPrismLine = refractMethod.findIntersection(
                            intersectionBottomPrism,
                            internalReflectedRayEndPoint,
                            startingPointOfRightLineOfPrism, endingPointOfRightLineOfPrism);

                        internalReflectRay.setEndX(intersectionInternalWithRightPrismLine[0]);
                        internalReflectRay.setEndY(intersectionInternalWithRightPrismLine[1]);
                        
                        
                    }else {//Without internal reflection
                        //Second Reflection
                        refractMethod.refractThings(reflectRayStartPoint,
                                firstRefractedRayEndPoint, startingPointOfRightLineOfPrism,
                                endingPointOfRightLineOfPrism, lightRay, secondNormal,
                                secondRefractRay, "Air");

                        firstRefractRay.setEndX(intersectionWithRightPrismLine[0]);
                        firstRefractRay.setEndY(intersectionWithRightPrismLine[1]);
                    }
                    
                    
                    //determine refracted line and wall 
                    double[] startPofSecRefrRay = lineMethod.getTheStartPointOfLine(secondRefractRay);
                        double[] endPofSecRefrRay = lineMethod.getTheEndPointOfLine(secondRefractRay);
                        
                    double[] interesctPRefWithWallTwo = wallMethod.findIntersectionWithWall(
                    startPofSecRefrRay, endPofSecRefrRay, wallTwo);

            boolean isRefTouchingTheWallTwo = wallMethod.wallTouched(startPofSecRefrRay,
                    endPofSecRefrRay, wallTwo);

            if (isRefTouchingTheWallTwo) {

                    secondRefractRay.setEndX(interesctPRefWithWallTwo[0]);
                    secondRefractRay.setEndY(interesctPRefWithWallTwo[1]);
            }else{

                        //Find intersection of second refract ray with Mirror One 
                        
                        double[] startPofMirrorOne = lineMethod.getTheStartPointOfLine(lineMirrorOne);
                        double[] endPofMirrorOne = lineMethod.getTheEndPointOfLine(lineMirrorOne);

                        boolean isTouchingTheMirrorOne = wallMethod.wallTouched(startPofSecRefrRay,
                                endPofSecRefrRay, lineMirrorOne);

                        if (isTouchingTheMirrorOne) {
                            double[] intersectionWithMirrorOne = reflectionMethod.findIntersection(startPofSecRefrRay,
                                    endPofSecRefrRay, startPofMirrorOne, endPofMirrorOne);

                            // Calculate the incident angle of light
                            double incidentAngleMirrorOne = reflectionMethod.findIncidentAngle(
                                    startPofSecRefrRay, endPofSecRefrRay,
                                    startPofMirrorOne, endPofMirrorOne);

                            // Calculate the end point coordinates of the reflected light, assuming the length is 800
                            double[] firstReflectedRayEndPoint = reflectionMethod.calculateReflectRayEndPoint(intersectionWithMirrorOne,
                                    incidentAngleMirrorOne, 800, startPofMirrorOne,
                                    endPofMirrorOne);
                            double firstReflectedRayEndX = firstReflectedRayEndPoint[0];
                            double firstReflectedRayEndY = firstReflectedRayEndPoint[1];

                            //Set ReflectRay proporties
                            firstReflectRay.setStartX(intersectionWithMirrorOne[0]);
                            firstReflectRay.setStartY(intersectionWithMirrorOne[1]);
                            firstReflectRay.setEndX(firstReflectedRayEndX);
                            firstReflectRay.setEndY(firstReflectedRayEndY);
                            firstReflectRay.setStroke(Color.YELLOW);
                            secondRefractRay.setEndX(intersectionWithMirrorOne[0]);
                            secondRefractRay.setEndY(intersectionWithMirrorOne[1]);
                        }

                        //determine ray with mirror two
                        double[] startPofFirstReflRay = lineMethod.getTheStartPointOfLine(firstReflectRay);
                        double[] endPofFirstReflRay = lineMethod.getTheEndPointOfLine(firstReflectRay);
                        double[] startPofMirrorTwo = lineMethod.getTheStartPointOfLine(lineMirrorTwo);
                        double[] endPofMirrorTwo = lineMethod.getTheEndPointOfLine(lineMirrorTwo);

                        boolean secReflectRayIsTouchingTheMirrorTwo = wallMethod.wallTouched(startPofFirstReflRay,
                                endPofFirstReflRay, lineMirrorTwo);

                        if (secReflectRayIsTouchingTheMirrorTwo) {
//                                System.out.println("Touching Mirror Two");
                            double[] intersectionWithMirrorTwo = reflectionMethod.findIntersection(startPofFirstReflRay,
                                    endPofFirstReflRay, startPofMirrorTwo, endPofMirrorTwo);

                            // Calculate the incident angle of light
                            double incidentAngleMirrorTwo = -1 * reflectionMethod.findIncidentAngle(
                                    startPofFirstReflRay, endPofFirstReflRay,
                                    startPofMirrorTwo, endPofMirrorTwo);

                            // Calculate the end point coordinates of the reflected light, assuming the length is 800
                            double[] secReflectedRayEndPoint = reflectionMethod.calculateReflectRayEndPoint(intersectionWithMirrorTwo,
                                    incidentAngleMirrorTwo, 1200, startPofMirrorTwo,
                                    endPofMirrorTwo);
                            double secReflectedRayEndX = secReflectedRayEndPoint[0];
                            double secReflectedRayEndY = secReflectedRayEndPoint[1];

                            //Set ReflectRay proporties
                            secReflectRay.setStartX(intersectionWithMirrorTwo[0]);
                            secReflectRay.setStartY(intersectionWithMirrorTwo[1]);
                            secReflectRay.setEndX(secReflectedRayEndX);
                            secReflectRay.setEndY(secReflectedRayEndY);
                            secReflectRay.setStroke(Color.YELLOW);
                            firstReflectRay.setEndX(intersectionWithMirrorTwo[0]);
                            firstReflectRay.setEndY(intersectionWithMirrorTwo[1]);

                        }

            }
                    }
                } //if the lightOne in wrong place
//                else {
//                    firstRefractRay.setStroke(Color.TRANSPARENT);
//                    secondRefractRay.setStroke(Color.TRANSPARENT);
//                    firstNormal.setStroke(Color.TRANSPARENT);
//                    secondNormal.setStroke(Color.TRANSPARENT);
//                }
            

            boolean isInteresctWithDetector = detectorMethod.isIntersecting(secReflectRay.getStartX(),
                    secReflectRay.getStartY(), secReflectRay.getEndX(),
                    secReflectRay.getEndY(), detectorImageView.getLayoutX() + 25,
                    detectorImageView.getLayoutY() + 25, 25);

            if (isInteresctWithDetector) {

                //rotate the door
                RotateTransition rotateDoor = new RotateTransition(Duration.seconds(2), doorImageView);
                rotateDoor.setFromAngle(0);
                rotateDoor.setToAngle(360);
                rotateDoor.play();

//                doorImageView.setImage(imageDoorOpened);
                imageViewWin.setVisible(true);
                
//                                circleForImageLevelUp.setFill(new ImagePattern(imageLevelUp));
//                                System.out.println("yes " + reflectRay.getBoundsInParent().getWidth());
            }

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
//            System.out.println("Touching border");
            reflectRay.setStroke(Color.TRANSPARENT);
            if (isTouchingTheBorderUp && eventEndingPointOfRay[1] <= lightBulb.getLayoutY()) {
//                System.out.println("Touching borderUp");
                lightRay.setEndX(interesctPointWithBorderUp[0]);
                lightRay.setEndY(interesctPointWithBorderUp[1]);
//                        System.out.println("interesctPointWithWallOne "
//                                +interesctPointWithWallOne[0]+"+"+interesctPointWithWallOne[1]);
//                        System.out.println("lightRay end x"+ lightRay.getEndX());
            } else if (isTouchingTheBorderLeft && eventEndingPointOfRay[0] < lightBulb.getLayoutX()) {
//                System.out.println("Touching borderLeft");
                lightRay.setEndX(interesctPointWithBorderLeft[0]);
                lightRay.setEndY(interesctPointWithBorderLeft[1]);

//                        System.out.println("interesctPointWithWallTwo "
//                                +interesctPointWithWallTwo[0]+"+"+interesctPointWithWallTwo[1]);
            } else if (isTouchingTheBorderRight && eventEndingPointOfRay[0] >= lightBulb.getLayoutX()) {
//                System.out.println("Touching borderRight");

                lightRay.setEndX(interesctPointWithBorderRight[0]);
                lightRay.setEndY(interesctPointWithBorderRight[1]);

            } else if (isTouchingTheBorderBottom && eventEndingPointOfRay[1] > lightBulb.getLayoutY()) {
                System.out.println("Touching Down");

                lightRay.setEndX(interesctPointWithBorderBottom[0]);
                lightRay.setEndY(interesctPointWithBorderBottom[1]);

            }
        }

    }

}
