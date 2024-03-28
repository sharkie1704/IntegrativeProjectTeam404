/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.util.Arrays;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Hongyan Li
 */
//public class RefractMethod extends Application {
public class RefractMethod {

//    public static void main(String[] args) {
//        launch(args);
//    }
//
//    @Test
//    @Override
//    public void start(Stage primaryStage) {
//        Pane root = new Pane();
//
//        // create ray
//        Line lightRay = new Line(50, 25 + 100 * Math.sqrt(3),
//                50, 25 + 100 * Math.sqrt(3));
//        lightRay.setStroke(Color.BLACK);
//        root.getChildren().add(lightRay);
//
//        // create left line of Prism
//        Line leftLineOfPrism = new Line(200, 25,
//                100, 25 + 100 * Math.sqrt(3)); //  /  
//        leftLineOfPrism.setStroke(Color.GRAY);
//
//        root.getChildren().add(leftLineOfPrism);
//
//        //create right line of Prism
//        Line rightLineOfPrism = new Line(200, 25,
//                300, 25 + 100 * Math.sqrt(3));  // \  
//        rightLineOfPrism.setStroke(Color.GRAY);
//        root.getChildren().add(rightLineOfPrism);
//
//        //create bottom line of Prism
//        Line bottomLineOfPrism = new Line(100, 25 + 100 * Math.sqrt(3),
//                300, 25 + 100 * Math.sqrt(3));  // _  
//        bottomLineOfPrism.setStroke(Color.GRAY);
//        root.getChildren().add(bottomLineOfPrism);
//
//        Line firstRefractRay = new Line(200, 50, 200, 350);
//        firstRefractRay.setStroke(Color.TRANSPARENT);
//        root.getChildren().addAll(firstRefractRay);
//
//        Line secondRefractRay = new Line(200, 50, 200, 350);
//        secondRefractRay.setStroke(Color.TRANSPARENT);
//        root.getChildren().addAll(secondRefractRay);
//
//        Line firstNormal = new Line(50, 200, 50, 200); //random place
//        firstNormal.setStroke(Color.TRANSPARENT);
//        root.getChildren().add(firstNormal);
//
//        Line secondNormal = new Line(50, 200, 50, 200); //random place
//        secondNormal.setStroke(Color.TRANSPARENT);
//        root.getChildren().add(secondNormal);
//
//        //Click the mouse to change the incident light and Refracted light
//        root.setOnMouseClicked(event -> {
//
//            RefractMethods reflectMethodsOne = new RefractMethods();
//
//            double[] startingPointOfRay = {50, 200}; // The starting point of ray
//            double[] endingPointOfRay = {event.getX(), event.getY()}; // The end point of ray
//            double[] startingPointOfLeftLineOfPrism = {leftLineOfPrism.getStartX(),
//                leftLineOfPrism.getStartY()}; // The starting point of /
//            double[] endingPointOfLeftLineOfPrism = {leftLineOfPrism.getEndX(),
//                leftLineOfPrism.getEndY()}; // The end point of line /
//            double[] startingPointOfRightLineOfPrism = {rightLineOfPrism.getStartX(),
//                rightLineOfPrism.getStartY()}; // The starting point of line \
//            double[] endingPointOfRightLineOfPrism = {rightLineOfPrism.getEndX(),
//                rightLineOfPrism.getEndY()}; // The end point of line \
//
//            double[] reflectRayStartPoint = reflectMethodsOne.findIntersection(
//                    startingPointOfRay,
//                    endingPointOfRay,
//                    startingPointOfLeftLineOfPrism,
//                    endingPointOfLeftLineOfPrism);
//
//            double refractedAngleOne = reflectMethodsOne.FindrefractedAngle(
//                    startingPointOfRay, endingPointOfRay,
//                    startingPointOfLeftLineOfPrism,
//                    endingPointOfLeftLineOfPrism, "Glass");
//
//            double[] firstNoramlStartPoint = reflectMethodsOne.calculateNormalStartPoint(
//                    startingPointOfLeftLineOfPrism,
//                    endingPointOfLeftLineOfPrism,
//                    reflectRayStartPoint);
//
//            double[] firstNoramlEndPoint = reflectMethodsOne.calculateNormalEndPoint(
//                    startingPointOfLeftLineOfPrism,
//                    endingPointOfLeftLineOfPrism,
//                    reflectRayStartPoint);
//
//            double[] firstRefractedRayEndPoint = reflectMethodsOne.calculateRefractRayEndPoint(
//                    reflectRayStartPoint, refractedAngleOne,
//                    200, firstNoramlStartPoint,
//                    firstNoramlEndPoint);
//
//            lightRay.setEndX(reflectRayStartPoint[0]);
//            lightRay.setEndY(reflectRayStartPoint[1]);
//
//            //make sure the ray only reflect when touching the prism
//            if (reflectRayStartPoint[1] < leftLineOfPrism.getEndY()
//                    && reflectRayStartPoint[1] > leftLineOfPrism.getStartY()) {
//                //First Reflection
//                reflectMethodsOne.reflectThings(startingPointOfRay, endingPointOfRay,
//                        startingPointOfLeftLineOfPrism, endingPointOfLeftLineOfPrism, lightRay,
//                        firstNormal, firstRefractRay, "Glass");
//                System.out.println("Yes");
//
//                if (firstRefractedRayEndPoint[1] < rightLineOfPrism.getEndY()
//                        && firstRefractedRayEndPoint[1] > rightLineOfPrism.getStartY()) {
//                    //Second Reflection
//                    reflectMethodsOne.reflectThings(reflectRayStartPoint,
//                            firstRefractedRayEndPoint, startingPointOfRightLineOfPrism,
//                            endingPointOfRightLineOfPrism, lightRay, secondNormal,
//                            secondRefractRay, "Air");
//
//                    // Find intersection between ReflectRay and PrismTwoC-D
//                    double[] intersectionSecond = reflectMethodsOne.findIntersection(
//                            reflectRayStartPoint,
//                            firstRefractedRayEndPoint,
//                            startingPointOfRightLineOfPrism, endingPointOfRightLineOfPrism);
//
//                    firstRefractRay.setEndX(intersectionSecond[0]);
//                    firstRefractRay.setEndY(intersectionSecond[1]);
//                } else { //if the lightTwo in wrong place
//                    secondRefractRay.setStroke(Color.TRANSPARENT);
//                    secondNormal.setStroke(Color.TRANSPARENT);
//                }
//            } //if the lightOne in wrong place
//            else {
//                lightRay.setEndX(event.getX());
//                lightRay.setEndY(event.getY());
//                firstRefractRay.setStroke(Color.TRANSPARENT);
//                secondRefractRay.setStroke(Color.TRANSPARENT);
//                firstNormal.setStroke(Color.TRANSPARENT);
//                secondNormal.setStroke(Color.TRANSPARENT);
////                System.out.println("No");
//            }
//
//        });
//
//        Scene scene = new Scene(root, 400, 400);
//        primaryStage.setTitle("Refraction Game");
//        primaryStage.setScene(scene);
//        primaryStage.show();
//    }

