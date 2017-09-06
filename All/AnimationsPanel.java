package All;

import javafx.scene.canvas.GraphicsContext;

import java.util.LinkedList;

/**
 * Created by jakub on 05.09.17.
 */
public class AnimationsPanel implements Displayable {

    private LinkedList<LoneAnimation> animations = new LinkedList<>();

    public void addAnimation(LoneAnimation l) {
        animations.add(l);
    }

    @Override
    public void display(GraphicsContext gc, double time) {
        for(LoneAnimation l : animations) {
            l.display(gc, time);
        }
        animations.removeIf(LoneAnimation::expired);
    }
}
