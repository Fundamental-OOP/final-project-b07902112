package zombie;

import fsm.*;
import model.Sprite;
import model.SpriteShape;

import java.awt.*;
import java.util.List;

import static utils.ImageStateUtils.imageStatesFromFolder;

public class ZombieHead extends Sprite {
    private final SpriteShape shape;
    private final FiniteStateMachine fsm = new FiniteStateMachine();

    public ZombieHead(Point location) {
        int height = 160;
        int width = 120;
        this.location = location;
        // Image offset
        this.location.translate(80, 0);
        this.shape = new SpriteShape(
                new Dimension(width, height),
                new Dimension(0, 0),
                new Dimension(0, 0));
        ImageRenderer imageRenderer = new ZombieImageRenderer(this);
        List<ImageState> imageStates = imageStatesFromFolder(
                "assets/Zombies/NormalZombie/ZombieHead",
                imageRenderer);
        State ending = new WaitingPerFrame(5,
                new Ending(this, imageStates));
        this.fsm.setInitialState(ending);
    }

    @Override
    public void update() {
        this.fsm.update();
    }

    @Override
    public void render(Graphics g) {
        this.fsm.render(g);
    }

    @Override
    public void onDamaged(int damage) {
        // No effect
    }

    @Override
    public Point getLocation() {
        return this.location;
    }

    @Override
    public Rectangle getRange() {
        return new Rectangle(this.location, this.shape.size);
    }

    @Override
    public Dimension getBodyOffset() {
        return this.shape.bodyOffset;
    }

    @Override
    public Dimension getBodySize() {
        return this.shape.bodySize;
    }
}