package controllers;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Mirror extends Lines {

    private double length;
    private double width;
    private MirrorType mirrorType;

    public static void changeCoordinates() {

    }
}
