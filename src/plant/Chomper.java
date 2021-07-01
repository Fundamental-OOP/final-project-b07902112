package plant;

import fsm.*;
import media.AudioPlayer;
import menu.GameMenu;
import utils.ImageStateUtils;
import zombie.Zombie;

import java.awt.*;
import java.util.List;

import static fsm.FiniteStateMachine.Transition.from;
import static plant.Chomper.Event.*;

public class Chomper extends ZombieDamagingPlant implements EdiblePlant {
    public static final String AUDIO_EAT = "ChomperEat";
    public static final Dimension SIZE = new Dimension(82, 92);
    public static final Dimension RANGE = SIZE;
    public static final int UPDATE_INTERVAL = 5;
    // apparently, the chomper digests a zombie for 15000ms, so this duration
    // is calculated from 15000ms / 15ms where 15ms is the frame time
    public static final int DIGEST_DURATION = 1000;
    public static final int DAMAGE = 100;
    private final State normal;
    private final State attack;
    private final State digest;
    private final FiniteStateMachine fsm = new FiniteStateMachine();
    private Zombie attackedZombie;
    private int currentFrame;

    public enum Event {
        ATTACK,
        DIGEST,
        DIGESTED
    }

    public Chomper(Point center, GameMenu menu) {
        super(center, SIZE, RANGE, menu);

        ImageRenderer imageRenderer = new ChomperImageRenderer(this);
        this.normal = WaitingPerFrame.cyclicImageStates(
                "assets/Plants/Chomper/Chomper", imageRenderer,
                UPDATE_INTERVAL);
        List<ImageState> attackImageStates =
                ImageStateUtils.imageStatesFromFolder(
                "assets/Plants/Chomper/ChomperAttack", imageRenderer);
        this.attack = new WaitingPerFrame(UPDATE_INTERVAL,
                new ChomperAttackSequence(attackImageStates));
        this.digest = WaitingPerFrame.cyclicImageStates(
                "assets/Plants/Chomper/ChomperDigest", imageRenderer,
                UPDATE_INTERVAL);

        this.fsm.setInitialState(this.normal);
        this.fsm.addTransition(from(this.normal).when(ATTACK).to(this.attack));
        this.fsm.addTransition(from(this.attack).when(DIGEST).to(this.digest));
        this.fsm.addTransition(from(this.digest).when(DIGESTED).to(this.normal));

        this.currentFrame = 0;
    }

    @Override
    public void update() {
        this.fsm.update();
        State currentState = this.fsm.currentState();
        if (currentState == this.attack && currentState.done()) {
            AudioPlayer.playSounds(Chomper.AUDIO_EAT);
            this.fsm.trigger(DIGEST);
            if (this.attackedZombie.isAlive())
                this.attackedZombie.onDamaged(DAMAGE);
        } else if (currentState == this.digest &&
                ++this.currentFrame == DIGEST_DURATION) {
            this.fsm.trigger(DIGESTED);
            this.currentFrame = 0;
        }
    }

    @Override
    public void render(Graphics graphics) {
        this.fsm.render(graphics);
    }

    @Override
    public void damage(Zombie zombie) {
        if (this.fsm.currentState() == this.normal) {
            this.fsm.trigger(ATTACK);
            this.attackedZombie = zombie;
        }
    }

    public static class ChomperFactory implements PlantFactory {
        @Override
        public Plant getPlant(Point location, GameMenu menu) {
            return new Chomper(location, menu);
        }
    }
}

class ChomperAttackSequence extends FiniteSequence {
    public ChomperAttackSequence(List<? extends State> states) {
        super(states);
    }

    @Override
    public boolean done() {
        if (this.currentPosition == this.states.size() - 1) {
            // we reset to the first state in the sequence so that when the attack
            // is triggered again, we will start from the first attack state and
            // done() should return false
            this.currentPosition = 0;
            return true;
        }
        return false;
    }
}
