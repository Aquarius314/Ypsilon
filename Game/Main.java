package Game; /**
 * Created by jakub on 02.09.17.
 */

import All.*;
import Environment.GameObject;
import Environment.Planet;
import Environment.World;
import Flying.Enemy;
import Flying.Player;
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
        stage.setTitle("Y P S I L O N");

        Group root = new Group();
        Scene scene = new Scene(root);
        stage.setScene(scene);

        final long startNanoTime = System.nanoTime();

        int worldWidth = 1024, worldHeight = 682;

        world = new World(worldWidth, worldHeight);

        canvas = new Canvas(worldWidth, worldHeight);
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

        collisions();

        gravity();
        // clearing planets
        World.planets.removeIf(Planet::notAlive);
        // clearing enemies
        World.enemies.removeIf(Enemy::notAlive);
    }

    private void collisions() {
        // spaceships vs missiles
        for(GameObject aliveCollider : world.getAliveColliders()) {
            for(GameObject collider : world.getColliders()) {
                aliveCollider.checkCollision(collider);
            }
        }
    }

    private void gravity() {
        for(Player plyr : world.players) {
            for(Planet plnt : world.planets) {
                plyr.applyGravity(plnt);
            }
        }
        for(Enemy e : world.enemies) {
            for(Planet plnt : world.planets) {
                e.applyGravity(plnt);
            }
        }
    }

    private void render(double time) {
        for(Displayable d : world.getDisplayables()) {
            d.display(gc, time);
        }
    }
}
