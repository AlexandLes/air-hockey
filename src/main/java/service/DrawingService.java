package service;

import javafx.scene.canvas.GraphicsContext;
import model.Ball;
import model.DividingLine;
import model.Field;
import model.Player;

public interface DrawingService {
    void drawField(GraphicsContext gc, Field field);
    void drawBall(GraphicsContext gc, Ball ball);

   void drawLine(GraphicsContext gc, DividingLine line);



    void drawPlayer(GraphicsContext gc, Player player);
}
