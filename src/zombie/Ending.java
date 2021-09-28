package zombie;

import fsm.ImageState;
import fsm.Sequence;
import model.Sprite;

import java.util.List;

/**
 * @author - johnny850807@gmail.com (Waterball)
 */
// This class handle all zombies' ending animation
// After the animation finish, remove the spite from the world
public class Ending extends Sequence {
    private final Sprite sprite;

    public Ending(Sprite sprite, List<ImageState> states) {
        super(states);
        this.sprite = sprite;
    }

    @Override
    protected void onSequenceEnd() {
        this.sprite.setWorld(null);
    }

    @Override
    public void update() {
        if (this.sprite.isAlive()) {
            super.update();
        }
    }

    @Override
    public String toString() {
        return "Ending";
    }
}
