package zombie;

import model.CollisionHandler;
import model.Sprite;
import plant.*;

import java.awt.*;

/**
 * @author - johnny850807@gmail.com (Waterball)
 */
public class ZombieCollisionHandler implements CollisionHandler {
    @Override
    public void handle(Point originalLocation, Sprite from, Sprite to) {
        if (from instanceof Zombie && to instanceof EdiblePlant) {
            Zombie zombie = (Zombie) from;
            if (zombie.damageArea().intersects(to.getBody()))
                zombie.attack();
        }
    }
}
