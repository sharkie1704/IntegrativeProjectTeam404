
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Mirror extends Line {

    private double length;
    private double width;
    private MirrorType mirrorType;

    public static void changeCoordinates() {

    }
}
