package plant;

import fsm.ImageRenderer;
import fsm.State;
import menu.GameMenu;
import utils.ImageStateUtils;

import java.awt.*;

class Pea extends Bullet {
    public static final Dimension SIZE = new Dimension(56, 34);
    public static final int EXPLOSION_DURATION = 10;

    public Pea(Point location, GameMenu menu) {
        super(location, SIZE, menu);

        ImageRenderer imageRenderer = new PlantImageRenderer(this);
        State flying = new BulletFlyingState(this,
                ImageStateUtils.imageStateFromPath(
                        "assets/Bullets/PeaNormal/0.png", imageRenderer));
        State explode = ImageStateUtils.imageStateFromPath(
                "assets/Bullets/PeaNormalExplode/0.png",
                imageRenderer);
        super.setStates(flying, explode, EXPLOSION_DURATION);
    }
}
