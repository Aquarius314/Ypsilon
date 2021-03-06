package Flying.Weapon;

import Flying.Player;

/**
 * Created by jakub on 04.09.17.
 */
public class RotationGun extends Weapon {

    public RotationGun(double s, Player owner) {
        super(s, owner);
        damage = 10;
        shootingSpeed = 4;
    }

    @Override
    public void use(double x, double y) {
        lastUseTime = System.currentTimeMillis();
        if(ammunition > 0) {
            for(int i = 0; i < 650; i++) {
                double rx = owner.getX() - Player.rx() + 100*Math.sin(i/100.0);
                double ry = owner.getY() - Player.ry() + 100*Math.cos(i/100.0);
                owner.shoot(rx, ry);
            }
            if(!Player.INFINITE_AMMUNITION)
                ammunition--;
        }
    }
}
