/**
 * Created by jakub on 02.09.17.
 */

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

public class Main extends Application {

    GraphicsContext gc;
    Canvas canvas;
    World world;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("JavaFX Game");

        Group root = new Group();
        Scene scene = new Scene(root);
        stage.setScene(scene);

        final long startNanoTime = System.nanoTime();

        world = new World();

        canvas = new Canvas(1024, 1024);
        root.getChildren().add(canvas);

        gc = canvas.getGraphicsContext2D();

        new AnimationTimer()
        {
            double lastIteration = 0.0;
            double step = 0.015;
            public void handle(long currentNanoTime)
            {
                double t = (currentNanoTime - startNanoTime) / 1000000000.0;

                // calculate using t
                if(t - lastIteration > step) {
                    lastIteration = t;
                    gameLoop(t);
                    render(t);
                }
            }
        }.start();

        KeyboardController keyboardController = new KeyboardController(scene);
        MouseController mouseController = new MouseController(scene);

        stage.show();
    }

    private void gameLoop(double time) {
        for(Active a : world.getActives())
            a.activity();

        gravity();

    }

    private void gravity() {
        for(Player plyr : world.players) {
            for(Planet plnt : world.planets) {
                plyr.applyGravity(plnt);
            }
        }
    }

    private void checkCollisions() {

    }

    private void render(double time) {
        for(Displayable d : world.getDisplayables()) {
            d.display(gc, time);
        }
    }
}