    @Test
    /**
     *
     * @param startPointOfRayA
     * @param endPointOfRayB
     * @param startPointOfPrismC
     * @param endPointOfPrismD
     * @param lightRay
     * @param normal
     * @param refractRay
     * @param Type //glass or air
     */
    //An overall method that add some methods together
    public static void reflectThings(double[] startPointOfRayA, double[] endPointOfRayB,
            double[] startPointOfPrismC, double[] endPointOfPrismD, Line lightRay,
            Line normal, Line refractRay, String Type) {

        // Update the end point of the Ray
        double[] intersection = findIntersection(
                startPointOfRayA,
                endPointOfRayB,
                startPointOfPrismC,
                endPointOfPrismD);

        //Set Normal's proporties
        double[] normalOneStartPoint = calculateNormalStartPoint(
                startPointOfPrismC,
                endPointOfPrismD, intersection);
        double[] noramlOneEndPoint = calculateNormalEndPoint(
                startPointOfPrismC,
                endPointOfPrismD, intersection); //Find the Normal endpoint      
        normal.setStartX(normalOneStartPoint[0]);
        normal.setStartY(normalOneStartPoint[1]);
        normal.setEndX(noramlOneEndPoint[0]);
        normal.setEndY(noramlOneEndPoint[1]);
        normal.setStroke(Color.DARKSALMON);

        // Calculate the Refraction angle of light
        double refractedAngleOne = FindrefractedAngle(startPointOfRayA, endPointOfRayB,
                startPointOfPrismC, endPointOfPrismD, Type);

        // Calculate the end point coordinates of the Refracted light, assuming the length is 200
        double[] refractedRayOneEndPoint = calculateRefractRayEndPoint(intersection,
                refractedAngleOne, 200,
                normalOneStartPoint,
                noramlOneEndPoint);
        double refractedRayOneEndX = refractedRayOneEndPoint[0];
        double refractedRayOneEndY = refractedRayOneEndPoint[1];

        //Set RefractRayOne proporties
        refractRay.setStartX(intersection[0]);
        refractRay.setStartY(intersection[1]);
        refractRay.setEndX(refractedRayOneEndX);
        refractRay.setEndY(refractedRayOneEndY);
        refractRay.setStroke(Color.BLACK);

//        System.out.println(Type + Arrays.toString(intersection));
    }

