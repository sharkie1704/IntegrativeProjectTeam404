package controllers;

import javafx.scene.shape.Line;


/**
 *
 * @author Hongyan Li
 */
public class Wall extends Lines {

//    private double length;
//    private double width;
//    
    
     Reflection reflectionMethod = new Reflection();
     Lines lineMethod = new Lines();
     
     
     public  double[] findIntersectionWithWall(double[] startPointOfRay,
            double[] endPointOfRay, Line lineWall) {

         double[] startPointOfWall=lineMethod.getTheStartPointOfLine(lineWall);
         double[] endPointOfWall=lineMethod.getTheEndPointOfLine(lineWall);

         double [] interesctedPoint=reflectionMethod.findIntersection(
                startPointOfRay, 
                 endPointOfRay, 
                 startPointOfWall, 
                 endPointOfWall);
        
        //Return intersection point
        return interesctedPoint;
    }
     
     
     public  boolean wallTouched(double[] startPointOfRay,
            double[] endPointOfRay, Line lineWall) {
        
         
         double[] startPointOfWall=lineMethod.getTheStartPointOfLine(lineWall);
         double[] endPointOfWall=lineMethod.getTheEndPointOfLine(lineWall);
         
//         System.out.println("startPointOfRay "+startPointOfRay[1]+"endPointOfRay "+endPointOfRay[1]);
//         System.out.println("startPointOfWall "+startPointOfWall[1]+"endPointOfWall "+endPointOfWall[1]);
        double [] interesctedPoint= findIntersectionWithWall(
                startPointOfRay,  endPointOfRay, lineWall);
         System.out.println("interesctedPoint:"+interesctedPoint[0]+" + "+interesctedPoint[1]);
//         System.out.println("interesctedPoint"+interesctedPoint[0]);
        if (interesctedPoint[0] >= Math.min(startPointOfWall[0], endPointOfWall[0])
                &&interesctedPoint[0] <= Math.max(startPointOfWall[0], endPointOfWall[0])
                &&interesctedPoint[1] >= Math.min(startPointOfWall[1], endPointOfWall[1])
                &&interesctedPoint[1] <= Math.max(startPointOfWall[1], endPointOfWall[1])){
            return true;
        }
         return false;
         
     }
    
    
    
    
}


