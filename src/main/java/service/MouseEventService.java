package service;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import model.Player;

public class MouseEventService {
    public void calculatePlayerPosition(GraphicsContext gc, MouseEvent e, Player first) {
        double minX = first.getPlayerWidth() / 2;
        double minY = first.getPlayerHeight() / 2;
        double maxX = gc.getCanvas().getWidth() - first.getPlayerWidth() / 2;
        double maxY = gc.getCanvas().getHeight() - first.getPlayerHeight() / 2;
        double x = e.getX();
        double y = e.getY();

        // Restrict the x-coordinate within the entire field
        if (x < minX) {
            x = minX;
        } else if (x > maxX) {
            x = maxX;
        }

        // Restrict the y-coordinate within the entire field
        if (y < minY) {
            y = minY;
        } else if (y > maxY) {
            y = maxY;
        }

        first.setPositionX(x);
        first.setPositionY(y);
    }
}
