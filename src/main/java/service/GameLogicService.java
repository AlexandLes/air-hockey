package service;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import model.Ball;
import model.Player;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class GameLogicService {
    private final DrawingService drawingService = new DrawingServiceImpl();
    private Player first;
    private Player second;

    public void updateGameState(GraphicsContext gc, Ball ball, Player first, Player second, AtomicBoolean gameStarted) {
        this.second = second;
        this.first = first;
        if (gameStarted.get()) {
            updateBallPosition(ball);
            drawingService.drawBall(gc, ball);
        } else {
            setStartText(gc);
            resetBallPosition(ball, gc);
            resetBallSpeed(ball);
        }
        ensureBallStaysInCanvas(ball, gc);
        checkMissedBall(ball, first, second, gameStarted);
        checkMissedBallBySecondPlayer(gc, ball, first, second, gameStarted);
        increaseBallSpeedAfterCollision(ball, first, second);
        drawScores(gc, first, second);
        drawPlayers(gc, first, second);
    }

    public void updateBallPosition(Ball ball) {
       ball.setBallXPos(ball.getBallXPos() + ball.getBallSpeedX());
       ball.setBallYPos(ball.getBallYPos() + ball.getBallSpeedY());
    }

    public void updateSecondPlayerPosition(Player player) {
        second.setPositionX(player.getPositionX());
        second.setPositionY(player.getPositionY());
    }

    private void setStartText(GraphicsContext gc) {
        gc.setStroke(Color.WHITE);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.strokeText("Click", gc.getCanvas().getWidth() / 2, gc.getCanvas().getHeight() / 2);
    }

    private void resetBallPosition(Ball ball, GraphicsContext gc) {
        ball.setBallXPos(gc.getCanvas().getWidth() / 2);
        ball.setBallYPos(gc.getCanvas().getHeight() / 2);
    }

    private void resetBallSpeed(Ball ball) {
        ball.setBallSpeedX(new Random().nextInt(2) == 0 ? 1 : -1);
        ball.setBallSpeedY(new Random().nextInt(2) == 0 ? 1 : -1);
    }

    private void ensureBallStaysInCanvas(Ball ball, GraphicsContext gc) {
        if (ball.getBallYPos() > gc.getCanvas().getHeight() || ball.getBallYPos() < 0) {
            ball.setBallSpeedY(ball.getBallSpeedY() * (-1));
        }
    }

    private void checkMissedBall(Ball ball, Player first,Player second, AtomicBoolean gameStarted) {
        if (ball.getBallXPos() <= first.getPlayerWidth() - 5) {
            second.setScore(second.getScore() + 1);
            gameStarted.set(false);
        }
    }
    private void checkMissedBallBySecondPlayer(GraphicsContext gc, Ball ball, Player first,Player second, AtomicBoolean gameStarted) {
        if (ball.getBallXPos() > gc.getCanvas().getWidth() - second.getPlayerWidth()) {
            first.setScore(first.getScore() + 1);
            gameStarted.set(false);
        }
    }

    private void increaseBallSpeedAfterCollision(Ball ball, Player first, Player second) {
        double ballRadius = ball.getBallRadius();
        double playerHeight = first.getPlayerHeight();
        double playerWidth = first.getPlayerWidth();
        if (((ball.getBallXPos() + ballRadius > second.getPositionX()) && ball.getBallYPos() >= second.getPositionY() && ball.getBallYPos() <= second.getPositionY() + playerHeight ) ||
                ((ball.getBallXPos() < first.getPositionX() + playerWidth) && ball.getBallYPos() >= first.getPositionY() && ball.getBallYPos() <= first.getPositionY() + playerHeight )) {
            Random random = new Random();
            ball.setBallSpeedX((random.nextInt(3) - 1) * 1.2);  // Випадкове значення від -1 до 1
            ball.setBallSpeedY((random.nextInt(3) - 1) * 1.2);  // Випадкове значення від -1 до 1
        }
    }

    private void drawScores(GraphicsContext gc, Player first, Player second) {
        gc.fillText(first.getScore() + "\t\t\t\t\t\t\t\t" + second.getScore(), gc.getCanvas().getWidth() / 2, 100);
    }

    private void drawPlayers(GraphicsContext gc,Player first, Player second) {
        drawingService.drawPlayer(gc, first);
        drawingService.drawPlayer(gc, second);
    }
}
