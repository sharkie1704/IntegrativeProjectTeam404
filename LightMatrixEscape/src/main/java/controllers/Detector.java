package controllers;

/**
 *
 * @author Hongyan Li
 */
public class Detector {

    
    public static boolean isIntersecting(double XofStartPointOfRay,
            double YofStartPointOfRay, double XofEndPointOfRay, double YofEndPointOfRay,
            double centerX, double centerY, double radius) {

        // Calculate the slope and intercept of the ray line
        double a = (YofEndPointOfRay - YofStartPointOfRay) / (XofEndPointOfRay - XofStartPointOfRay);
        double b = YofStartPointOfRay - a * XofStartPointOfRay;

        // Calculate the vertical distance from the straight line to the center of the circle
        double distance = Math.abs(a * centerX - centerY + b) / Math.sqrt(a * a + 1);

        // Determine whether the distance from the straight line to the
        //center of the circle is less than or equal to the radius of the circle
        if (distance <= radius) {
            // Determine whether the intersection point is on a line segment
            if (centerX >= Math.min(XofStartPointOfRay, XofEndPointOfRay) && centerX <= Math.max(XofStartPointOfRay, XofEndPointOfRay)
                    && centerY >= Math.min(YofStartPointOfRay, YofEndPointOfRay) && centerY <= Math.max(YofStartPointOfRay, YofEndPointOfRay)) {
                return true;
            }
        }

        return false;
    }
}
