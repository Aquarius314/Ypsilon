import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Created by jakub on 03.09.17.
 */
public class Planet extends GameObject {

    private Color color;

    public Planet(double x, double y, double r, Color color) {
        super(x, y, r);
        this.color = color;
    }

    private void displayAtmosphere(GraphicsContext gc, int i) {
        double R, G, B, O;
        R = color.getRed();
        G = color.getGreen();
        B = color.getBlue();
        gc.setStroke(new Color(R, G, B, (10.0-i)/10.0));
        double r = getRadius()+i;
        gc.strokeOval(getX()-r, getY()-r, r*2, r*2);
    }

    @Override
    public void display(GraphicsContext gc, double time) {
        // atmosphere
        for(int i = 1; i <= 10; i++) {
            displayAtmosphere(gc, i);
        }

        gc.setFill(color);
        gc.fillOval(getX()-getRadius(), getY()-getRadius(), getRadius()*2, getRadius()*2);
    }
}
