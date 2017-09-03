import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.Light;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;


/**
 * Created by jakub on 02.09.17.
 */
public class Player extends GameObject implements Active, RigidBody {

//    private double vx = 0, vy = 0;
    public static double X, Y;

    private double unitSpeed = 0.1;
    private double rotation = 0;
    private double animationRotation = 0;
    private boolean staticPosition = false;
    private Planet landedPlanet;

    private LinkedList<CanvasElement> canvasElements = new LinkedList<CanvasElement>();
    public Fuel fuel;
    private ArrayList<Missile> missiles = new ArrayList<Missile>();
    private Weapon weapon;

    private static Animation animationStand;
    private static Animation animationJump;

    private LinkedList<Smoke> tail = new LinkedList<Smoke>();
    private int tailCounter = 0;

    static {
        setAnimations();
    }

    public Player(double x, double y, double r) {
        super(x, y, r);
        setWidth(animationStand.getFrame(0).getWidth());
        setHeight(animationStand.getFrame(0).getHeight());
        setRadius((getWidth()+getHeight())/4);
        setVx(unitSpeed);
        fuel = new Fuel();
        weapon = new Weapon(4, this);
        canvasElements.add(fuel);
        canvasElements.add(weapon);
    }

    static void setAnimations() {
        animationStand = new Animation(Animation.createFrames("ufo", 1), 0.15);
        animationJump = new Animation(Animation.createFrames("ufo", 1), 0.15);
    }

    @Override
    public void display(GraphicsContext gc, double time) {
        try {

            // tail
            gc.setStroke(Color.RED);
            for(int i = 0; i < tail.size(); i++) {
                tail.get(i).display(gc, time);
            }

            gc.save();
            rotate(gc);

            Animation curAnim;
            if(staticPosition) {
                curAnim = animationStand;
            } else {
                curAnim = animationJump;
            }
            gc.drawImage(curAnim.getFrame(time), getX()-getRadius(), getY()-getRadius());
            gc.restore();

            // missiles
            for(Missile m : missiles) {
                m.display(gc, time);
            }

            // canvas
            for(CanvasElement ce : canvasElements) {
                ce.display(gc, time);
            }

        } catch (NullPointerException e) {
            System.out.println("Player animation was not defined!");
        }
    }

    @Override
    public void activity() {
        // calculations and stuff
        regenerate();
        move();
        controlActions();
        animate();
        for(Missile m : missiles) {
            m.activity();
        }
        missiles.removeIf(Missile::isInactive);
    }

    private void regenerate() {
        fuel.refill(0.01);
    }

    private void animate() {
        animationRotation = 10*Math.sin(System.currentTimeMillis()/200.0);
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

            setVx(getVx()+x);
            setVy(getVy()+y);
            fuel.use(fuelUse);
        }
    }

    public void controlActions() {
        if (KeyboardController.isKeyPressed("A")) {
            if(!staticPosition)
                useEngine(-unitSpeed, 0, 1);
        }
        if(KeyboardController.isKeyPressed("D")) {
            if(!staticPosition)
                useEngine(unitSpeed, 0, 1);
        }
        if(KeyboardController.isKeyPressed("W")) {
            if(!staticPosition)
                useEngine(0, -unitSpeed, 1);
        }
        if(KeyboardController.isKeyPressed("S")) {
            if(!staticPosition)
                useEngine(0, unitSpeed, 1);
        }

        if(KeyboardController.isKeyPressed("SPACE")) {
            if(staticPosition) {
                Physics.leavePlanet(this, getLandedPlanet());
                staticPosition = false;
            }
        }

        if(KeyboardController.isKeyPressed("ENTER"))
            this.rotation += 1;

        if(MouseController.isButtonPressed("PRIMARY")) {
            if(weapon.isReady()) {
                weapon.use(MouseController.getMouseX(), MouseController.getMouseY());
            }
//            shoot();
        }
    }

    public void shoot(double x, double y) {
        double distance = Math.sqrt(Math.pow(getX()-x,2)+Math.pow(getY()-y,2));
        double mvX = (x-getX())/distance;
        double mvY = (y-getY())/distance;
        missiles.add(new Missile(getX(), getY(), 4, mvX, mvY));
    }

    public Planet getLandedPlanet() {
        if(staticPosition) {
            return landedPlanet;
        } else {
            System.out.println("No planet landed!");
            return null;
        }
    }

    public void setLandedPlanet(Planet p) {
        landedPlanet = p;
        staticPosition = true;
    }

    @Override
    public void move() {
        setX(getX() + getVx());
        setY(getY() + getVy());
        staticPosition = (getVx()==0 && getVy()==0);

//        System.out.println(staticPosition);

        X = getX();
        Y = getY();

    }

    @Override
    public boolean isStatic() {
        return staticPosition;
    }

    @Override
    public void rotate(GraphicsContext gc) {
        Rotate r = new Rotate(rotation+animationRotation, getX(), getY());
        gc.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());
    }

    @Override
    public void applyGravity(Planet planet) {
        Physics.gravityAcceleration(this, planet);
        for(Missile m : missiles) {
            Physics.gravityAcceleration(m, planet);
        }
    }
}
