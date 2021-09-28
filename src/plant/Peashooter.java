package plant;

import fsm.*;
import menu.GameMenu;
import utils.ImageStateUtils;

import java.awt.*;
import java.util.List;

public class Peashooter extends ShooterPlant {
    private static final Dimension SIZE = new Dimension(71, 71);
    private static final Dimension RANGE = new Dimension(
            3000, Pea.SIZE.height);
    private static final int UPDATE_INTERVAL = 5;

    public Peashooter(Point center, GameMenu menu) {
        super(center, SIZE, RANGE, menu);

        ImageRenderer imageRenderer = new PlantImageRenderer(this);
        List<ImageState> imageStates = ImageStateUtils.imageStatesFromFolder(
                "assets/Plants/Peashooter", imageRenderer);
        State idle = new WaitingPerFrame(UPDATE_INTERVAL,
                new CyclicSequence(imageStates));
        State attack = new WaitingPerFrame(UPDATE_INTERVAL,
                new PeashooterShootingSequence(imageStates, this));
        super.setStates(idle, attack);
    }

    @Override
    public void shoot() {
        if (this.world != null)
            this.getWorld().addSprites(new Pea(
                    this.getBulletStartingLocation(Pea.SIZE), this.menu));
    }

    public static class PeashooterFactory implements PlantFactory {
        @Override
        public Plant getPlant(Point location, GameMenu menu) {
            return new Peashooter(location, menu);
        }
    }
}
