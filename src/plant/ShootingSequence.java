package plant;

import fsm.FiniteSequence;
import fsm.State;

import java.util.List;

public abstract class ShootingSequence extends FiniteSequence {
    protected final ShooterPlant plant;

    public ShootingSequence(List<? extends State> states, ShooterPlant plant) {
        super(states);
        this.plant = plant;
    }

    @Override
    public boolean done() {
        if (this.currentPosition == this.states.size() - 1) {
            // we reset to the first state in the sequence so that when the
            // attack is triggered again, we will start from the first attack
            // state and done() should return false
            this.currentPosition = 0;
            return true;
        }
        return false;
    }
}
