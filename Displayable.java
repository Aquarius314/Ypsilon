import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * Created by jakub on 02.09.17.
 */
public interface Displayable {

    void display(GraphicsContext gc, double time);

}
