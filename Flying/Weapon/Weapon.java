package Flying.Weapon;

import Flying.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Created by jakub on 03.09.17.
 */
public abstract class Weapon extends CanvasElement {

    protected double shootingSpeed;
    protected double lastUseTime = 0;
    protected Alive owner;
    protected int maxAmmunition = 100;
    protected int ammunition = maxAmmunition;
    protected double damage;

    public Weapon(double s, Alive owner) {
        this.shootingSpeed = s;
        this.owner = owner;
        x = 10;
        y = 30;
        w = 4;
        h = 10;
        strokeColor = Color.RED;
        fillColor = Color.RED;
    }

    public double getDamage() {
        return damage;
    }

    public double getSpeed() {
        return shootingSpeed;
    }

    public boolean isReady() {
        return (System.currentTimeMillis() - lastUseTime > 1000/shootingSpeed);
    }

    public abstract void use(double x, double y);

    @Override
    public void display(GraphicsContext gc, double time) {
        gc.setFill(fillColor);
        for(int i = 0; i < ammunition; i++) {
            gc.fillRect(i*5+x, y, w, h);
        }
        gc.setStroke(strokeColor);
        for(int i = 0; i < maxAmmunition; i++) {
            gc.strokeRect(i*5+x, y, w, h);
        }
    }
}
