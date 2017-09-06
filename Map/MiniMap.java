package Map;

import Environment.World;
import Flying.Player;

/**
 * Created by jakub on 04.09.17.
 */
public class MiniMap extends Map {

    public MiniMap(Player parent) {
        super(parent);
        width = World.width/10;
        height = World.height/10;
        x = 120;//+width/2;
        y = 10;//+height/2;
        planetSize = 2;
        playerSize = 4;
    }
}
