package model;

import javafx.scene.paint.Color;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Field {
    private final double v = 0;
    private final Color color = Color.BLACK;
    private double width;
    private double height;

    public Field(double width, double height) {
        this.width = width;
        this.height = height;
    }
}
