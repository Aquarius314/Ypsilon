package Map;

import Environment.World;
import Flying.Player;
import javafx.scene.canvas.GraphicsContext;

/**
 * Created by jakub on 04.09.17.
 */
public class FullMap extends Map {

    private boolean active = false;

    public FullMap(Player parent) {
        super(parent);
        width = World.width;
        height = World.height;
        x = 0;
        y = 0;
        planetSize = 5;
        playerSize = 10;
        opacity = 0.8;
        s = 40;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean a) {
        active = a;
    }

    @Override
    public void display(GraphicsContext gc, double time) {
        if(active) {
            super.display(gc, time);
        }
    }
}
