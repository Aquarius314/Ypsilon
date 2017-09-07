package Flying;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Created by jakub on 03.09.17.
 */
public class Fuel extends CanvasElement {

    private final double maxValue = 100;
    private double value = maxValue;
    private double unit = 0.1;

    public Fuel() {
        x = 10;
        y = 10;
        h = 10;
        arc = 10;
        strokeColor = Color.RED;
        fillColor = Color.BLUE;
    }

    public void use(double val) {
        value -= unit*val;
    }

    public void refill(double val) {
        value += val;
        value = Math.min(value, maxValue);
    }

    public boolean empty() {
        return value <= 0;
    }

    @Override
    public void display(GraphicsContext gc, double time) {
        gc.setFill(fillColor);
        gc.setStroke(strokeColor);
        gc.setLineWidth(2);
        gc.fillRoundRect(x, y, value, h, arc, arc);
        gc.strokeRoundRect(x, y, maxValue, h, arc, arc);
    }
}
