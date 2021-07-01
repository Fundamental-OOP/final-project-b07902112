package plant;

import fsm.*;
import menu.GameMenu;

import java.awt.*;

import static fsm.FiniteStateMachine.Transition.from;
import static plant.Wallnut.Event.*;

public class Wallnut extends Plant implements EdiblePlant {
    private static final Dimension SIZE = new Dimension(65, 73);
    private static final Dimension RANGE = new Dimension(0, 0);
    private static final int HP = 30;
    private static final int UPDATE_INTERVAL = 5;
    private final FiniteStateMachine fsm = new FiniteStateMachine();

    public enum Event {
        CRACK1, CRACK2
    }

    private static final int crack1HP_ = 20;
    private static final int crack2HP_ = 10;

    public Wallnut(Point center, GameMenu menu) {
        super(center, SIZE, RANGE, HP, menu);

        ImageRenderer imageRenderer = new PlantImageRenderer(this);
        State normal = WaitingPerFrame.cyclicImageStates(
                "assets/Plants/Wallnut/Wallnut", imageRenderer,
                UPDATE_INTERVAL);
        State cracked1 = WaitingPerFrame.cyclicImageStates(
                "assets/Plants/Wallnut/Wallnut_cracked1", imageRenderer,
                UPDATE_INTERVAL);
        State cracked2 = WaitingPerFrame.cyclicImageStates(
                "assets/Plants/Wallnut/Wallnut_cracked2", imageRenderer,
                UPDATE_INTERVAL);

        this.fsm.setInitialState(normal);
        this.fsm.addTransition(from(normal).when(CRACK1).to(cracked1));
        this.fsm.addTransition(from(cracked1).when(CRACK2).to(cracked2));
    }

    @Override
    public void update() {
        this.fsm.update();
    }

    @Override
    public void render(Graphics graphics) {
        this.fsm.render(graphics);
    }

    @Override
    public void onDamaged(int damage) {
        super.onDamaged(damage);
        if (super.hp == crack1HP_)
            this.fsm.trigger(CRACK1);
        if (super.hp == crack2HP_)
            this.fsm.trigger(CRACK2);
    }

    public static class WallnutFactory implements PlantFactory {
        @Override
        public Plant getPlant(Point location, GameMenu menu) {
            return new Wallnut(location, menu);
        }
    }
}
