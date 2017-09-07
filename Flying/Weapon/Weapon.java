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
    protected int maxAmmunition = 20;
    protected int ammunition = maxAmmunition;
    protected double damage;

    public Weapon(double s, Alive owner) {
        this.shootingSpeed = s;
        this.owner = owner;
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
        gc.setFill(Color.RED);
        for(int i = 0; i < ammunition; i++) {
            gc.fillRect(i*5+10, 30, 4, 10);
        }
        gc.setStroke(Color.RED);
        for(int i = 0; i < maxAmmunition; i++) {
            gc.strokeRect(i*5+10, 30, 4, 10);
        }
    }
}
