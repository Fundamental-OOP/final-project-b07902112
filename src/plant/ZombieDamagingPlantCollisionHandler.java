package plant;

import model.CollisionHandler;
import model.Sprite;
import zombie.Zombie;

import java.awt.*;

public class ZombieDamagingPlantCollisionHandler implements CollisionHandler {
    @Override
    public void handle(Point originalLocation, Sprite from, Sprite to) {
        if (from instanceof ZombieDamagingPlant && to instanceof Zombie) {
            ZombieDamagingPlant plant = (ZombieDamagingPlant) from;
            Zombie zombie = (Zombie) to;
            plant.damage(zombie);
        }
    }
}
