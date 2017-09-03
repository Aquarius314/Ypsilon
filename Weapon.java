import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Created by jakub on 03.09.17.
 */
public class Weapon extends CanvasElement {

    private double shootingSpeed;
    private double lastShotTime = 0;
    private Player owner;
    private int maxAmmunition = 20;
    private int ammunition = maxAmmunition;

    public Weapon(double s, Player owner) {
        this.shootingSpeed = s;
        this.owner = owner;
    }

    public double getSpeed() {
        return shootingSpeed;
    }

    public boolean isReady() {
        return (System.currentTimeMillis() - lastShotTime > 1000/shootingSpeed);
    }

    public void use(double x, double y) {
        lastShotTime = System.currentTimeMillis();
        if(ammunition > 0) {
            owner.shoot(x, y);
            ammunition--;
        }
    }

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
