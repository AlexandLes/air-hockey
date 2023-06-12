package model;

import javafx.scene.paint.Color;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DividingLine {
    private final double lineY = 0;
    private final Color color = Color.WHITE;
    private final double width = 3;
    private double lineX;
    private double lineY2;

    public DividingLine(double lineX, double lineY2) {
        this.lineX = lineX;
        this.lineY2 = lineY2;
    }
}
