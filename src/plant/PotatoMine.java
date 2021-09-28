package plant;

import fsm.*;
import media.AudioPlayer;
import menu.GameMenu;
import utils.ImageStateUtils;
import zombie.Zombie;

import java.awt.*;

import static fsm.FiniteStateMachine.Transition.from;
import static plant.PotatoMine.Event.*;

public class PotatoMine extends ZombieDamagingPlant implements EdiblePlant {
    public static final String AUDIO_READY = "PotatoMineRisingUp";
    public static final String AUDIO_EXPLODE = "PotatoMineExplosion";
    public static final Dimension SIZE = new Dimension(75, 55);
    public static final Dimension RANGE = new Dimension(0, 0);
    public static final int UPDATE_INTERVAL = 10;
    // apparently, a potato mine takes 15000ms to prime, so this duration is
    // calculated from 15000ms / 15ms where 15ms is the frame time
    public static final int INIT_DURATION = 1000;
    private final FiniteStateMachine fsm = new FiniteStateMachine();
    private int currentFrame;

    public enum Event {
        READY
    }

    public PotatoMine(Point center, GameMenu menu) {
        super(center, SIZE, RANGE, menu);

        ImageRenderer imageRenderer = new PlantImageRenderer(this);
        State init = WaitingPerFrame.cyclicImageStates(
                "assets/Plants/PotatoMine/PotatoMineInit", imageRenderer,
                UPDATE_INTERVAL);
        State normal = WaitingPerFrame.cyclicImageStates(
                "assets/Plants/PotatoMine/PotatoMine", imageRenderer,
                UPDATE_INTERVAL);

        this.fsm.setInitialState(init);
        this.fsm.addTransition(from(init).when(READY).to(normal));

        this.currentFrame = 0;
    }

    @Override
    public void update() {
        this.fsm.update();
        if (this.currentFrame < INIT_DURATION &&
                ++this.currentFrame == INIT_DURATION) {
            this.fsm.trigger(READY);
            AudioPlayer.playSounds(AUDIO_READY);
        }
    }

    @Override
    public void render(Graphics graphics) {
        this.fsm.render(graphics);
    }

    @Override
    public void damage(Zombie zombie) {
        if (this.world != null && this.currentFrame == INIT_DURATION) {
            Point mid = new Point(this.getLocation());
            mid.translate(SIZE.width / 2, SIZE.height / 2);
            this.getWorld().addSprites(new PotatoMineExplosion(mid, this.menu));
            AudioPlayer.playSounds(AUDIO_EXPLODE);
            this.onDamaged(this.hp);
        }
    }

    public static class PotatoMineFactory implements PlantFactory {
        @Override
        public Plant getPlant(Point location, GameMenu menu) {
            return new PotatoMine(location, menu);
        }
    }
}

class PotatoMineExplosion extends Explosion {
    public static final Dimension SIZE = new Dimension(132, 93);
    public static final int EXPLODE_DURATION = 10;

    public PotatoMineExplosion(Point location, GameMenu menu) {
        super(location, SIZE, menu);

        ImageRenderer imageRenderer = new PlantImageRenderer(this);
        State explode = ImageStateUtils.imageStateFromPath(
                "assets/Plants/PotatoMine/PotatoMineExplode/1.png",
                imageRenderer);
        super.setState(explode, EXPLODE_DURATION);
    }
}
