package Flying;

import All.Active;
import All.Missile;
import Environment.GameObject;
import Environment.Physics;
import Environment.Planet;
import Environment.RigidBody;
import Flying.Weapon.Weapon;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by jakub on 05.09.17.
 */
public abstract class Alive extends GameObject implements Active, RigidBody {

    protected double maxHealth;
    protected double health = maxHealth;
    protected double maxUnitSpeed = 10.0;
    protected double unitSpeed = 0.1;
    protected boolean isAlive = true;
    protected Planet landedPlanet;

    protected ArrayList<Missile> missiles = new ArrayList<Missile>();
    protected LinkedList<CanvasElement> canvasElements = new LinkedList<CanvasElement>();
    protected LinkedList<Smoke> tail = new LinkedList<Smoke>();
    protected int tailCounter = 0;

    public Alive(double x, double y, double r) {
        super(x, y, r);
        setAlign();
    }

    public abstract void shoot(double x, double y);

    public void applyDamage(double dmg) {
        health -= dmg;
        System.out.println("Took " + dmg + " damage! HP left: " + health);
        if(health <= 0) {
            kill();
            System.out.println("Died!");
        }
    }

    public abstract double getDamage();

    protected abstract void setAlign();

    public int getAlign() {
        return align;
    }

    public abstract void kill();

    public double getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(double maxHealth) {
        this.maxHealth = maxHealth;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public double getUnitSpeed() {
        return unitSpeed;
    }

    public void setUnitSpeed(double unitSpeed) {
        this.unitSpeed = unitSpeed;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
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
    }

    @Override
    public void applyGravity(Planet planet) {
        Physics.gravityAcceleration(this, planet);
        for(Missile m : missiles) {
            Physics.gravityAcceleration(m, planet);
        }
    }

    protected abstract void regenerate();

    private void controlSpeed() {
        if(Math.abs(getVx()) > maxUnitSpeed)
            setVx(maxUnitSpeed*Math.signum(getVx()));
        if(Math.abs(getVy()) > maxUnitSpeed)
            setVy(maxUnitSpeed*Math.signum(getVy()));
    }

    @Override
    public void activity() {
        regenerate();
        controlSpeed();
        move();
        for(Missile m : missiles) {
            m.activity();
        }
        missiles.removeIf(Missile::isInactive);
    }

    public ArrayList<Missile> getMissiles() {
        return missiles;
    }
}
