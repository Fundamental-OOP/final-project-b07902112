package fsm;

import java.awt.*;
import java.util.List;


/**
 * The sequential gallery, it requires the resource homePath where the images
 * are located. The sequence will contain all the images in the homePath
 * directory, it expects all the image file names are in number form, (e.g.
 * 0.png, 1.png, 2.png,...), so that it can determine the sequence order
 * (ascending).
 *
 * @author johnny850807 (waterball)
 */
public abstract class Sequence implements State {
    protected final List<? extends State> states;
    protected int currentPosition;

    public Sequence(List<? extends State> states) {
        this.states = states;
    }

    @Override
    public synchronized void update() {
        this.states.get(this.currentPosition).update();
        this.currentPosition++;
        if (this.currentPosition >= this.states.size()) {
            this.onSequenceEnd();
        }
    }

    @Override
    public synchronized void render(Graphics g) {
        try {
            this.states.get(this.currentPosition).render(g);
        } catch (Exception ignored) {}
    }

    protected abstract void onSequenceEnd();
}