    /**
     *
     * @param startingCoordinateOfRayA
     * @param endingCoordinateOfRayB
     * @param startingCoordinateOfWallC
     * @param endingCoordinateOfWallD
     * @return Incident Angle
     */
    @Test
    public static double findIncidentAngle(double[] startingCoordinateOfRayA,
            double[] endingCoordinateOfRayB, double[] startingCoordinateOfWallC,
            double[] endingCoordinateOfWallD) {
        //A and B are the starting and ending coordinates of the ray
        //C and D are the starting and ending coordinates of the wall

        // Calculate vectors AB and CD
        double[] ab = {endingCoordinateOfRayB[0] - startingCoordinateOfRayA[0],
            endingCoordinateOfRayB[1] - startingCoordinateOfRayA[1]};
        double[] cd = {endingCoordinateOfWallD[0] - startingCoordinateOfWallC[0],
            endingCoordinateOfWallD[1] - startingCoordinateOfWallC[1]};

        // Calculate the dot product of vectors AB and CD
        double dotProduct = ab[0] * cd[0] + ab[1] * cd[1];

        // Calculate the modulus of vectors AB and CD
        double magnitudeAB = Math.sqrt(ab[0] * ab[0] + ab[1] * ab[1]);
        double magnitudeCD = Math.sqrt(cd[0] * cd[0] + cd[1] * cd[1]);

        // Calculate the angle
        double cosTheta = dotProduct / (magnitudeAB * magnitudeCD);
        double angleRadians = Math.acos(cosTheta);
        double angleDegrees = Math.toDegrees(angleRadians);

        //        return angle
        return angleDegrees;

    }

    /**
     *
     * @param startPointOfRayA
     * @param endPointOfRayB
     * @param startPointOfPrismC
     * @param endPointOfPrismD
     * @param Type glass/air
     * @return
     */
    @Test
    public static double FindrefractedAngle(double[] startPointOfRayA,
            double[] endPointOfRayB, double[] startPointOfPrismC,
            double[] endPointOfPrismD, String Type) {

        // Calculate the incident angle of light
//            double incidentAngle =  Math.toRadians(Math.abs(findIncidentAngle(A, B, C, D)-90));
        double incidentAngle = Math.toRadians(findIncidentAngle(startPointOfRayA,
                endPointOfRayB, startPointOfPrismC,
                endPointOfPrismD) - 90);
        double refractedAngle = 0;

        // Calculate the Refraction angle of light
        if (Type.equals("Glass")) {
            refractedAngle = -Math.toDegrees(Math.asin(Math.sin(incidentAngle) / 1.33));

//            System.out.println("incidentAngle " + incidentAngleOne + " ; RefractedAngle " + refractedAngleOne);
        } else if (Type.equals("Air")) {
            refractedAngle = -Math.toDegrees(Math.asin(Math.sin(incidentAngle) * 1.33));

        } else {
            refractedAngle = 0;
        }

        return refractedAngle;
    }

    /**
     *
     * @param startingCoordinateOfFirstLineA
     * @param endingCoordinateOfFirstLineB
     * @param startingCoordinateOfSecondLineC
     * @param endingCoordinateOfSecondLineD
     * @return Intersection point of two lines
     */
    @Test
    public static double[] findIntersection(double[] startingCoordinateOfFirstLineA,
            double[] endingCoordinateOfFirstLineB, double[] startingCoordinateOfSecondLineC,
            double[] endingCoordinateOfSecondLineD) {

        // Calculate vectors ab,cd and ac
        double[] ab = {endingCoordinateOfFirstLineB[0] - startingCoordinateOfFirstLineA[0],
            endingCoordinateOfFirstLineB[1] - startingCoordinateOfFirstLineA[1]};
        double[] cd = {endingCoordinateOfSecondLineD[0] - startingCoordinateOfSecondLineC[0],
            endingCoordinateOfSecondLineD[1] - startingCoordinateOfSecondLineC[1]};
        double[] ac = {startingCoordinateOfSecondLineC[0] - startingCoordinateOfFirstLineA[0],
            startingCoordinateOfSecondLineC[1] - startingCoordinateOfFirstLineA[1]};

        double denominator = ab[0] * cd[1] - ab[1] * cd[0];
        if (denominator == 0) { // make sure lines are parallel
            return null;
        }

        double t1 = (ac[0] * cd[1] - ac[1] * cd[0]) / denominator;

        // Calculate intersection coordinates
        double intersectionX = startingCoordinateOfFirstLineA[0] + t1 * ab[0];
        double intersectionY = startingCoordinateOfFirstLineA[1] + t1 * ab[1];
        double[] intersectionPoint = {intersectionX, intersectionY};

        // return Intersection point
        return intersectionPoint;

    }

