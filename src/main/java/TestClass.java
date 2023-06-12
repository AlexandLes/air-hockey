
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Ball;
import model.DividingLine;
import model.Field;
import model.Player;
import client.GameClient;
import server.GameServer;
import service.DrawingService;
import service.DrawingServiceImpl;
import service.GameLogicService;
import service.MouseEventService;
import java.util.concurrent.atomic.AtomicBoolean;

public class TestClass extends Application {
    private static final int width = 800;
    private static final int height = 600;
    private static final int PLAYER_HEIGHT = 50;
    private static final int PLAYER_WIDTH = 15;
    private double playerOneYPos = height / 2;
    private double playerTwoYPos = height / 2;
    private double ballXPos = width / 2;
    private double ballYPos = height / 2;
    private int scoreP1 = 0;
    private int scoreP2 = 0;
    private AtomicBoolean gameStarted = new AtomicBoolean(false);
    private int playerOneXPos = 0;
    private double playerTwoXPos = width - PLAYER_WIDTH;
    private DrawingService drawingService;
    private GameLogicService gameLogicService;
    private MouseEventService mouseEventService;
    private GameClient gameClient;
    private GameServer gameServer;

    public void start(Stage stage) throws Exception {
        stage.setTitle("Air hockey");
        //background size
        Canvas canvas = new Canvas(width, height);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Ball ball = new Ball(ballYPos,ballXPos, 2, 2);
        Field field = new Field(width, height);
        DividingLine dividingLine = new DividingLine(gc.getCanvas().getWidth() / 2,
                gc.getCanvas().getHeight());
        Player first = new Player(playerOneYPos, playerOneXPos, PLAYER_HEIGHT, PLAYER_WIDTH, scoreP1);
        first.setPlayerId(1);
        Player second = new Player(playerTwoYPos, playerTwoXPos, PLAYER_HEIGHT, PLAYER_WIDTH, scoreP2);
        second.setPlayerId(2);
        drawingService = new DrawingServiceImpl();
        gameLogicService = new GameLogicService();
        mouseEventService = new MouseEventService();
        Thread serverThread = new Thread(() -> {
            try {
                gameServer = GameServer.getInstance();
                gameServer.run();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        serverThread.setPriority(10);
        serverThread.start();

        Thread clientThread = new Thread(() -> {
            try {
                gameClient = new GameClient("127.0.0.1", 8000);
                gameClient.run();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        clientThread.setPriority(9);
        clientThread.start();
        //JavaFX Timeline = free form animation defined by KeyFrames and their duration
        Timeline tl = new Timeline(new KeyFrame(Duration.millis(10), e -> run(gc,ball,field,dividingLine,first,second)));
        //number of cycles in animation INDEFINITE = repeat indefinitely
        tl.setCycleCount(Timeline.INDEFINITE);

        //mouse control (move and click)
        canvas.setOnMouseMoved(e -> mouseEventService.calculatePlayerPosition(gc, e, first));
        canvas.setOnMouseClicked(e ->  gameStarted.set(true));
        stage.setScene(new Scene(new StackPane(canvas)));
        stage.show();
        tl.play();
    }

    private void run(GraphicsContext gc,Ball ball, Field field, DividingLine dividingLine, Player first, Player second) {
        drawingService.drawField(gc, field);
            drawingService.drawLine(gc, dividingLine);
            //set text
            gc.setFill(Color.WHITE);
            gc.setFont(Font.font(25));
            gameLogicService.updateGameState(gc, ball, first, second, gameStarted);
            if (gameClient.getGameClientHandler() != null) {
                gameClient.getGameClientHandler().sendPlayerMovement(second);
                gameClient.getGameClientHandler().sendPlayerMovement(first);
                gameClient.getGameClientHandler().sendBallMovement(ball);
            }
    }

    // start the application
    public static void main(String[] args) {
        launch(args);
    }
}
