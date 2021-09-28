package zombie;

import fsm.*;
import model.Sprite;
import model.SpriteShape;

import java.awt.*;
import java.util.List;

import static utils.ImageStateUtils.imageStatesFromFolder;

public class ZombieBoom extends Sprite {
    private final SpriteShape shape;
    private final FiniteStateMachine fsm = new FiniteStateMachine();

    public ZombieBoom(Point _location){
        int height = 160;
        int width = 120;
        this.location = _location;
        this.shape = new SpriteShape(
                new Dimension(width, height),
                new Dimension(0, 0),
                new Dimension(0, 0));
        ImageRenderer imageRenderer = new ZombieImageRenderer(this);
        List<ImageState> imageStates = imageStatesFromFolder(
                "assets/Zombies/NormalZombie/Boomdie", imageRenderer);
        State ending = new WaitingPerFrame(
                6, new Ending(this, imageStates));
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
