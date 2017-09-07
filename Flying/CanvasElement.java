package Flying;

import All.Displayable;
import javafx.scene.paint.Color;

/**
 * Created by jakub on 03.09.17.
 */
public abstract class CanvasElement implements Displayable {

    protected double x, y, w, h, arc;
    protected Color strokeColor, fillColor;

}
