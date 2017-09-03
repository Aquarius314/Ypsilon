import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * Created by jakub on 02.09.17.
 */
public class Background implements Displayable {

    private Image image = new Image("assets/space.png");

    @Override
    public void display(GraphicsContext gc, double time) {
        gc.drawImage(image, 0, 0);
    }
}
