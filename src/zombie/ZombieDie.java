package zombie;

import fsm.FiniteStateMachine;
import fsm.ImageRenderer;
import fsm.State;
import fsm.WaitingPerFrame;
import model.Sprite;
import model.SpriteShape;

import java.awt.*;

import static utils.ImageStateUtils.imageStatesFromFolder;

public class ZombieDie extends Sprite {
    private final SpriteShape shape;
    private final FiniteStateMachine fsm = new FiniteStateMachine();

    public ZombieDie(Point _location, String folderPath) {
        int height = 160;
        int width = 120;
        this.location = _location;
        this.shape = new SpriteShape(
                new Dimension(width, height),
                new Dimension(0, 0),
                new Dimension(0, 0));
        ImageRenderer imageRenderer = new ZombieImageRenderer(this);
        State ending = new WaitingPerFrame(6,
                new Ending(this, imageStatesFromFolder(folderPath,
                        imageRenderer)));
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
