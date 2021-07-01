package fsm;

import java.util.List;

public class FiniteSequence extends Sequence {
    public FiniteSequence(List<? extends State> states) {
        super(states);
    }

    @Override
    protected void onSequenceEnd() {
        this.currentPosition = this.currentPosition - 1;
    }
}
