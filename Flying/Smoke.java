package Flying;

import All.Displayable;
import Environment.GameObject;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Created by jakub on 03.09.17.
 */
public class Smoke extends GameObject {

    private double opacity = 1.0;
    private boolean active = true;

    public Smoke(double x, double y, double r, double vx, double vy) {
        super(x, y, 5);
        setVx(vx*4);
        setVy(vy*4);
    }

    public boolean isInactive() {
        return !active;
    }

    @Override
    public void display(GraphicsContext gc, double time) {

        move();

        Color smokeColor = new Color(0.4, 0.7, 1, opacity);
        gc.setStroke(smokeColor);
        gc.setLineWidth(getRadius()/2);
        opacity -= 0.01;
        if(opacity <= 0) {
            opacity = 0;
            active = false;
        }
        if(active) {
            setRadius(getRadius()*1.02);
            gc.strokeOval(getX()-getRadius() - Player.rx(), getY()-getRadius() - Player.ry(), getRadius()*2, getRadius()*2);
        }
    }

    @Override
    public void collideWith(GameObject c) {
        // pass
    }
}
