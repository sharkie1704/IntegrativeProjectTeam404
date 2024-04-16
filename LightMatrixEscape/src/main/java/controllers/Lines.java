package controllers;
//
//import lombok.Getter;
//import lombok.Setter;

import javafx.scene.shape.Line;

//@Getter
//@Setter
/**
 *
 * @author Hongyan Li
 */
public class Lines {

    public double[] getTheStartPointOfLine(Line line) {
        double[] StartPoint = {line.getStartX(), line.getStartY()};
        return StartPoint;
    }

    public double[] getTheEndPointOfLine(Line line) {
        double[] EndPoint = {line.getEndX(), line.getEndY()};
        return EndPoint;
    }
}
