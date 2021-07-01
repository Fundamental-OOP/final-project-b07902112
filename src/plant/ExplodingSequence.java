package plant;

import fsm.FiniteSequence;
import fsm.State;

import java.util.List;

public class ExplodingSequence extends FiniteSequence {
    private boolean done = false;

    public ExplodingSequence(List<? extends State> states) {
        super(states);
    }

    @Override
    protected void onSequenceEnd() {
        super.onSequenceEnd();
        this.done = true;
    }

    @Override
    public boolean done() {
        return this.done;
    }

    public State currentState() {
        return this.states.get(this.currentPosition);
    }
}
