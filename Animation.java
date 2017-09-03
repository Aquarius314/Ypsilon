import com.sun.istack.internal.Nullable;
import javafx.scene.image.Image;

/**
 * Created by jakub on 02.09.17.
 */
public class Animation {
    private Image[] frames;
    private double duration;

    public Animation(Image[] frames, double duration) {
        this.frames = frames;
        this.duration = duration;
    }

    public Image getFrame(double time) {
        int index = (int)((time % (frames.length * duration)) / duration);
        return frames[index];
    }

    @Nullable
    public static Image[] createFrames(String name, int n) {
        if(n <= 0) {
            System.out.println("N must be greater than 0!");
            return null;
        }
        Image[] f = new Image[n];
        for(int i = 0; i < n; i++) {
            f[i] = new Image("assets/animations/"+name+"/"+i+".png");
        }
        return f;
    }
}
