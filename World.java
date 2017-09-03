import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by jakub on 02.09.17.
 */
public class World {

    public static boolean showColliders = true;
    public static boolean showTails = true;

    public ArrayList<Player> players;
    public LinkedList<Displayable> decorations;
    public LinkedList<Planet> planets;

    public World() {

        decorations = new LinkedList<>();
        decorations.add(new Background());

        players = new ArrayList<>(1);
        Player p1 = new Player(100, 100, 10);
        players.add(p1);

        planets = generatePlanets();

    }

    private LinkedList<Planet> generatePlanets() {
        LinkedList<Planet> p = new LinkedList<>();
        p.add(new Planet(700, 200, 60, Color.BLUE));
        p.add(new Planet(400, 400, 60, Color.CRIMSON));
        p.add(new Planet(800, 800, 200, Color.GREENYELLOW));
        return p;
    }

    public LinkedList<Displayable> getDisplayables() {
        LinkedList<Displayable> displayables = new LinkedList<>();

        // decorations
        displayables.addAll(decorations);

        // planets
        displayables.addAll(planets);

        // players
        displayables.addAll(players);

        return displayables;
    }

    public LinkedList<RigidBody> getRigidBodies() {
        LinkedList<RigidBody> rigids = new LinkedList<>();

        // players
        rigids.addAll(players);

        return rigids;
    }

    public LinkedList<Active> getActives() {
        LinkedList<Active> actives = new LinkedList<>();

        // players
        actives.addAll(players);

        return actives;
    }

}
