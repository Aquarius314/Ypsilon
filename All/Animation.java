package All;

import com.sun.istack.internal.Nullable;
import javafx.scene.image.Image;

/**
 * Created by jakub on 02.09.17.
 */
public class Animation {

    private Image[] frames;
    private double frameDuration;
    private long displayTime = 0;

    // for faster use
    public static Image[] redExplosionFrames, blueExplosionFrames;
    public static Image[] playerFrames;

    private int counter = 0;
    public boolean animationFinished = false;

    static {
        redExplosionFrames = createFrames("explosion/red", 17);
        blueExplosionFrames = createFrames("explosion/blue", 17);
        playerFrames = createFrames("player", 8);
    }

    public Animation(String name, int n, double frameDuration) {
        switch(name) {
            case "explosion/red" :
                frames = redExplosionFrames;
                break;
            case "explosion/blue" :
                frames = blueExplosionFrames;
                break;
            case "player" :
                frames = playerFrames;
                break;
            default :
                frames = createFrames(name, n);
        }
      this.frameDuration = frameDuration;
    }

    @Nullable
    private static Image[] createFrames(String name, int n) {
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

    public Image getFrame(double time) {
        long currentTime = System.currentTimeMillis();
        if((currentTime - displayTime) > frameDuration*1000.0 ) {
            displayTime = currentTime;
            counter++;
            if(counter >= frames.length) {
                animationFinished = true;
                counter = 0;
            }
        }
        return frames[counter];
    }

    public double getTotalDuration() {

        return 1000*frames.length*frameDuration;
    }
}
