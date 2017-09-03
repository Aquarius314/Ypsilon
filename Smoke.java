import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Created by jakub on 03.09.17.
 */
public class Smoke extends GameObject implements Displayable {

    private long creationTime;
    private double opacity = 1.0;
    private boolean active = true;
    private double vx, vy;

    public Smoke(double x, double y, double r, double vx, double vy) {
        super(x, y, 5);
        creationTime = System.currentTimeMillis();
        this.vx = vx;
        this.vy = vy;
    }

    public boolean isInactive() {
        return !active;
    }

    private void move() {
        setX(getX()+vx);
        setY(getY()+vy);
    }

    @Override
    public void display(GraphicsContext gc, double time) {

        move();

        gc.setStroke(new Color(0.4, 0.2, 0.9, opacity));
        gc.setLineWidth(getRadius()/2);
        opacity -= 0.01;
        if(opacity <= 0) {
            opacity = 0;
            active = false;
        }
        setRadius(getRadius()*1.02);
        gc.strokeOval(getX()-getRadius(), getY()-getRadius(), getRadius()*2, getRadius()*2);
    }
}