    /**
     *
     * @param intersection
     * @param angle
     * @param lengthOfTheRefractedLight
     * @param startingCoordinateOfWallA
     * @param endingCoordinateOfWallB
     * @return refract ray's end point
     */
    @Test
    public static double[] calculateRefractRayEndPoint(double[] intersection,
            double angle, double lengthOfTheRefractedLight,
            double[] startingCoordinateOfWallA, double[] endingCoordinateOfWallB) {
        //A and B are the starting and ending coordinates of the wall
        //intersection is the starting point coordinate of the Refracted light, 
        //which is the point where it intersects with AB
        //angle is the angle between the Refracted light and the wall
        //lengthOfTheRefractedLight is the length of the Refracted light

        // Calculate the length and direction vector of line segment AB
        double lengthAB = Math.sqrt(Math.pow(endingCoordinateOfWallB[0]
                - startingCoordinateOfWallA[0], 2)
                + Math.pow(endingCoordinateOfWallB[1] - startingCoordinateOfWallA[1], 2));
        double[] abVector = {(endingCoordinateOfWallB[0] - startingCoordinateOfWallA[0]) / lengthAB,
            (endingCoordinateOfWallB[1] - startingCoordinateOfWallA[1]) / lengthAB};

        //Convert angle to radians
        double angleRadians = Math.toRadians(angle);

        // Calculate the direction vector of line segment CD
        double[] cdVector = {
            Math.cos(angleRadians) * abVector[0] - Math.sin(angleRadians) * abVector[1],
            Math.sin(angleRadians) * abVector[0] + Math.cos(angleRadians) * abVector[1]
        };

        // Calculate the end point coordinates of line segment CD
        double[] RefractRayEndPoint = {intersection[0] + lengthOfTheRefractedLight * cdVector[0],
            intersection[1] + lengthOfTheRefractedLight * cdVector[1]};

        return RefractRayEndPoint;
    }

    /**
     *
     * @param startingCoordinateOfWallA
     * @param endingCoordinateOfWallB
     * @param intersection //intersection of ray and wall
     * @return normal's start point
     */
    @Test
    public static double[] calculateNormalStartPoint(double[] startingCoordinateOfWallA,
            double[] endingCoordinateOfWallB, double[] intersection) {

        //Assume the length of normal is 200
        double lengthOfNormal = 200;

        // Calculate the unit vector of line segment AB
        double[] abVector = {(endingCoordinateOfWallB[0] - startingCoordinateOfWallA[0]),
            (endingCoordinateOfWallB[1] - startingCoordinateOfWallA[1])};
        double lengthAB = Math.sqrt(abVector[0] * abVector[0] + abVector[1] * abVector[1]);
        abVector[0] /= lengthAB;
        abVector[1] /= lengthAB;

        // Calculate vertical vector
        double[] perpendicularVector = {-abVector[1], abVector[0]};

        // Calculate Start Point of mormal
        double[] startPointOfNormal = {intersection[0] + 0.5 * lengthOfNormal * perpendicularVector[0],
            intersection[1] + 0.5 * lengthOfNormal * perpendicularVector[1]};

        return startPointOfNormal;

    }

    /**
     *
     * @param startingCoordinateOfWallA
     * @param endingCoordinateOfWallB
     * @param intersection //intersection of ray and wall
     * @return normal's end point
     */
    @Test
    public static double[] calculateNormalEndPoint(double[] startingCoordinateOfWallA,
            double[] endingCoordinateOfWallB, double[] intersection) {

        //Assume the length of normal is 200
        double lengthOfNormal = 200;

        // alculate the unit vector of line segment AB
        double[] abVector = {(endingCoordinateOfWallB[0] - startingCoordinateOfWallA[0]),
            (endingCoordinateOfWallB[1] - startingCoordinateOfWallA[1])};
        double lengthAB = Math.sqrt(abVector[0] * abVector[0] + abVector[1] * abVector[1]);
        abVector[0] /= lengthAB;
        abVector[1] /= lengthAB;

        // Calculate vertical vector
        double[] perpendicularVector = {-abVector[1], abVector[0]};

        // Calculate Normal end Point
        double[] endPointOfNormal = {intersection[0] - 0.5 * lengthOfNormal * perpendicularVector[0], intersection[1] - 0.5 * lengthOfNormal * perpendicularVector[1]};
        return endPointOfNormal;

    }

}
