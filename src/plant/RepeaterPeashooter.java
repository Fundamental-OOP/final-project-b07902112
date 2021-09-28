package plant;

import fsm.*;
import menu.GameMenu;
import utils.ImageStateUtils;

import java.awt.*;
import java.util.List;

public class RepeaterPeashooter extends ShooterPlant {
    private static final Dimension SIZE = new Dimension(73, 71);
    private static final Dimension RANGE = new Dimension(
            3000, Pea.SIZE.height);
    private static final int UPDATE_INTERVAL = 5;

    public RepeaterPeashooter(Point center, GameMenu menu) {
        super(center, SIZE, RANGE, menu);

        ImageRenderer imageRenderer = new PlantImageRenderer(this);
        List<ImageState> imageStates = ImageStateUtils.imageStatesFromFolder(
                "assets/Plants/RepeaterPea", imageRenderer);
        State idle = new WaitingPerFrame(UPDATE_INTERVAL,
                new CyclicSequence(imageStates));
        State attack = new WaitingPerFrame(UPDATE_INTERVAL,
                new RepeaterPeaShootingSequence(imageStates, this));
        super.setStates(idle, attack);
    }

    @Override
    public void shoot() {
        if (this.world != null)
            this.getWorld().addSprites(
                    new Pea(this.getBulletStartingLocation(Pea.SIZE), this.menu));
    }

    public static class RepeaterPeashooterFactory implements PlantFactory {
        @Override
        public Plant getPlant(Point location, GameMenu menu) {
            return new RepeaterPeashooter(location, menu);
        }
    }
}

class RepeaterPeaShootingSequence extends ShootingSequence {
    public static final int ATTACK_POS_1 = 2;
    public static final int ATTACK_POS_2 = 4;

    public RepeaterPeaShootingSequence(List<? extends State> states,
                                       ShooterPlant plant) {
        super(states, plant);
    }

    @Override
    public void update() {
        super.update();
        if (this.currentPosition == ATTACK_POS_1 ||
                this.currentPosition == ATTACK_POS_2)
            this.plant.shoot();
    }
}
