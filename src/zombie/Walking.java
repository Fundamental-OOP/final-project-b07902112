package zombie;

import fsm.ImageState;
import fsm.Sequence;
import fsm.StateMachine;

import java.awt.*;
import java.util.List;

/**
 * @author - johnny850807@gmail.com (Waterball)
 */
public class Walking extends Sequence {
    private final Zombie zombie;
    private final StateMachine stateMachine;
    private final int speed;

    public Walking(Zombie zombie, StateMachine stateMachine, int speed,
                   List<ImageState> states) {
        super(states);
        this.zombie = zombie;
        this.stateMachine = stateMachine;
        this.speed = speed;
    }

    @Override
    public void update() {
        if (this.zombie.isAlive()) {
            super.update();
            // the zombie will only crawl left slowly
            this.zombie.getWorld().move(
                    this.zombie, new Dimension(-this.speed, 0));
        }
    }

    @Override
    public void onSequenceEnd() {
        this.currentPosition = 0;
        this.stateMachine.reset();
    }

    @Override
    public String toString() {
        return "Walking";
    }
}
