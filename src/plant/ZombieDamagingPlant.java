package plant;

import menu.GameMenu;
import zombie.Zombie;

import java.awt.*;

public abstract class ZombieDamagingPlant extends Plant {
    public ZombieDamagingPlant(Point center, Dimension size, Dimension range,
                               GameMenu menu) {
        super(center, size, range, menu);
    }

    public ZombieDamagingPlant(Point center, Dimension size, Dimension range,
                               int hp, GameMenu menu) {
        super(center, size, range, hp, menu);
    }

    public abstract void damage(Zombie zombie);
}
