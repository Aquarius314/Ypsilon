package Flying.Weapon;

import Flying.Player;

/**
 * Created by jakub on 04.09.17.
 */
public class MachineGun extends Weapon {
    public MachineGun(double s, Player owner) {
        super(s, owner);
        damage = 10;
    }

    @Override
    public void use(double x, double y) {
        lastUseTime = System.currentTimeMillis();
        if(ammunition > 0) {
            owner.shoot(x, y);
            if(!Player.INFINITE_AMMUNITION)
                ammunition--;
        }
    }
}
