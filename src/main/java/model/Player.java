package model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Player {
    private int playerId;
    private double positionY;
    private double positionX;
    private double playerHeight;
    private double playerWidth;
    private double score;

    public Player(double positionY, double positionX, double playerHeight, double playerWidth, double score) {
        this.positionY = positionY;
        this.positionX = positionX;
        this.playerHeight = playerHeight;
        this.playerWidth = playerWidth;
        this.score = score;
    }
}
