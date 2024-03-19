///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//
//import javafx.application.Application;
//import static javafx.application.Application.launch;
//import javafx.scene.Scene;
//import javafx.scene.layout.Pane;
//import javafx.scene.paint.Color;
//import javafx.scene.shape.Line;
//import javafx.stage.Stage;
//import org.junit.jupiter.api.Test;
//
///**
// *
// * @author Hongyan Li
// */
//public class RefractMethod extends Application {
//
//    public static void main(String[] args) {
//        launch(args);
//    }
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
//        Line RefractRay = new Line(200, 50, 200, 350);
//        RefractRay.setStroke(Color.TRANSPARENT);
//        root.getChildren().addAll(RefractRay);
//
//        Line Normal = new Line(200, 50, 200, 350);
//        RefractRay.setStroke(Color.BURLYWOOD);
//        root.getChildren().add(Normal);
//
//        //Click the mouse to change the incident light and Refracted light
//        root.setOnMouseClicked(event -> {
//
//            // Update the end point of the light
//            double[] A = {50, 200}; // The starting point of line segment AB (ray
//            double[] B = {event.getX(), event.getY()}; // The end point of line segment AB (ray
//            double[] C = {200, 50}; // The starting point of line segment CD (wall
//            double[] D = {200, 350}; // The end point of line segment CD (wall
//
//            double[] intersection = findIntersection(A, B, C, D);
//            lightRay.setEndX(intersection[0]);
//            lightRay.setEndY(intersection[1]);
//
//            //Set Normal proporties
//            double[] NoramlStartPoint = calculateNormalStartPoint(C, D, intersection);
//            double[] NoramlEndPoint = calculateNormalEndPoint(C, D, intersection); //FInd the Normal endpoint      
//            Normal.setStartX(NoramlStartPoint[0]);
//            Normal.setStartY(NoramlStartPoint[1]);
//            Normal.setEndX(NoramlEndPoint[0]);
//            Normal.setEndY(NoramlEndPoint[1]);
//            Normal.setStroke(Color.DARKSALMON);
//
//            // Calculate the incident angle of light
////            double incidentAngle =  Math.toRadians(Math.abs(findIncidentAngle(A, B, C, D)-90));
//            double incidentAngle = Math.toRadians(findIncidentAngle(A, B, C, D) - 90);
//
//            // Calculate the Refraction angle of light
//            double refractedAngle = -Math.toDegrees(Math.asin(Math.sin(incidentAngle) / 1.33));
//            System.out.println("incidentAngle " + incidentAngle + " ; RefractedAngle " + refractedAngle);
//
//            // Calculate the end point coordinates of the Refracted light, assuming the length is 200
//            double[] RefractedRayEndPoint = calculateRefractRayEndPoint(intersection, refractedAngle, 200, NoramlStartPoint, NoramlEndPoint);
//            double RefractedRayEndX = RefractedRayEndPoint[0];
//            double RefractedRayEndY = RefractedRayEndPoint[1];
//
//            //Set RefractRay proporties
//            RefractRay.setStartX(intersection[0]);
//            RefractRay.setStartY(intersection[1]);
//            RefractRay.setEndX(RefractedRayEndX);
//            RefractRay.setEndY(RefractedRayEndY);
//            RefractRay.setStroke(Color.BLACK);
//        });
//
//        Scene scene = new Scene(root, 400, 400);
//        primaryStage.setTitle("Refraction Game");
//        primaryStage.setScene(scene);
//        primaryStage.show();
//    }
//
//    public static double[] findIntersection(double[] A, double[] B, double[] C, double[] D) {
//
//        //A and B are the starting and ending coordinates of the ray
//        //C and D are the starting and ending coordinates of the wall
//        double[] AB = {B[0] - A[0], B[1] - A[1]};
//        double[] CD = {D[0] - C[0], D[1] - C[1]};
//        double[] AC = {C[0] - A[0], C[1] - A[1]};
//
//        double denominator = AB[0] * CD[1] - AB[1] * CD[0];
//        if (denominator == 0) { // make sure 线段parallel
//            return null;
//        }
//
//        double t1 = (AC[0] * CD[1] - AC[1] * CD[0]) / denominator;
//
//        // Calculate intersection coordinates
//        double x = A[0] + t1 * AB[0];
//        double y = A[1] + t1 * AB[1];
//        double[] P = {x, y};
//
//        // return Intersection point
//        return P;
//
//    }
//
//    public static double findIncidentAngle(double[] A, double[] B, double[] C, double[] D) {
//        //A and B are the starting and ending coordinates of the ray
//        //C and D are the starting and ending coordinates of the wall
//
//        // Calculate vectors AB and CD
//        double[] AB = {B[0] - A[0], B[1] - A[1]};
//        double[] CD = {D[0] - C[0], D[1] - C[1]};
//
//        // Calculate the dot product of vectors AB and CD
//        double dotProduct = AB[0] * CD[0] + AB[1] * CD[1];
//
//        // Calculate the modulus of vectors AB and CD
//        double magnitudeAB = Math.sqrt(AB[0] * AB[0] + AB[1] * AB[1]);
//        double magnitudeCD = Math.sqrt(CD[0] * CD[0] + CD[1] * CD[1]);
//
//        // Calculate the angle
//        double cosTheta = dotProduct / (magnitudeAB * magnitudeCD);  // 余弦值
//        double angleRadians = Math.acos(cosTheta);
//        double angleDegrees = Math.toDegrees(angleRadians);
//
//        //        return angle
//        return angleDegrees;
//
//    }
//
//    public static double[] calculateRefractRayEndPoint(double[] intersection, double angle, double lengthCD, double[] A, double[] B) {
//        //A and B are the starting and ending coordinates of the wall
//        //intersection is the starting point coordinate of the Refracted light, which is the point where it intersects with AB
//        //angle is the angle between the Refracted light and the wall
//        //lengthCD is the length of the Refracted light
//
//        // Calculate the length and direction vector of line segment AB
//        double lengthAB = Math.sqrt(Math.pow(B[0] - A[0], 2) + Math.pow(B[1] - A[1], 2));
//        double[] ABVector = {(B[0] - A[0]) / lengthAB, (B[1] - A[1]) / lengthAB};
//
//        //Convert angle to radians
//        double angleRadians = Math.toRadians(angle);
//
//        // Calculate the direction vector of line segment CD
//        double[] CDVector = {
//            Math.cos(angleRadians) * ABVector[0] - Math.sin(angleRadians) * ABVector[1],
//            Math.sin(angleRadians) * ABVector[0] + Math.cos(angleRadians) * ABVector[1]
//        };
//
//        // Calculate the end point coordinates of line segment CD
//        double[] D = {intersection[0] + lengthCD * CDVector[0], intersection[1] + lengthCD * CDVector[1]};
//
//        return D;
//    }
//
//    public static double[] calculateNormalStartPoint(double[] WallA, double[] WallB, double[] intersection) {
//
//        double lengthNormal = 200;
//
//        // Calculate the unit vector of line segment AB
//        double[] ABVector = {(WallB[0] - WallA[0]), (WallB[1] - WallA[1])};
//        double lengthAB = Math.sqrt(ABVector[0] * ABVector[0] + ABVector[1] * ABVector[1]);
//        ABVector[0] /= lengthAB;
//        ABVector[1] /= lengthAB;
//
//        // Calculate vertical vector
//        double[] perpendicularVector = {-ABVector[1], ABVector[0]};
//
//        // Calculate NormalStartPoint
//        double[] NormalStartPoint = {intersection[0] + 0.5 * lengthNormal * perpendicularVector[0], intersection[1] + 0.5 * lengthNormal * perpendicularVector[1]};
//        return NormalStartPoint;
//
//    }
//
//    public static double[] calculateNormalEndPoint(double[] WallA, double[] WallB, double[] intersection) {
//
//        double lengthNormal = 200;
//
//        // alculate the unit vector of line segment AB
//        double[] ABVector = {(WallB[0] - WallA[0]), (WallB[1] - WallA[1])};
//        double lengthAB = Math.sqrt(ABVector[0] * ABVector[0] + ABVector[1] * ABVector[1]);
//        ABVector[0] /= lengthAB;
//        ABVector[1] /= lengthAB;
//
//        // Calculate vertical vector
//        double[] perpendicularVector = {-ABVector[1], ABVector[0]};
//
//        // Calculate NormalStartPoint
//        double[] NormalEnd = {intersection[0] - 0.5 * lengthNormal * perpendicularVector[0], intersection[1] - 0.5 * lengthNormal * perpendicularVector[1]};
//        return NormalEnd;
//
//    }
//
//}
