package plant;

import zombie.Zombie;
import fsm.*;
import media.AudioPlayer;
import menu.GameMenu;
import utils.ImageStateUtils;
import views.GameView;

import java.util.List;

import java.awt.*;

import static fsm.FiniteStateMachine.Transition.from;
import static plant.Squash.Event.AIM;
import static views.GameView.*;

public class Squash extends ZombieDamagingPlant implements CellPlant {
    public static final String AUDIO_AIM = "SquashRealize2";
    public static final String AUDIO_LAND = "SquashLanding";
    public static final Dimension SIZE = new Dimension(
            3 * CELL_WIDTH, GameView.CELL_HEIGHT);
    public static final Dimension BODY_OFFSET = new Dimension(
            -CELL_WIDTH, 130);
    private static final Dimension RANGE = new Dimension(0, 0);
    private static final int UPDATE_INTERVAL = 5;
    private static final int HP = 10000;
    private static final int AIM_DURATION = 30;
    private int frameCounter = 0;
    private final FiniteStateMachine fsm = new FiniteStateMachine();
    private final State normal;
    private final State aim;

    public enum Event {
        AIM
    }

    public Squash(Point center, GameMenu menu) {
        super(new Point(center.x + CELL_WIDTH - 10,
                center.y - CELL_HEIGHT - 45), SIZE, RANGE, HP, menu);

        ImageRenderer imageRenderer = new SquashImageRenderer(this);
        this.normal = WaitingPerFrame.cyclicImageStates(
                "assets/Plants/Squash/Squash", imageRenderer,
                UPDATE_INTERVAL);
        this.aim = WaitingPerFrame.cyclicImageStates(
                "assets/Plants/Squash/SquashAim", imageRenderer,
                UPDATE_INTERVAL);

        this.fsm.setInitialState(this.normal);
        this.fsm.addTransition(from(this.normal).when(AIM).to(this.aim));

        this.colIdx = (this.location.x + 50 - GameView.TOP_LEFT_X) /
                GameView.CELL_WIDTH;
        this.rowIdx = (this.location.y + 184 - GameView.TOP_LEFT_Y) /
                GameView.CELL_HEIGHT;
    }

    @Override
    public void update() {
        this.fsm.update();
    }

    @Override
    public void render(Graphics g) {
        this.fsm.render(g);
    }

    @Override
    public void damage(Zombie zombie) {
        if (this.fsm.currentState() == this.normal) {
            this.fsm.trigger(AIM);
            AudioPlayer.playSounds(AUDIO_AIM);
        } else if (this.fsm.currentState() == this.aim &&
                ++this.frameCounter == AIM_DURATION) {
            Rectangle body = zombie.getBody();
            Point center = new Point(
                    GameView.getCellAlignedX(body.x),
                    GameView.getCellAlignedY(body.y));
            Point location = new Point(
                    center.x - 10, center.y - CELL_HEIGHT - 45);
            this.getWorld().addSprites(new SquashExplosion(location, this.menu));
            this.onDamaged(this.hp);
        }
    }

    @Override
    public Dimension getBodyOffset() {
        return BODY_OFFSET;
    }

    public static class SquashFactory implements PlantFactory {
        @Override
        public Plant getPlant(Point location, GameMenu menu) {
            return new Squash(location, menu);
        }
    }
}

class SquashExplosion extends Explosion {
    public static final Dimension SIZE = new Dimension(
            CELL_WIDTH, CELL_HEIGHT);
    public static final int EXPLODE_DURATION = 10;
    public static final Dimension BODY_OFFSET = new Dimension(0, 130);

    public SquashExplosion(Point location, GameMenu menu) {
        super(location, SIZE, menu);

        ImageRenderer imageRenderer = new SquashImageRenderer(this);
        List<ImageState> explodeStates = ImageStateUtils.imageStatesFromFolder(
                "assets/Plants/Squash/SquashAttack/",
                imageRenderer);
        super.setState(explodeStates, EXPLODE_DURATION);
    }

    @Override
    public Dimension getBodyOffset() {
        return BODY_OFFSET;
    }

    @Override
    public void damage(Zombie zombie) {
        if (!this.done())
            return;
        AudioPlayer.playSounds(Squash.AUDIO_LAND);
        if (!this.damagedZombies.contains(zombie)) {
            this.damagedZombies.add(zombie);
            zombie.onDamaged(DAMAGE);
        }
    }
}