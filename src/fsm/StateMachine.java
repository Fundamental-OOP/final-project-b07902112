package fsm;

import java.awt.*;

/**
 * @author - johnny850807@gmail.com (Waterball)
 */
public interface StateMachine extends State {

    @Override
    default void render(Graphics g) {
        this.currentState().render(g);
    }

    State currentState();

    void reset();
}
