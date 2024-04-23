/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import java.io.FileNotFoundException;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 *
 * @author 2254403
 */
public class LevelPageTwoController {

    @FXML
    Pane gamePane;

    @FXML
    Slider volumeSlider;

    @FXML
    Rectangle mirrorOne, mirrorTwo;

    @FXML
    Line wallOne, wallTwo, wallThree;

    @FXML
    Ellipse lightBulb;

    @FXML
    ImageView doorImageView, detectorImageView;

    Stage stage;

    Refraction refractMethod = new Refraction();

    public void initialize() throws FileNotFoundException {

        // create ray in level 2
        Line lightRay = new Line(105,522,
                50, 25 + 100 * Math.sqrt(3));
        lightRay.setStroke(Color.TRANSPARENT);
        gamePane.getChildren().add(lightRay);

        // create left line of Prism
        Line leftLineOfPrism = new Line(235, 25,
                135, 25 + 100 * Math.sqrt(3)); //  /  
        leftLineOfPrism.setStroke(Color.TRANSPARENT);

        gamePane.getChildren().add(leftLineOfPrism);

        //create right line of Prism
        Line rightLineOfPrism = new Line(235, 25,
                335, 25 + 100 * Math.sqrt(3));  // \  
        rightLineOfPrism.setStroke(Color.TRANSPARENT);
        gamePane.getChildren().add(rightLineOfPrism);

        //create bottom line of Prism
        Line bottomLineOfPrism = new Line(135, 25 + 100 * Math.sqrt(3),
                335, 25 + 100 * Math.sqrt(3));  // _  
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

        gamePane.setOnMouseClicked(event -> {
            System.out.println("Yes");//Try
            lightRay.setStroke(Color.BLACK);
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

            lightRay.setEndX(reflectRayStartPoint[0]);
            lightRay.setEndY(reflectRayStartPoint[1]);

            //make sure the ray only reflect when touching the prism
            if (reflectRayStartPoint[1] < leftLineOfPrism.getEndY()
                    && reflectRayStartPoint[1] > leftLineOfPrism.getStartY()) {
                //First Reflection
                refractMethod.reflectThings(startingPointOfRay, endingPointOfRay,
                        startingPointOfLeftLineOfPrism, endingPointOfLeftLineOfPrism, lightRay,
                        firstNormal, firstRefractRay, "Glass");
                System.out.println("Yes");

                if (firstRefractedRayEndPoint[1] < rightLineOfPrism.getEndY()
                        && firstRefractedRayEndPoint[1] > rightLineOfPrism.getStartY()) {
                    //Second Reflection
                    refractMethod.reflectThings(reflectRayStartPoint,
                            firstRefractedRayEndPoint, startingPointOfRightLineOfPrism,
                            endingPointOfRightLineOfPrism, lightRay, secondNormal,
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
                lightRay.setEndX(event.getX());
                lightRay.setEndY(event.getY());
                firstRefractRay.setStroke(Color.TRANSPARENT);
                secondRefractRay.setStroke(Color.TRANSPARENT);
                firstNormal.setStroke(Color.TRANSPARENT);
                secondNormal.setStroke(Color.TRANSPARENT);
//                System.out.println("No");
            }

        });

    }

    public Stage giveStage(Stage stage) {
        return this.stage = stage;
    }

}
