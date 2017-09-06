package Environment;

import All.Displayable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.transform.Rotate;

/**
 * Created by jakub on 02.09.17.
 */
public abstract class GameObject implements Displayable {

    private double x, y;
    private double width, height;
    private double radius;
    private double vx, vy;
    protected boolean staticPosition = false;
    private double rotation;

    public GameObject(){}

    public GameObject(double x, double y, double r) {
        this.x = x;
        this.y = y;
        this.radius = r;
    }

    public double getVx() {
        return vx;
    }

    public double getVy() {
        return vy;
    }

    public void setVx(double vx) {
        this.vx = vx;
    }

    public void setVy(double vy) {
        this.vy = vy;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void speedUp(double x, double y) {
        this.vx += x;
        this.vy += y;
    }

    public void move() {
        staticPosition = (getVx()==0 && getVy()==0);
        x += vx;
        y += vy;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double distanceTo(GameObject g) {
        return Math.sqrt(Math.pow(g.getX()-this.getX(), 2) + Math.pow(g.getY()-this.getY(), 2));
    }

    public void checkCollision(GameObject c) {
        double rSum = getRadius()+c.getRadius();
        if(rSum < distanceTo(c)) {
            // we have a collision
            collideWith(c);
        }
    }

    public double getRotation() {
        return rotation;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

    protected void rotate(GraphicsContext gc, double x, double y) {
        setRotation(Math.atan2(getVy(), getVx()) * 180 / Math.PI + 90);
        Rotate r = new Rotate(getRotation(), x, y);
        gc.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());
    }

    protected void rotate(GraphicsContext gc) {
        setRotation(Math.atan2(getVy(), getVx()) * 180 / Math.PI + 90);
        Rotate r = new Rotate(getRotation(), getX()+getRadius(), getY()+getRadius());
        gc.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());
    }


    public boolean isStatic() {
        return staticPosition;
    }

    public void setStaticPosition(boolean v) {
        staticPosition = v;
    }

    public abstract void collideWith(GameObject c);
}
