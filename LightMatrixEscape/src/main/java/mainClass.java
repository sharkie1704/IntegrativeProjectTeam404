
import controllers.MainAppController;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class mainClass extends Application {

    public static void main(String[] args) {
        launch(args);
    }

//    @Override
//    public void start(Stage primaryStage) throws IOException {
//       
////        FXMLLoader loader = new FXMLLoader(
////                getClass().getResource("/fxml/main_page_layout.fxml")
////        
////            );
////        MainAppController fxmlController = new MainAppController();
////        loader.setController(fxmlController);
////        fxmlController.giveStage(primaryStage);
////        
////        Pane root = loader.load();
//
//
//        Pane root = new Pane();
//        Scene scene = new Scene(root);
//        primaryStage.setTitle("Refraction Game");
//        primaryStage.setScene(scene);
//        primaryStage.show();
//    }
//}
//        //Reflect show:
//         // create ray
//        ReflectMethods reflectMethodsOne=new ReflectMethods();
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
//            double[] intersection = reflectMethodsOne.findIntersection(
//                    startingPointOfRay, endingPointOfRay, 
//                    startingPointOfWall, endingPointOfWall);
//            lightRay.setEndX(intersection[0]);
//            lightRay.setEndY(intersection[1]);
//
//            // Calculate the incident angle of light
//            double incidentAngle = reflectMethodsOne.findIncidentAngle(
//                    startingPointOfRay, endingPointOfRay, 
//                    startingPointOfWall, endingPointOfWall);
//
//            // Calculate the reflection angle of light
//            double reflectedAngle = incidentAngle;
//            System.out.println("reflectedAngle " + reflectedAngle + " ; reflectedAngle " + reflectedAngle);
//
//            // Calculate the end point coordinates of the reflected light, assuming the length is 200
//            double[] reflectedRayEndPoint = reflectMethodsOne.calculateReflectRayEndPoint(intersection, 
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
//    }
//
//}
    @Override
    public void start(Stage stage) {
//        String javaVersion = System.getProperty("java.version");
//        String javafxVersion = System.getProperty("javafx.version");
//        Label l = new Label("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");
//        Scene scene = new Scene(new StackPane(l), 640, 480);
//        stage.setScene(scene);
//        stage.show();
//        

        // Methods for refract:
        Pane root = new Pane();

        // create ray
        Line lightRay = new Line(50, 25 + 100 * Math.sqrt(3),
                50, 25 + 100 * Math.sqrt(3));
        lightRay.setStroke(Color.BLACK);
        root.getChildren().add(lightRay);

        // create left line of Prism
        Line leftLineOfPrism = new Line(200, 25,
                100, 25 + 100 * Math.sqrt(3)); //  /  
        leftLineOfPrism.setStroke(Color.GRAY);

        root.getChildren().add(leftLineOfPrism);

        //create right line of Prism
        Line rightLineOfPrism = new Line(200, 25,
                300, 25 + 100 * Math.sqrt(3));  // \  
        rightLineOfPrism.setStroke(Color.GRAY);
        root.getChildren().add(rightLineOfPrism);

        //create bottom line of Prism
        Line bottomLineOfPrism = new Line(100, 25 + 100 * Math.sqrt(3),
                300, 25 + 100 * Math.sqrt(3));  // _  
        bottomLineOfPrism.setStroke(Color.GRAY);
        root.getChildren().add(bottomLineOfPrism);

        Line firstRefractRay = new Line(200, 50, 200, 350);
        firstRefractRay.setStroke(Color.TRANSPARENT);
        root.getChildren().addAll(firstRefractRay);

        Line secondRefractRay = new Line(200, 50, 200, 350);
        secondRefractRay.setStroke(Color.TRANSPARENT);
        root.getChildren().addAll(secondRefractRay);

        Line firstNormal = new Line(50, 200, 50, 200); //random place
        firstNormal.setStroke(Color.TRANSPARENT);
        root.getChildren().add(firstNormal);

        Line secondNormal = new Line(50, 200, 50, 200); //random place
        secondNormal.setStroke(Color.TRANSPARENT);
        root.getChildren().add(secondNormal);

        //Click the mouse to change the incident light and Refracted light
        root.setOnMouseClicked(event -> {

            RefractMethods reflectMethodsOne = new RefractMethods();

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

            double[] reflectRayStartPoint = reflectMethodsOne.findIntersection(
                    startingPointOfRay,
                    endingPointOfRay,
                    startingPointOfLeftLineOfPrism,
                    endingPointOfLeftLineOfPrism);

            double refractedAngleOne = reflectMethodsOne.FindrefractedAngle(
                    startingPointOfRay, endingPointOfRay,
                    startingPointOfLeftLineOfPrism,
                    endingPointOfLeftLineOfPrism, "Glass");
            
            double[] firstNoramlStartPoint = reflectMethodsOne.calculateNormalStartPoint(
                    startingPointOfLeftLineOfPrism,
                    endingPointOfLeftLineOfPrism,
                    reflectRayStartPoint);
            
            double[] firstNoramlEndPoint = reflectMethodsOne.calculateNormalEndPoint(
                    startingPointOfLeftLineOfPrism,
                    endingPointOfLeftLineOfPrism,
                    reflectRayStartPoint);
            
            double[] firstRefractedRayEndPoint = reflectMethodsOne.calculateRefractRayEndPoint(
                    reflectRayStartPoint, refractedAngleOne,
                    200, firstNoramlStartPoint,
                    firstNoramlEndPoint);

            lightRay.setEndX(reflectRayStartPoint[0]);
            lightRay.setEndY(reflectRayStartPoint[1]);

            //make sure the ray only reflect when touching the prism
            if (reflectRayStartPoint[1] < leftLineOfPrism.getEndY()
                    && reflectRayStartPoint[1] > leftLineOfPrism.getStartY()) {
                //First Reflection
                reflectMethodsOne.reflectThings(startingPointOfRay, 
                        endingPointOfRay,
                        startingPointOfLeftLineOfPrism, 
                        endingPointOfLeftLineOfPrism, lightRay,
                        firstNormal, firstRefractRay, "Glass");
                System.out.println("Yes");

                if (firstRefractedRayEndPoint[1] < rightLineOfPrism.getEndY()
                        && firstRefractedRayEndPoint[1] > rightLineOfPrism.getStartY()) {
                    //Second Reflection
                    reflectMethodsOne.reflectThings(reflectRayStartPoint,
                            firstRefractedRayEndPoint, 
                            startingPointOfRightLineOfPrism,
                            endingPointOfRightLineOfPrism, lightRay, 
                            secondNormal,
                            secondRefractRay, "Air");

                    // Find intersection between ReflectRay and PrismTwoC-D
                    double[] intersectionSecond = reflectMethodsOne.findIntersection(
                            reflectRayStartPoint,
                            firstRefractedRayEndPoint,
                            startingPointOfRightLineOfPrism,
                            endingPointOfRightLineOfPrism);

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

        Scene scene = new Scene(root, 400, 400);
        stage.setTitle("Refraction Game");
        stage.setScene(scene);
        stage.show();
    }
}
