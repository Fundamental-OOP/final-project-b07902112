package plant;

import fsm.*;
import menu.GameMenu;
import utils.ImageStateUtils;
import zombie.Zombie;

import java.awt.*;
import java.util.List;

public class SnowPeashooter extends ShooterPlant {
    public static final Dimension SIZE = new Dimension(71, 71);
    public static final Dimension RANGE = new Dimension(
            3000, SnowPea.SIZE.height);
    public static final int UPDATE_INTERVAL = 5;

    public SnowPeashooter(Point center, GameMenu menu) {
        super(center, SIZE, RANGE, menu);

        ImageRenderer imageRenderer = new PlantImageRenderer(this);
        List<ImageState> imageStates = ImageStateUtils.imageStatesFromFolder(
                "assets/Plants/SnowPea", imageRenderer);
        State idle = new WaitingPerFrame(UPDATE_INTERVAL,
                new CyclicSequence(imageStates));
        State attack = new WaitingPerFrame(UPDATE_INTERVAL,
                new PeashooterShootingSequence(imageStates, this));
        super.setStates(idle, attack);
    }

    @Override
    public void shoot() {
        if (this.world != null)
            this.getWorld().addSprites(new SnowPea(
                    this.getBulletStartingLocation(SnowPea.SIZE), this.menu));
    }

    public static class SnowPeashooterFactory implements PlantFactory {
        @Override
        public Plant getPlant(Point location, GameMenu menu) {
            return new SnowPeashooter(location, menu);
        }
    }
}

class SnowPea extends Bullet {
    public static final Dimension SIZE = new Dimension(56, 34);
    public static final int EXPLOSION_DURATION = 10;

    public SnowPea(Point location, GameMenu menu) {
        super(location, SIZE, menu);

        ImageRenderer imageRenderer = new PlantImageRenderer(this);
        State flying = new BulletFlyingState(this,
                ImageStateUtils.imageStateFromPath(
                        "assets/Bullets/PeaIce/0.png", imageRenderer));
        State explode = ImageStateUtils.imageStateFromPath(
                "assets/Bullets/PeaNormalExplode/0.png",
                imageRenderer);
        super.setStates(flying, explode, EXPLOSION_DURATION);
    }

    @Override
    public void damage(Zombie zombie) {
        super.damage(zombie);
        zombie.slowDown();
    }
}