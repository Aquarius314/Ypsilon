package Map;

import Environment.Planet;
import Environment.World;
import Flying.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Created by jakub on 04.09.17.
 */
public abstract class Map extends CanvasElement {

    protected Player parent;
    protected int width, height;
    protected double s = 600;    // scale
    protected int x, y;
    protected double opacity = 1.0;
    protected int planetSize, playerSize;

    public Map(Player parent) {
        this.parent = parent;
    }

    @Override
    public void display(GraphicsContext gc, double time) {
        gc.setStroke(Color.RED);
        gc.setFill(new Color(0, 0, 0, opacity));
        gc.fillRect(x, y, width, height);
        gc.strokeRect(x, y, width, height);

        // planets
        for(Planet p : World.planets) {
            Color col = new Color(p.color.getRed(), p.color.getGreen(), p.color.getBlue(), opacity);
            gc.setFill(col);
            gc.fillOval(x + width/2 + p.getX()/s-planetSize/2, y + height/2 + p.getY()/s-planetSize/2, p.getRadius()/s+1, p.getRadius()/s+1);
        }

        // player
        gc.setFill(Color.BLUE);
        gc.fillRect(x + width/2 + parent.getX()/s-playerSize/2, y + height/2 + parent.getY()/s-playerSize/2, playerSize, playerSize);
    }

}
