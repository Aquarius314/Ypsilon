import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Created by jakub on 03.09.17.
 */
public class Missile extends GameObject implements Active, RigidBody {

    private long creationTime;
    private double unitSpeed = 10.0;

    public Missile(double x, double y, double r, double vx, double vy) {
        super(x, y, r);
        setVx(vx*unitSpeed);
        setVy(vy*unitSpeed);
        creationTime = System.currentTimeMillis();
    }

    public boolean isInactive() {
        return (System.currentTimeMillis()-creationTime > 2000);
    }

    @Override
    public void move() {
        setX(getX()+getVx());
        setY(getY()+getVy());

    }

    @Override
    public void rotate(GraphicsContext gc) {

    }

    @Override
    public boolean isStatic() {
        return false;
    }

    @Override
    public void display(GraphicsContext gc, double time) {
        gc.setFill(Color.RED);
        gc.fillOval(getX()-getRadius(), getY()-getRadius(), getRadius()*2, getRadius()*2);
    }

    @Override
    public void activity() {
        move();
    }

    @Override
    public void applyGravity(Planet planet) {
        Physics.gravityAcceleration(this, planet);
    }
}
