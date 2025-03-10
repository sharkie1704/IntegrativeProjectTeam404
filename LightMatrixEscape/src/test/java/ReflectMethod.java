/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

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
//public class ReflectMethod extends Application {
public class ReflectMethod  {

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
//        Line lightRay = new Line(50, 200, 350, 200);
//        lightRay.setStroke(Color.BLACK);
//        root.getChildren().add(lightRay);
//
//        // create wall
//        Line wall = new Line(200, 50, 200, 350);
//        wall.setStroke(Color.GRAY);
//        root.getChildren().add(wall);
//
//        Line reflectRay = new Line(200, 50, 200, 350);
//        reflectRay.setStroke(Color.TRANSPARENT);
//        root.getChildren().addAll(reflectRay);
//
//        //Click the mouse to change the incident light and reflected light
//        root.setOnMouseClicked(event -> {
//
//            // Update the end point of the light
//            double[] startingPointOfRay = {50, 200}; // The starting point of ray
//            double[] endingPointOfRay = {event.getX(), event.getY()}; // The end point of ray
//            double[] startingPointOfWall = {200, 50}; // The starting point of wall
//            double[] endingPointOfWall = {200, 350}; // The end point of line segment CD (wall
//
//            double[] intersection = findIntersection(
//                    startingPointOfRay, endingPointOfRay,
//                    startingPointOfWall, endingPointOfWall);
//            lightRay.setEndX(intersection[0]);
//            lightRay.setEndY(intersection[1]);
//
//            // Calculate the incident angle of light
//            double incidentAngle = findIncidentAngle(
//                    startingPointOfRay, endingPointOfRay,
//                    startingPointOfWall, endingPointOfWall);
//
//            // Calculate the reflection angle of light
//            double reflectedAngle = incidentAngle;
//            System.out.println("reflectedAngle " + reflectedAngle + " ; reflectedAngle " + reflectedAngle);
//
//            // Calculate the end point coordinates of the reflected light, assuming the length is 200
//            double[] reflectedRayEndPoint = calculateReflectRayEndPoint(intersection,
//                    reflectedAngle, 200, startingPointOfWall,
//                    endingPointOfWall);
//            double reflectedRayEndX = reflectedRayEndPoint[0];
//            double reflectedRayEndY = reflectedRayEndPoint[1];
//
//            //Set ReflectRay proporties
//            reflectRay.setStartX(intersection[0]);
//            reflectRay.setStartY(intersection[1]);
//            reflectRay.setEndX(reflectedRayEndX);
//            reflectRay.setEndY(reflectedRayEndY);
//            reflectRay.setStroke(Color.BLACK);
//
//        });
//
//        Scene scene = new Scene(root, 400, 400);
//        primaryStage.setTitle("Reflection Game");
//        primaryStage.setScene(scene);
//        primaryStage.show();
//    }

    @Test
    /**
     *
     * @param startingCoordinateOfFirstLineA
     * @param endingCoordinateOfFirstLineB
     * @param startingCoordinateOfSecondLineC
     * @param endingCoordinateOfSecondLineD
     * @return Intersection point between two line
     */
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
     * @param startingCoordinateOfRayA
     * @param endingCoordinateOfRayB
     * @param startingCoordinateOfWallC
     * @param endingCoordinateOfWallD
     * @return incident angle
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
     * @param intersection //intersection is the starting point coordinate of
     * //the reflected light, which is the point where it //intersects with the
     * wall
     * @param angleReflected
     * @param lengthOfReflectedLightCD
     * @param startingCoordinateOfWallA
     * @param endingCoordinateOfWallB
     * @return
     */
    @Test
    public static double[] calculateReflectRayEndPoint(double[] intersection,
            double angleReflected, double lengthOfReflectedLightCD, double[] startingCoordinateOfWallA,
            double[] endingCoordinateOfWallB) {
        //A and B are the starting and ending coordinates of the wall
        //intersection is the starting point coordinate of the reflected light, which is the point where it intersects with AB
        //angle is the angle between the reflected light and the wall
        //lengthCD is the length of the reflected light

        // Calculate the length and direction vector of line segment AB
        double lengthAB = Math.sqrt(Math.pow(endingCoordinateOfWallB[0] - startingCoordinateOfWallA[0], 2)
                + Math.pow(endingCoordinateOfWallB[1] - startingCoordinateOfWallA[1], 2));
        double[] abVector = {(endingCoordinateOfWallB[0] - startingCoordinateOfWallA[0]) / lengthAB,
            (endingCoordinateOfWallB[1] - startingCoordinateOfWallA[1]) / lengthAB};

        //Convert angle to radians
        double angleRadians = Math.toRadians(angleReflected);

        // Calculate the direction vector of line segment CD
        double[] CDVector = {
            Math.cos(angleRadians) * abVector[0] - Math.sin(angleRadians) * abVector[1],
            Math.sin(angleRadians) * abVector[0] + Math.cos(angleRadians) * abVector[1]
        };

        // Calculate the end point coordinates of line segment CD
        double[] reflectRayEndPoint = {intersection[0] + lengthOfReflectedLightCD * CDVector[0],
            intersection[1] + lengthOfReflectedLightCD * CDVector[1]};

        return reflectRayEndPoint;
    }

    /**
     *
     * @param startingCoordinateOfWallA
     * @param endingCoordinateOfWallB
     * @param intersection
     * @return the start point of normal
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
     * @param intersection
     * @return
     */
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
        double[] endPointOfNormal = {intersection[0] - 0.5 * lengthOfNormal * perpendicularVector[0],
            intersection[1] - 0.5 * lengthOfNormal * perpendicularVector[1]};
        return endPointOfNormal;

    }

}
