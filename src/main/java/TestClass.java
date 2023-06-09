import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class TestClass extends Application {
    private static final int GOAL_WIDTH = 200;
    private static final int GOAL_HEIGHT = 100;
    private static final int PADDLE_WIDTH = 100;
    private static final int PADDLE_HEIGHT = 20;
    private GraphicsContext gc;

    private double paddle1Y;
    private double paddle1X;
    private double paddle2Y;
    private double paddle2X;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Canvas canvas = new Canvas();
        StackPane root = new StackPane(canvas);
        root.setPadding(new Insets(10));

        Scene scene = new Scene(root, Color.WHITE);
        stage.setScene(scene);
        stage.setTitle("Resizable Movable Paddles Air Hockey Field");
        stage.show();

        // Bind canvas dimensions to the scene's width and height
        canvas.widthProperty().bind(scene.widthProperty());
        canvas.heightProperty().bind(scene.heightProperty());

        gc = canvas.getGraphicsContext2D();
        canvas.setOnMouseDragged(this::handleMouseDragged);
        scene.widthProperty().addListener((obs, oldWidth, newWidth) -> drawField(gc));
        scene.heightProperty().addListener((obs, oldHeight, newHeight) -> drawField(gc));
        drawField(gc);
    }

    private void handleMouseDragged(MouseEvent event) {
        double mouseX = event.getX();
        double mouseY = event.getY();

        double canvasWidth = gc.getCanvas().getWidth();
        double canvasHeight = gc.getCanvas().getHeight();

        // Update paddle positions based on mouse movement
        if (mouseX <= canvasWidth / 2) {
            paddle1Y = Math.max(0, Math.min(mouseY - PADDLE_HEIGHT / 2, canvasHeight - PADDLE_HEIGHT));
        } else {
            paddle2Y = Math.max(0, Math.min(mouseY - PADDLE_HEIGHT / 2, canvasHeight - PADDLE_HEIGHT));
        }

        // Allow horizontal movement for each paddle
        paddle1X = Math.max(0, Math.min(mouseX - PADDLE_WIDTH / 2, canvasWidth / 2 - PADDLE_WIDTH));
        paddle2X = Math.max(canvasWidth / 2, Math.min(mouseX - PADDLE_WIDTH / 2, canvasWidth - PADDLE_WIDTH));

        // Redraw the field
        drawField(gc);
    }

    private void drawField(GraphicsContext gc) {
        double canvasWidth = gc.getCanvas().getWidth();
        double canvasHeight = gc.getCanvas().getHeight();

        // Clear canvas
        gc.clearRect(0, 0, canvasWidth, canvasHeight);

        // Draw boundaries Boundary(Color color, int width, strokeV, strokeV2)
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(5);
        gc.strokeRect(0, 0, canvasWidth, canvasHeight);


// DividingLine(int X, int startY, int endY, int lineWidth, Color color)
        double lineX = canvasWidth / 2;
        double lineY1 = 0;
        double lineY2 = canvasHeight;
        gc.setLineWidth(3);
        gc.setStroke(Color.BLACK);
        gc.strokeLine(lineX, lineY1, lineX, lineY2);

        // Draw goals Goal(int goalY, Color color, int width, int height)
        int goalY = (int) ((canvasHeight - GOAL_HEIGHT) / 2);
        gc.setFill(Color.RED);
        gc.fillRect(0, goalY, GOAL_WIDTH, GOAL_HEIGHT);
        gc.setFill(Color.BLUE);
        gc.fillRect(canvasWidth - GOAL_WIDTH, goalY, GOAL_WIDTH, GOAL_HEIGHT);

        // Draw paddles (Color color, paddleY, paddleX)
        int paddleY1 = (int) paddle1Y;
        int paddleY2 = (int) paddle2Y;
        gc.setFill(Color.GREEN);
        gc.fillRect(paddle1X, paddleY1, PADDLE_WIDTH, PADDLE_HEIGHT);
        gc.setFill(Color.YELLOW);
        gc.fillRect(paddle2X, paddleY2, PADDLE_WIDTH, PADDLE_HEIGHT);
    }
}
