package Environment;

import All.*;
import Flying.Enemy;
import Flying.Player;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

/**
 * Created by jakub on 02.09.17.
 */
public class World {

    public static int width, height;

    public static boolean showColliders = true;
    public static boolean showTails = true;

    public ArrayList<Player> players;
    public LinkedList<Displayable> decorations;
    public static LinkedList<Planet> planets;
    public static LinkedList<Enemy> enemies;

    public static AnimationsPanel animationsPanel;

    public World(int worldWidth, int worldHeight) {

        width = worldWidth;
        height = worldHeight;

        decorations = new LinkedList<>();
        decorations.add(new Background());
        animationsPanel = new AnimationsPanel();

        players = new ArrayList<>(1);
        Player p1 = new Player(100, 100, 10);
        players.add(p1);

        enemies = new LinkedList<>();
        enemies.add(new Enemy(200, 200, 10));

        planets = generatePlanets();

    }

    private LinkedList<Planet> generatePlanets() {
        LinkedList<Planet> p = new LinkedList<>();
        p.add(new Planet(700, 200, 60, Color.BLUE));
        p.add(new Planet(400, 400, 60, Color.CRIMSON));
        p.add(new Planet(800, 800, 200, Color.GREENYELLOW));
        p.add(new Planet(100, 1800, 20, Color.BLACK, Color.WHITE, 50));

        Random rnd = new Random();

        for(int i = 0; i < 1000; i++) {
            int x = rnd.nextInt()/100000;
            int y = rnd.nextInt()/100000;
            int r = Math.abs(rnd.nextInt()%100) + 60;
            Color col = new Color(Math.abs(rnd.nextFloat()), Math.abs(rnd.nextFloat()), Math.abs(rnd.nextFloat()), 1.0);
            p.add(new Planet(x, y, r, col));
        }

        p.add(new Planet(0, -10000, 1000, Color.BLACK));

//        int x = 0, y = 0;
//        for(int i = 0; i < 100; i++) {
//            x += rnd.nextInt()%200 + 200;
//            y += rnd.nextInt()%200 + 200;
//            int r = Math.abs(rnd.nextInt()%60) + 60;
//            Color col = new Color(Math.abs(rnd.nextFloat()), Math.abs(rnd.nextFloat()), Math.abs(rnd.nextFloat()), 1.0);
//            p.add(new Planet(x, y, r, col));
//        }

        return p;
    }

    public LinkedList<Displayable> getDisplayables() {
        LinkedList<Displayable> displayables = new LinkedList<>();

        // decorations
        displayables.addAll(decorations);

        // planets
        displayables.addAll(planets);

        // enemies
        displayables.addAll(enemies);

        // players
        displayables.addAll(players);

        // animations panel
        displayables.add(animationsPanel);

        return displayables;
    }

    public LinkedList<RigidBody> getRigidBodies() {
        LinkedList<RigidBody> rigids = new LinkedList<>();

        // enemies
        rigids.addAll(enemies);

        // players
        rigids.addAll(players);

        return rigids;
    }

    public LinkedList<Active> getActives() {
        LinkedList<Active> actives = new LinkedList<>();

        // enemies
        actives.addAll(enemies);

        // players
        actives.addAll(players);

        return actives;
    }

    public LinkedList<GameObject> getAliveColliders() {
        LinkedList<GameObject> colliders = new LinkedList<>();
        colliders.addAll(players);
        colliders.addAll(enemies);
        return colliders;
    }

    public LinkedList<GameObject> getColliders() {
        LinkedList<GameObject> colliders = new LinkedList<>();
        // players/enemies missiles
        for(Player p : players) {
            colliders.addAll(p.getMissiles());
        }
        for(Enemy e : enemies) {
            colliders.addAll(e.getMissiles());
        }
        // so far no more colliders (will be in future (boosts, bonuses, bombs etc)

        return colliders;
    }

}
