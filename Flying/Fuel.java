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
        gc.setFill(Color.BLUE);
        gc.setStroke(Color.RED);
        gc.setLineWidth(2);
        gc.fillRoundRect(10, 10, value, 10, 10, 10);
        gc.strokeRoundRect(10, 10, maxValue, 10, 10, 10);
    }
}
