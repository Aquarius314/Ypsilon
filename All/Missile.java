package All;

import Environment.*;
import Flying.Alive;
import Flying.Player;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.Random;

/**
 * Created by jakub on 03.09.17.
 */
public class Missile extends GameObject implements Active, RigidBody {


    private long creationTime;
    private double unitSpeed = 20.0;
    private long lifeTime = 4000;
    private double dmg;
    private boolean isActive = true;
    private static Image bulletBlue, bulletRed;

    static {
        bulletBlue = new Image("assets/bullet_blue.png");
        bulletRed = new Image("assets/bullet_red.png");
    }

//    public Missile(double x, double y, double r, double vx, double vy, double dmg) {
//        super(x, y, r);
//        setVx(vx*unitSpeed);
//        setVy(vy*unitSpeed);
//        move();move();move();   // so it doesnt start from middle of player
//        creationTime = System.currentTimeMillis();
//        this.dmg = dmg;
//    }

    public Missile(Alive parent, double vx, double vy) {
        super(parent.getX(), parent.getY(), parent.getRadius());
        setVx(vx*unitSpeed);
        setVy(vy*unitSpeed);
        creationTime = System.currentTimeMillis();
        this.dmg = parent.getDamage();
        this.align = parent.getAlign();
    }

    public double getDamage() {
        return dmg;
    }

    public boolean isInactive() {
        return !isActive || (System.currentTimeMillis()-creationTime > lifeTime);
    }

    @Override
    public boolean isStatic() {
        return staticPosition;
    }

    @Override
    public void display(GraphicsContext gc, double time) {
//        gc.setFill(Color.RED);
        gc.save();
        rotate(gc, getX()+getWidth()/2 - Player.rx(), getY()+getHeight()/2 - Player.ry());
//        rotate(gc, getX(), getY());
        gc.drawImage(align==0 ? bulletBlue : bulletRed, getX()-getRadius() - Player.rx(), getY()-getRadius() - Player.ry());
        gc.restore();
    }

    @Override
    public void activity() {
        move();
        if(staticPosition) {
            explode();
        }
    }

    public void explode() {
        isActive = false;
        String name = align==0 ? "explosion/blue" : "explosion/red";
        World.animationsPanel.addAnimation(new LoneAnimation(this, name, 17, 0.015));
    }

    @Override
    public void applyGravity(Planet planet) {
        Physics.gravityAcceleration(this, planet);
    }

    @Override
    public void collideWith(GameObject c) {
        System.out.println("Collided with " + c.getClass());
    }
}
