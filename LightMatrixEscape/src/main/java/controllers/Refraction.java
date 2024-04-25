package controllers;

/**
 *
 * @author Hongyan Li
 */
import java.util.Arrays;
import javafx.scene.shape.Line;
import javafx.scene.paint.Color;

public class Refraction {

    public static void refractThings(double[] startPointOfRay, double[] endPointOfRay,
            double[] startPointOfPrism, double[] endPointOfPrism, Line lightRay,
            Line normal, Line refractRay, String Type) {

        //Update the end point of the ray
        double[] intersection = findIntersection(startPointOfRay, endPointOfRay,
                startPointOfPrism, endPointOfPrism);

        
        //Set normal's properties
        double[] normalOneStartPoint = calculateNormalStartPoint(startPointOfPrism,
                endPointOfPrism, intersection);
        double[] noramlOneEndPoint = calculateNormalEndPoint(startPointOfPrism,
                endPointOfPrism, intersection); //FInd the Normal endpoint      
        normal.setStartX(normalOneStartPoint[0]);
        normal.setStartY(normalOneStartPoint[1]);
        normal.setEndX(noramlOneEndPoint[0]);
        normal.setEndY(noramlOneEndPoint[1]);
//        normal.setStroke(Color.DARKSALMON);

        //Calculate the refraction angle of light
        double refractedAngleOne = FindrefractedAngle(startPointOfRay, endPointOfRay,
                startPointOfPrism, endPointOfPrism, Type);
//        System.out.println("refractedAngleOne"+refractedAngleOne);
//        System.out.println("startPointOfRay"+startPointOfRay[1]+" ?"+endPointOfRay[1]+" ? "+startPointOfPrism[1]+" ? "+endPointOfPrism[1]+" ? ");
        //Calculate the end point coordinates of the refracted light, assuming the length is 1200
        double[] refractedRayOneEndPoint = calculateRefractRayEndPoint(intersection,
                refractedAngleOne, 1200, normalOneStartPoint, noramlOneEndPoint);
        double refractedRayOneEndX = refractedRayOneEndPoint[0];
        double refractedRayOneEndY = refractedRayOneEndPoint[1];

        //Set RefractRayOne properties
        refractRay.setStartX(intersection[0]);
        refractRay.setStartY(intersection[1]);
        refractRay.setEndX(refractedRayOneEndX);
        refractRay.setEndY(refractedRayOneEndY);
        refractRay.setStroke(Color.YELLOW);
//        System.out.println("refractRay "+intersection[0]+" ? "+refractedRayOneEndX);

//        System.out.println(Type + Arrays.toString(intersection));
//System.out.println("refractRay="+intersection[1]);
    }

    public static double findIncidentAngle(double[] startingCoordinateOfRayA,
            double[] endingCoordinateOfRayB, double[] startingCoordinateOfWallC,
            double[] endingCoordinateOfWallD) {
        //A and B are the starting and ending coordinates of the ray
        //C and D are the starting and ending coordinates of the wall

        //Calculate vectors AB and CD
        double[] ab = {endingCoordinateOfRayB[0] - startingCoordinateOfRayA[0],
            endingCoordinateOfRayB[1] - startingCoordinateOfRayA[1]};
        double[] cd = {endingCoordinateOfWallD[0] - startingCoordinateOfWallC[0],
            endingCoordinateOfWallD[1] - startingCoordinateOfWallC[1]};

        //Calculate the dot product of vectors AB and CD
        double dotProduct = ab[0] * cd[0] + ab[1] * cd[1];

        //Calculate the modulus of vectors AB and CD
        double magnitudeAB = Math.sqrt(ab[0] * ab[0] + ab[1] * ab[1]);
        double magnitudeCD = Math.sqrt(cd[0] * cd[0] + cd[1] * cd[1]);

        //Calculate the angle
        double cosTheta = dotProduct / (magnitudeAB * magnitudeCD);
        double angleRadians = Math.acos(cosTheta);
        double angleDegrees = Math.toDegrees(angleRadians);

        //Return angle
        return angleDegrees;
    }

    public static double FindrefractedAngle(double[] startPointOfRayA,
            double[] endPointOfRayB, double[] startPointOfPrismC,
            double[] endPointOfPrismD, String Type) {
        //Calculate the incident angle of light
        double incidentAngle = Math.toRadians(findIncidentAngle(startPointOfRayA,
                endPointOfRayB, startPointOfPrismC,
                endPointOfPrismD) - 90);
        double refractedAngle = 0;

        //Calculate the Refraction angle of light
        if (Type.equals("Glass")) {
            refractedAngle = -Math.toDegrees(Math.asin(Math.sin(incidentAngle) / 1.33));

            
        } else if (Type.equals("Air")) {
            refractedAngle = -Math.toDegrees(Math.asin(Math.sin(incidentAngle) * 1.33));

        } else {
            refractedAngle = 0;
        }
        return refractedAngle;
    }

