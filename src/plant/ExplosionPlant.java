package plant;

import fsm.WaitingPerFrame;
import menu.GameMenu;

import java.awt.*;

public abstract class ExplosionPlant extends Plant implements EdiblePlant {
    private WaitingPerFrame explodingSequence;

    public ExplosionPlant(Point center, Dimension size, Dimension range,
                          int hp, GameMenu menu) {
        super(center, size, range, hp, menu);
    }

    public void setExplodingSequence(int updateInterval,
                                     ExplodingSequence explodingSequence) {
        this.explodingSequence = new WaitingPerFrame(updateInterval,
                explodingSequence);
    }

    public abstract void explode();

    @Override
    public void update() {
        this.explodingSequence.update();
        if (this.explodingSequence.done()) {
            this.explode();
            this.onDamaged(this.hp);
        }
    }

    @Override
    public void render(Graphics graphics) {
        this.explodingSequence.render(graphics);
    }
}
