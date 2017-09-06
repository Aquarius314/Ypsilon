package Environment;

import Flying.Player;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Created by jakub on 03.09.17.
 */
public class Planet extends GameObject {

    public Color color;
    public Color atmosphereColor;
    private boolean alive = true;
    private double atmosphereWidth;

    public Planet(double x, double y, double r, Color color) {
        super(x, y, r);
        this.color = color;
        this.atmosphereColor = color;
        this.atmosphereWidth = getRadius()/6;
    }
    public Planet(double x, double y, double r, Color color, Color atmosphereColor) {
        super(x, y, r);
        this.color = color;
        this.atmosphereColor = atmosphereColor;
        this.atmosphereWidth = getRadius()/6;
    }
    public Planet(double x, double y, double r, Color color, Color atmosphereColor, int atmosphereWidth) {
        super(x, y, r);
        this.color = color;
        this.atmosphereColor = atmosphereColor;
        this.atmosphereWidth = atmosphereWidth;
    }

    private void displayAtmosphere(GraphicsContext gc, int i) {
        double R, G, B, O;
        R = atmosphereColor.getRed();
        G = atmosphereColor.getGreen();
        B = atmosphereColor.getBlue();
        gc.setStroke(new Color(R, G, B, 1.0 - i/atmosphereWidth));
        double r = getRadius()+i;
        gc.strokeOval(getX()-r - Player.rx(), getY()-r - Player.ry(), r*2, r*2);
    }

    @Override
    public void display(GraphicsContext gc, double time) {

        // don't display if it's far away from player
        double x = getX()-getRadius() - Player.rx();
        if(x + getRadius()*2.5 < 0) return;
        if(x - getRadius()*2.5 > World.width*2) return;
        double y = getY()-getRadius() - Player.ry();
        if(y + getRadius()*2.5 < 0) return;
        if(y - getRadius()*2.5 > World.height*2) return;

        // atmosphere
        for(int i = 1; i <= atmosphereWidth; i++) {
            displayAtmosphere(gc, i);
        }

        gc.setFill(color);
        gc.fillOval(x, y, getRadius()*2, getRadius()*2);
    }

    public void destroy() {
        alive = false;
    }

    public boolean notAlive() {
        return !alive;
    }

    @Override
    public void collideWith(GameObject c) {
        // pass
    }

}
