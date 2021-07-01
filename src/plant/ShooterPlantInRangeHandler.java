package plant;

import model.InRangeHandler;
import model.Sprite;
import zombie.Zombie;

import java.awt.*;

public class ShooterPlantInRangeHandler implements InRangeHandler {
    @Override
    public void handle(Point originalLocation, Sprite from, Sprite to) {
        if (from instanceof ShooterPlant && to instanceof Zombie) {
            ShooterPlant shooterPlant = (ShooterPlant) from;
            shooterPlant.triggerAttack();
        }
    }
}
