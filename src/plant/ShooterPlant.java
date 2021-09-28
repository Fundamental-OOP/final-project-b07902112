package plant;

import fsm.FiniteStateMachine;
import fsm.State;
import menu.GameMenu;

import java.awt.*;

import static fsm.FiniteStateMachine.Transition.from;
import static plant.ShooterPlant.Event.*;

public abstract class ShooterPlant extends Plant implements EdiblePlant {
    private final FiniteStateMachine fsm = new FiniteStateMachine();

    public enum Event {
        IDLE, ATTACK
    }

    public ShooterPlant(Point center, Dimension size, Dimension range,
                        GameMenu menu) {
        super(center, size, range, menu);
    }

    public void setStates(State idle, State attack) {
        this.fsm.setInitialState(idle);
        this.fsm.addTransition(from(idle).when(ATTACK).to(attack));
        this.fsm.addTransition(from(attack).when(IDLE).to(idle));
    }

    public void triggerAttack() {
        this.fsm.trigger(ATTACK);
    }

    public abstract void shoot();

    @Override
    public void update() {
        if (this.fsm.currentState().done())
            this.fsm.trigger(IDLE);
        this.fsm.update();
    }

    @Override
    public void render(Graphics graphics) {
        this.fsm.render(graphics);
    }

    protected Point getBulletStartingLocation(Dimension bulletSize) {
        return new Point(this.getLocation().x + bulletSize.width / 2,
                this.getLocation().y + bulletSize.height / 2);
    }
}
