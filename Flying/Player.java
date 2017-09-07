package Flying;

import All.Animation;
import All.Missile;
import Environment.*;
import Flying.Weapon.RotationGun;
import Game.KeyboardController;
import Game.MouseController;
import Map.*;
import Flying.Weapon.MachineGun;
import Flying.Weapon.Weapon;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;


/**
 * Created by jakub on 02.09.17.
 */
public class Player extends Alive {

//    private double vx = 0, vy = 0;
    public static double X, Y;

    private static boolean DISPLAY_IN_CENTER = true;
    public static boolean INFINITE_AMMUNITION = false;
    private long INFO_TIME = 0, INFO_STEP = 1000;

    private double energyRegenerationRate = 0.02d;

    public Fuel fuel;
    private Weapon weapon;
    private MiniMap minimap;
    private FullMap fullmap;
    private HealthBar healthBar;
    private long creationTime = System.currentTimeMillis();

    protected static Animation anim = new Animation("player", 8, 0.03);

    public static int rx() {
        // GET RELATIVE X

        return DISPLAY_IN_CENTER ? (int)(X - World.width/2) : 0;
    }

    public static int ry() {
        // GET RELATIVE Y
        return DISPLAY_IN_CENTER ? (int)(Y - World.height/2) : 0;
    }

    public Player(double x, double y, double r) {
        super(x, y, r);
        double l = 10;   // radius, width and height smaller on purpose.
        setWidth(anim.getFrame(0).getWidth());
        setHeight(anim.getFrame(0).getHeight());
        setRadius((getWidth()+getHeight())/4);
        setVx(unitSpeed);
        fuel = new Fuel();
        weapon = new RotationGun(5, this);
        minimap = new MiniMap(this);
        fullmap = new FullMap(this);
        healthBar = new HealthBar(this);
        canvasElements.add(fuel);
        canvasElements.add(weapon);
        canvasElements.add(minimap);
        canvasElements.add(fullmap);
        canvasElements.add(healthBar);
        maxHealth = 200;
        health = maxHealth;
    }

    @Override
    protected void setAlign() {
        align = 0;
    }

    @Override
    public void kill() {
        isAlive = false;
    }

    @Override
    public void display(GraphicsContext gc, double time) {
        // collider
        if(World.showColliders) {
            gc.setStroke(Color.CYAN);
            gc.strokeOval(World.width/2-getRadius(), World.height/2-getRadius(), getRadius()*2, getRadius()*2);

        }
        // tail
        gc.setStroke(Color.RED);
        for(int i = 0; i < tail.size(); i++) {
            tail.get(i).display(gc, time);
        }
        gc.save();
        rotate(gc, World.width/2, World.height/2);
        if(DISPLAY_IN_CENTER)
            gc.drawImage(anim.getFrame(time), World.width/2-getRadius(), World.height/2-getRadius());
        else
            gc.drawImage(anim.getFrame(time), getX()-getRadius(), getY()-getRadius());
        gc.restore();

        // missiles
        for(Missile m : missiles) {
            m.display(gc, time);
        }
         // canvas
        for(CanvasElement ce : canvasElements) {
            ce.display(gc, time);
        }
    }

    @Override
    public void activity() {
        super.activity();
        controlActions();
//        logInfo();
    }

    private void logInfo() {
        if(System.currentTimeMillis() - INFO_TIME > INFO_STEP) {
            INFO_TIME = System.currentTimeMillis();
            System.out.println("\nELAPSED TIME: " + (System.currentTimeMillis()-creationTime)/1000.0);
            System.out.println("POCISKI: " + missiles.size());
            System.out.println("POZYCJA: " + getX() + " " + getY());
            System.out.println("DYMKI: " + tail.size());
            System.out.println("CANVASY: " + canvasElements.size());
            double maxR = 0;
            for(Smoke s : tail)
                if(s.getRadius() > maxR)
                    maxR = s.getRadius();
            System.out.println("NAJWIEKSZY DYMEK: " + maxR);
        }
    }

    @Override
    protected void regenerate() {
        fuel.refill(energyRegenerationRate);
    }

    public void useEngine(double x, double y, double fuelUse) {
        if(!fuel.empty()) {
            if(World.showTails) {

                tailCounter += fuelUse;
                if(tailCounter > 4) {
                    tailCounter = 0;
                    tail.add(new Smoke(getX(), getY(), getRadius(), -x*5, -y*5));
                    tail.removeIf(Smoke::isInactive);
                }
            }

            speedUp(x, y);
            fuel.use(fuelUse);
        }
    }

    public void controlActions() {
        if(!staticPosition) {   // use engine if not static
            if(KeyboardController.isKeyPressed("A"))
                useEngine(-unitSpeed, 0, 1);
            if(KeyboardController.isKeyPressed("D"))
                useEngine(unitSpeed, 0, 1);
            if(KeyboardController.isKeyPressed("W"))
                useEngine(0, -unitSpeed, 1);
            if(KeyboardController.isKeyPressed("S"))
                useEngine(0, unitSpeed, 1);
        } else {    // leave planet if space is pressed
            if(KeyboardController.isKeyPressed("SPACE")) {
                Physics.leavePlanet(this, getLandedPlanet());
                staticPosition = false;
            }
        }
        // set full map if Enter is pressed
        fullmap.setActive(KeyboardController.isKeyPressed("ENTER"));

        if(MouseController.isButtonPressed("PRIMARY") && weapon.isReady())
            weapon.use(MouseController.getMouseX(), MouseController.getMouseY());
    }

    public void shoot(double x, double y) {
        x += rx();
        y += ry();
        double distance = Math.sqrt(Math.pow(getX()-x,2)+Math.pow(getY()-y,2));
        double mvX = (x-getX())/distance;
        double mvY = (y-getY())/distance;
        missiles.add(new Missile(this, mvX, mvY));
    }

    @Override
    public void move() {
        super.move();
        X = getX();
        Y = getY();
    }

    @Override
    public void collideWith(GameObject c) {
        Missile m = (Missile)c;
        applyDamage(m.getDamage());
        m.explode();
    }

    @Override
    public double getDamage() {
        return weapon.getDamage();
    }
}