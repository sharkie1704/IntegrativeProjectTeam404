
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class mainClass extends Application {

    @Override
    public void start(Stage stage) {
//        String javaVersion = System.getProperty("java.version");
//        String javafxVersion = System.getProperty("javafx.version");
//        Label l = new Label("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");
//        Scene scene = new Scene(new StackPane(l), 640, 480);
//        stage.setScene(scene);
//        stage.show();
//        

        Pane root = new Pane();

        // create ray
        Line lightRay = new Line(50, 25 + 100 * Math.sqrt(3), 50, 25 + 100 * Math.sqrt(3));
        lightRay.setStroke(Color.BLACK);
        root.getChildren().add(lightRay);

        // create wall
        Line PrismOne = new Line(200, 25, 100, 25 + 100 * Math.sqrt(3)); //  /  
        PrismOne.setStroke(Color.GRAY);

        root.getChildren().add(PrismOne);

        Line PrismTwo = new Line(200, 25, 300, 25 + 100 * Math.sqrt(3));  // \  
        PrismTwo.setStroke(Color.GRAY);
        root.getChildren().add(PrismTwo);

        Line PrismThree = new Line(100, 25 + 100 * Math.sqrt(3), 300, 25 + 100 * Math.sqrt(3));  // _  
        PrismThree.setStroke(Color.GRAY);
        root.getChildren().add(PrismThree);

        Line RefractRayOne = new Line(200, 50, 200, 350);
        RefractRayOne.setStroke(Color.TRANSPARENT);
        root.getChildren().addAll(RefractRayOne);

        Line RefractRayTwo = new Line(200, 50, 200, 350);
        RefractRayTwo.setStroke(Color.TRANSPARENT);
        root.getChildren().addAll(RefractRayTwo);

        Line NormalOne = new Line(50, 200, 50, 200); //random place
        NormalOne.setStroke(Color.TRANSPARENT);
        root.getChildren().add(NormalOne);

        Line NormalTwo = new Line(50, 200, 50, 200); //random place
        NormalTwo.setStroke(Color.TRANSPARENT);
        root.getChildren().add(NormalTwo);

        //Click the mouse to change the incident light and Refracted light
        root.setOnMouseClicked(event -> {

            RefractMethods ReflectMethodsOne = new RefractMethods();

            double[] A = {50, 200}; // The starting point of line segment AB (ray
            double[] B = {event.getX(), event.getY()}; // The end point of line segment AB (ray
            double[] PrismOneC = {PrismOne.getStartX(), PrismOne.getStartY()}; // The starting point of /
            double[] PrismOneD = {PrismOne.getEndX(), PrismOne.getEndY()}; // The end point of line /
            double[] PrismTwoC = {PrismTwo.getStartX(), PrismTwo.getStartY()}; // The starting point of line \
            double[] PrismTwoD = {PrismTwo.getEndX(), PrismTwo.getEndY()}; // The end point of line \

            double[] ReflectRayStartPoint = ReflectMethodsOne.findIntersection(A, B, PrismOneC, PrismOneD);
            double refractedAngleOne = ReflectMethodsOne.FindrefractedAngle(A, B, PrismOneC, PrismOneD, "Glass");
            double[] NoramlOneStartPoint = ReflectMethodsOne.calculateNormalStartPoint(PrismOneC, PrismOneD, ReflectRayStartPoint);
            double[] NoramlOneEndPoint = ReflectMethodsOne.calculateNormalEndPoint(PrismOneC, PrismOneD, ReflectRayStartPoint);
            double[] RefractedRayOneEndPoint = ReflectMethodsOne.calculateRefractRayEndPoint(ReflectRayStartPoint, refractedAngleOne, 200, NoramlOneStartPoint, NoramlOneEndPoint);

            lightRay.setEndX(ReflectRayStartPoint[0]);
            lightRay.setEndY(ReflectRayStartPoint[1]);
            if (ReflectRayStartPoint[1] < PrismOne.getEndY() && ReflectRayStartPoint[1] > PrismOne.getStartY()) {
                //First Reflection
                ReflectMethodsOne.reflectThings(A, B, PrismOneC, PrismOneD, lightRay, NormalOne, RefractRayOne, "Glass");
                System.out.println("Yes");

                if (RefractedRayOneEndPoint[1] < PrismTwo.getEndY() && RefractedRayOneEndPoint[1] > PrismTwo.getStartY()) {
                    //Second Reflection
                    ReflectMethodsOne.reflectThings(ReflectRayStartPoint, RefractedRayOneEndPoint, PrismTwoC, PrismTwoD, lightRay, NormalTwo, RefractRayTwo, "Air");

                    // Find intersection between ReflectRay and PrismTwoC-D
                    double[] intersectionTwo = ReflectMethodsOne.findIntersection(ReflectRayStartPoint, RefractedRayOneEndPoint, PrismTwoC, PrismTwoD);

                    RefractRayOne.setEndX(intersectionTwo[0]);
                    RefractRayOne.setEndY(intersectionTwo[1]);
                } else { //if the lightTwo in wrong place
                    RefractRayTwo.setStroke(Color.TRANSPARENT);
                    NormalTwo.setStroke(Color.TRANSPARENT);
                }
            } //if the lightOne in wrong place
            else {
                lightRay.setEndX(event.getX());
                lightRay.setEndY(event.getY());
                RefractRayOne.setStroke(Color.TRANSPARENT);
                RefractRayTwo.setStroke(Color.TRANSPARENT);
                NormalOne.setStroke(Color.TRANSPARENT);
                NormalTwo.setStroke(Color.TRANSPARENT);
//                System.out.println("No");
            }

        });

        Scene scene = new Scene(root, 400, 400);
        stage.setTitle("Refraction Game");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
