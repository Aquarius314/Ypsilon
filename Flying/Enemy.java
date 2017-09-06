package Flying;

import All.Animation;
import All.Missile;
import Environment.GameObject;
import Environment.Planet;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Created by jakub on 05.09.17.
 */
public class Enemy extends Alive {

    private Color color = Color.WHITE;
    protected static Animation anim = new Animation("enemy", 1, 0.15);

    public Enemy(double x, double y, double r) {
        super(x, y, r);
        setWidth(r*2);
        setHeight(r*2);
    }

    @Override
    public double getDamage() {
        return 10;
    }

    @Override
    protected void setAlign() {
        align = 1;
    }

    @Override
    public void kill() {
        isAlive = false;
        color = Color.RED;
    }

    @Override
    public void applyGravity(Planet planet) {

    }

    @Override
    protected void regenerate() {

    }

    @Override
    public void activity() {

    }

    @Override
    public void display(GraphicsContext gc, double time) {

        // missiles
        for(Missile m : missiles) {
            m.display(gc, time);
        }

        // canvas
        for(CanvasElement ce : canvasElements) {
            ce.display(gc, time);
        }

        // enemy itself
//        gc.setFill(Color.WHITE);
        gc.drawImage(anim.getFrame(time), getX() - Player.rx(), getY() - Player.ry());
    }

    @Override
    public void move() {
        super.move();
    }

    @Override
    public boolean isStatic() {
        return false;
    }

    public boolean notAlive() {
        return !isAlive();
    }

    @Override
    public void collideWith(GameObject c) {

    }
}
