
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Line {

    private double startPointX;
    private double startPointY;
    private double endPointX;
    private double endPointY;
    private double angleXAxis;

    public static double calcAngle() {
        return 0;
    }
}
