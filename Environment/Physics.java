package Environment;

import Flying.Alive;
import Flying.Player;
import javafx.scene.shape.Rectangle;

/**
 * Created by jakub on 02.09.17.
 */
public abstract class Physics {

    public static final double G = 9.84/100;

    private Physics(){}

    public static boolean rectangleCollision(Rectangle r1, Rectangle r2) {

        double  lx1 = r1.getX(), rx1 = r1.getX()+r1.getWidth(),
                lx2 = r2.getX(), rx2 = r2.getX()+r2.getWidth(),
                uy1 = r1.getY(), dy1 = r1.getY()+r1.getHeight(),
                uy2 = r2.getY(), dy2 = r2.getY()+r2.getHeight();

        boolean vertical = (lx1 > lx2 && lx1 < rx2) || (rx1 > lx2 && rx1 < rx2);
        boolean horizontal = (uy1 > uy2 && uy1 < dy2) || (dy1 > uy2 && dy1 < dy2);

        return (vertical && horizontal);
    }

    public static void leavePlanet(Player player, Planet planet) {
        double dx = (player.getX()-planet.getX())/100;
        double dy = (player.getY()-planet.getY())/100;
        if(!player.fuel.empty()) {
            player.setX(player.getX()+dx*2);
            player.setY(player.getY()+dy*2);
            player.useEngine(dx, dy, 10);
        }
    }

    public static void gravityAcceleration(GameObject o1, Planet o2) {
        double dx = o2.getX() - o1.getX();
        double dy = o2.getY() - o1.getY();

        // avoid calculating very big numbers
        if(Math.abs(dx) > 10000 || Math.abs(dy) > 10000) return;

        double distance = Math.sqrt(dx*dx + dy*dy);
        if(!o1.isStatic()) {
            if(distance > o1.getRadius()+o2.getRadius()) {
                double cumulatedMass = Math.pow(o1.getRadius(), 2)*Math.pow(o2.getRadius()/1000.0, 2);
                o1.setVx(o1.getVx() + G*cumulatedMass*dx/(distance*distance));
                o1.setVy(o1.getVy() + G*cumulatedMass*dy/(distance*distance));
            } else {
                o1.setVx(0);
                o1.setVy(0);

                if(o1.getClass().getSuperclass().equals(Alive.class)) {
                    Player p1 = (Player)o1;
                    p1.setLandedPlanet(o2);
                }
                o1.setStaticPosition(true);

//                if(o1.getClass().equals(Missile.class)) {
//                    o2.setRadius(o2.getRadius() - ((Missile)o1).getDamage());
//                    if(o2.getRadius() <= 0) {
//                        o2.destroy();
//                    }
//                }
            }
        }
    }

    /*  PLANET GRAVITY
    for(var i = 0; i < planets.length; i++) {
      var P = planets[i];
      var distance = Math.sqrt(Math.pow(P.x-this.x, 2) + Math.pow(P.y-this.y, 2));
      if(distance > P.r+this.r) {
        var G = Math.pow(P.r, 1.5)/1000000;
        this.vx += G*(P.x - this.x)/distance;
        this.vy += G*(P.y - this.y)/distance;
      } else {
        this.vx = 0;
        this.vy = 0;
        break;
      }
    }
     */

    /*

    @Override
    public void rotate(GraphicsContext gc) {
        double rotation = 0;
        Rotate r = new Rotate(rotation +animationRotation, getX(), getY());
        gc.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());
    }
     */

}
