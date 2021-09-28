package fsm;

import utils.ImageStateUtils;

import java.awt.*;
import java.util.List;

/**
 * @author - johnny850807@gmail.com (Waterball)
 */
public class WaitingPerFrame implements State {
    private final State state;
    private final long waitingLoopPerFrame;
    private long remaining;

    public WaitingPerFrame(long waitingLoopPerFrame, State state) {
        this.remaining = this.waitingLoopPerFrame = waitingLoopPerFrame;
        this.state = state;
    }


    public static WaitingPerFrame cyclicImageStates(String path,
                                                    ImageRenderer imageRenderer,
                                                    int updateInterval) {
        List<? extends State> imageStates =
                ImageStateUtils.imageStatesFromFolder(
                path, imageRenderer);
        return new WaitingPerFrame(updateInterval,
                new CyclicSequence(imageStates));
    }

    @Override
    public boolean done() {
        return this.state.done();
    }

    @Override
    public void update() {
        if (--this.remaining <= 0) {
            this.remaining = this.waitingLoopPerFrame;
            this.state.update();
        }
    }

    @Override
    public void render(Graphics g) {
        this.state.render(g);
    }

    @Override
    public String toString() {
        return this.state.toString();
    }
}