    public static double[] findIntersection(double[] startingCoordinateOfFirstLineA,
            double[] endingCoordinateOfFirstLineB, double[] startingCoordinateOfSecondLineC,
            double[] endingCoordinateOfSecondLineD) {

        //Calculate vectors ab,cd and ac
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

        //Calculate intersection coordinates
        double intersectionX = startingCoordinateOfFirstLineA[0] + t1 * ab[0];
        double intersectionY = startingCoordinateOfFirstLineA[1] + t1 * ab[1];
        double[] intersectionPoint = {intersectionX, intersectionY};

        //Return intersection point
        return intersectionPoint;
    }

    public static double[] calculateRefractRayEndPoint(double[] intersection,
            double angle, double lengthOfTheRefractedLight,
            double[] startingCoordinateOfWallA, double[] endingCoordinateOfWallB) {
        //A and B are the starting and ending coordinates of the wall
        //intersection is the starting point coordinate of the Refracted light, 
        //which is the point where it intersects with AB
        //angle is the angle between the Refracted light and the wall
        //lengthOfTheRefractedLight is the length of the Refracted light

        //Calculate the length and direction vector of line segment AB
        double lengthAB = Math.sqrt(Math.pow(endingCoordinateOfWallB[0]
                - startingCoordinateOfWallA[0], 2)
                + Math.pow(endingCoordinateOfWallB[1] - startingCoordinateOfWallA[1], 2));
        double[] abVector = {(endingCoordinateOfWallB[0] - startingCoordinateOfWallA[0]) / lengthAB,
            (endingCoordinateOfWallB[1] - startingCoordinateOfWallA[1]) / lengthAB};
//        System.out.println("???"+abVector[0]+abVector[1]);
        //Convert angle to radians
        double angleRadians = Math.toRadians(angle);
        //Calculate the direction vector of line segment CD
        double[] cdVector = {
            Math.cos(angleRadians) * abVector[0] - Math.sin(angleRadians) * abVector[1],
            Math.sin(angleRadians) * abVector[0] + Math.cos(angleRadians) * abVector[1]
        };

        //Calculate the end point coordinates of line segment CD
        double[] RefractRayEndPoint = {intersection[0] + lengthOfTheRefractedLight * cdVector[0],
            intersection[1] + lengthOfTheRefractedLight * cdVector[1]};

        return RefractRayEndPoint;
    }

    public static double[] calculateNormalStartPoint(double[] startingCoordinateOfWallA,
            double[] endingCoordinateOfWallB, double[] intersection) {

        //Assume the length of normal is 200
        double lengthOfNormal = 200;

        //Calculate the unit vector of line segment AB
        double[] abVector = {(endingCoordinateOfWallB[0] - startingCoordinateOfWallA[0]),
            (endingCoordinateOfWallB[1] - startingCoordinateOfWallA[1])};
        double lengthAB = Math.sqrt(abVector[0] * abVector[0] + abVector[1] * abVector[1]);
        abVector[0] /= lengthAB;
        abVector[1] /= lengthAB;

        //Calculate vertical vector
        double[] perpendicularVector = {-abVector[1], abVector[0]};

        //Calculate Start Point of mormal
        double[] startPointOfNormal = {intersection[0] + 0.5 * lengthOfNormal * perpendicularVector[0],
            intersection[1] + 0.5 * lengthOfNormal * perpendicularVector[1]};

        return startPointOfNormal;
    }

    public static double[] calculateNormalEndPoint(double[] startingCoordinateOfWallA,
            double[] endingCoordinateOfWallB, double[] intersection) {

        //Assume the length of normal is 200
        double lengthOfNormal = 200;

        //Calculate the unit vector of line segment AB
        double[] abVector = {(endingCoordinateOfWallB[0] - startingCoordinateOfWallA[0]),
            (endingCoordinateOfWallB[1] - startingCoordinateOfWallA[1])};
        double lengthAB = Math.sqrt(abVector[0] * abVector[0] + abVector[1] * abVector[1]);
        abVector[0] /= lengthAB;
        abVector[1] /= lengthAB;

        //Calculate vertical vector
        double[] perpendicularVector = {-abVector[1], abVector[0]};

        //Calculate normal end point
        double[] endPointOfNormal = {intersection[0] - 0.5 * lengthOfNormal * perpendicularVector[0], intersection[1] - 0.5 * lengthOfNormal * perpendicularVector[1]};
        return endPointOfNormal;
    }
}


