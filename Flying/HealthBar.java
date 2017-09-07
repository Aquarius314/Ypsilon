package Flying;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Created by jakub on 07.09.17.
 */
public class HealthBar extends CanvasElement {

    private Alive parent;

    public HealthBar(Alive a) {
        x = 10;
        y = 50;
        w = 100;
        h = 10;
        arc = 10;
        parent = a;
        strokeColor = Color.GREEN;
    }

    @Override
    public void display(GraphicsContext gc, double time) {
        gc.setFill(Color.RED);
        gc.fillRoundRect(x, y, parent.getHealth()/parent.getMaxHealth()*w, h, arc, arc);
        gc.setStroke(strokeColor);
        gc.strokeRoundRect(x, y, w, h, arc, arc);
    }
}
