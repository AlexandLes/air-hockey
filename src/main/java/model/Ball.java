package model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Ball {
    private final double ballRadius = 15;
    private double ballSpeedX;
    private double ballSpeedY;
    private double ballYPos;
    private double ballXPos;

    public Ball(double ballYPos, double ballXPos, double ballSpeedX, double ballSpeedY) {
        this.ballYPos = ballYPos;
        this.ballXPos = ballXPos;
        this.ballSpeedX = ballSpeedX;
        this.ballSpeedY = ballSpeedY;
    }
}
