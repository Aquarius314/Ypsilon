package All;

import Environment.GameObject;
import Flying.Player;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * Created by jakub on 05.09.17.
 */
public class LoneAnimation implements Displayable {

    private double x, y;
    private Animation anim;
    private long creationTime;
    private double width, height;

    public LoneAnimation(GameObject g, String name, int n, double fDuration) {
        this.anim = new Animation(name, n, fDuration);
        this.x = g.getX();
        this.y = g.getY();
        this.creationTime = System.currentTimeMillis();
        setSize();
    }

    private void setSize() {
        width = anim.getFrame(0).getWidth();
        height = anim.getFrame(0).getHeight();
    }

    @Override
    public void display(GraphicsContext gc, double time) {
        Image i = anim.getFrame(time);
        if(!expired())
            gc.drawImage(i, x-width/2 - Player.rx() , y-height/2 - Player.ry());
    }

    public boolean expired() {
        return anim.animationFinished;
    }
}
