package plant;

import fsm.State;
import model.World;

import java.awt.*;

public class BulletFlyingState implements State {
    public static final Dimension VELOCITY = new Dimension(5, 0);
    private final Bullet bullet;
    private final State state;

    public BulletFlyingState(Bullet bullet, State state) {
        this.bullet = bullet;
        this.state = state;
    }

    @Override
    public void update() {
        this.state.update();
        World world = this.bullet.getWorld();
        if (world != null)
            world.move(this.bullet, VELOCITY);
    }

    @Override
    public void render(Graphics graphics) {
        this.state.render(graphics);
    }
}
