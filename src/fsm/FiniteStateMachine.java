package fsm;

import java.util.HashMap;
import java.util.Map;

/**
 * @author - johnny850807@gmail.com (Waterball)
 */
public class FiniteStateMachine implements StateMachine {
    private final Map<State, Map<Object, State>> transitionTable =
            new HashMap<>();
    private State initialState;
    private State current;

    public FiniteStateMachine() {
    }

    public void setInitialState(State initialState) {
        this.initialState = initialState;
        this.reset();
    }

    public void addTransition(Transition transition) {
        if (!this.transitionTable.containsKey(transition.from)) {
            this.transitionTable.put(transition.from, new HashMap<>());
        }
        this.transitionTable.get(transition.from).put(transition.event,
                transition.to);
    }

    public void trigger(Object event) {
        var transition = this.transitionTable.get(this.current);
        if (transition != null) {
            State to = transition.get(event);
            if (to != null) {
                this.current = to;
            }
        }
    }

    @Override
    public void update() {
        this.currentState().update();
    }

    @Override
    public void reset() {
        this.current = this.initialState;
    }

    @Override
    public State currentState() {
        return this.current;
    }


    public static class Transition {
        private final State from;
        private Object event;
        private State to;

        public Transition(State from) {
            this.from = from;
        }

        public static Transition from(State from) {
            return new Transition(from);
        }

        public Transition when(Object event) {
            this.event = event;
            return this;
        }

        public Transition to(State to) {
            this.to = to;
            return this;
        }

    }
}
