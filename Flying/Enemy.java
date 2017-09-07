package Flying;

import All.Animation;
import All.Missile;
import Environment.GameObject;
import Environment.Physics;
import Environment.Planet;
import Environment.World;
import Flying.Weapon.MachineGun;
import Flying.Weapon.Weapon;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Created by jakub on 05.09.17.
 */
public class Enemy extends Alive {

    private Color color = Color.WHITE;
    protected static Animation anim = new Animation("enemy", 9, 0.03);
    private Weapon weapon = new MachineGun(2, this);

    public Enemy(double x, double y, double r) {
        super(x, y, r);
        setWidth(anim.getFrame(0).getWidth());
        setHeight(anim.getFrame(0).getHeight());
//        setWidth(r*2);
//        setHeight(r*2);
        setRadius((getWidth()+getHeight())/4);
        health = 100;
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
        for(Missile m : missiles) {
            Physics.gravityAcceleration(m, planet);
        }
    }

    @Override
    protected void regenerate() {

    }

    @Override
    public void activity() {

        double minDist = 2000;
        boolean chase = false;  // is there a player to chase?
        Player closest = World.players.get(0);
        for(Player p : World.players) {
            double dist = Math.sqrt(Math.pow(p.getX()-getX(),2) + Math.pow(p.getY()-getY(),2));
            if(dist < minDist) {
                minDist = dist;
                closest = p;
                chase = true;
            }
        }
        if(chase) {
            super.activity();
            chase(closest, minDist);
        }

    }

    private void chase(Player p, double minDist) {
            double dx = p.getX()-getX();
            double dy = p.getY()-getY();
            setVx((getVx() + unitSpeed*dx/minDist));
            setVy((getVy() + unitSpeed*dy/minDist));

            if(weapon.isReady()) {
                weapon.use(p.getX(), p.getY());
            }
//            shoot(p.getX(), p.getY());
    }

    public void shoot(double x, double y) {
        double distance = Math.sqrt(Math.pow(getX()-x,2)+Math.pow(getY()-y,2));
        double mvX = (x-getX())/distance;
        double mvY = (y-getY())/distance;
        missiles.add(new Missile(this, mvX, mvY));
    }

//    public void shoot(Player p) {
//        double distance = Math.sqrt(Math.pow(getX()-p.getX(),2)+Math.pow(getY()-p.getY(),2));
//        double mvX = (p.getX()-getX())/distance;
//        double mvY = (p.getY()-getY())/distance;
//        missiles.add(new Missile(getX(), getY(), 4, mvX, mvY, weapon.getDamage()));
//    }

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
        if(World.showColliders) {
            gc.setStroke(Color.WHITE);
            gc.strokeOval(getX() - Player.rx() - getRadius(), getY() - Player.ry() - getRadius(), getRadius()*2, getRadius()*2 );
        }
        gc.save();
        rotate(gc, getX() - Player.rx(), getY() - Player.ry());
        gc.drawImage(anim.getFrame(time), getX()-getRadius() - Player.rx(), getY()-getRadius() - Player.ry());
        gc.restore();
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
        Missile m = (Missile)c;
        applyDamage(m.getDamage());
        m.explode();
    }
}
