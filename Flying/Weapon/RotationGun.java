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
            for(int i = 0; i < 65; i++) {
                double rx = owner.getX() - owner.rx() + 100*Math.sin(i/10.0);
                double ry = owner.getY() - owner.ry() + 100*Math.cos(i/10.0);
                owner.shoot(rx, ry);
            }
            if(!Player.INFINITE_AMMUNITION)
                ammunition--;
        }
    }
}
