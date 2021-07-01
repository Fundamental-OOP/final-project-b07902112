package zombie;

import menu.GameMenu;

import java.awt.*;

public interface ZombieFactory {
    Zombie getZombie(int level, Point location, GameMenu menu);
}
