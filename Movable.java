import javafx.scene.canvas.GraphicsContext;

/**
 * Created by jakub on 02.09.17.
 */

// an interface for moving objects

public interface Movable {

    void move();
    void rotate(GraphicsContext gc);
    double getX();
    double getY();
    double getRadius();
    boolean isStatic();
    void setVx(double vx);
    void setVy(double vy);
    double getVx();
    double getVy();

}
