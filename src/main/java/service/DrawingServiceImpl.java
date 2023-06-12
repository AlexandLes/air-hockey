package service;

import javafx.scene.canvas.GraphicsContext;
import model.Ball;
import model.DividingLine;
import model.Field;
import model.Player;

public class DrawingServiceImpl implements DrawingService {
    @Override
    public void drawField(GraphicsContext gc, Field field) {
        gc.setFill(field.getColor());
        gc.fillRect(field.getV(), field.getV(), field.getWidth(), field.getHeight());
    }

    @Override
    public void drawBall(GraphicsContext gc, Ball ball) {
        gc.fillOval(ball.getBallXPos(), ball.getBallYPos(), ball.getBallRadius(), ball.getBallRadius());
    }

    @Override
    public void drawLine(GraphicsContext gc, DividingLine line) {
        gc.setLineWidth(line.getWidth());
        gc.setStroke(line.getColor());
        gc.strokeLine(line.getLineX(), line.getLineY(), line.getLineX(), line.getLineY2());
    }

    @Override
    public void drawPlayer(GraphicsContext gc, Player player) {
        gc.fillRect(player.getPositionX(), player.getPositionY(), player.getPlayerWidth(), player.getPlayerHeight());
    }
}
