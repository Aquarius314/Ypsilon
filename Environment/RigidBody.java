package Environment;

import All.Movable;

/**
 * Created by jakub on 02.09.17.
 */
public interface RigidBody extends Movable {

    void applyGravity(Planet planet);

}
