package zombie;

import controller.Game;
import menu.GameMenu;
import model.Sprite;

import java.awt.*;
import java.util.Random;

import static views.GameView.*;

public class ZombieGenerator extends Sprite {
    public static final int LEVEL_UPDATE = 15;
    public static final int INIT_DURATION = 3000;
    private int level = 1;
    private final Random random = new Random(1234);
    private int initCounter = 0;
    private int spawnInterval = 1000 / this.level;
    private int frameCounter = 0;
    private int zombieCounter = 0;
    private final GameMenu menu;
    private final ZombieFactory[] zombieFactories = {
        new NormalZombie.NormalZombieFactory(),
        new ConeheadZombie.ConeheadZombieFactory(),
        new BucketheadZombie.BucketheadZombieFactory(),
        new NewspaperZombie.NewspaperZombieFactory()
    };
    private final ZombieFactory levelUpZombieFactory =
            new FlagZombie.FlagZombieFactory();

    public ZombieGenerator(GameMenu menu) {
        this.menu = menu;
        if (Game.DEBUG) {
            this.level = 5;
            this.initCounter = INIT_DURATION - 1;
            this.spawnInterval = 1000 / this.level;
        }
    }

    @Override
    public void update() {
        if (++this.initCounter < INIT_DURATION)
            return;
        this.initCounter = INIT_DURATION;
        if (++this.frameCounter == this.spawnInterval) {
            this.frameCounter = 0;
            for (int i = 0; i < Math.min(this.level, 5); ++i) {
                Point location = new Point(
                        WIDTH,
                        TOP_LEFT_Y + this.random.nextInt(NUM_ROWS) * CELL_HEIGHT);
                ZombieFactory zombieFactory = this.zombieFactories[
                        this.random.nextInt(Math.min(
                                this.level, this.zombieFactories.length))];
                this.world.addSprites(zombieFactory.getZombie(
                        this.level, location, this.menu));
            }
            if (++this.zombieCounter == LEVEL_UPDATE) {
                ++this.level;
                this.spawnInterval = 1000 / this.level;
                this.zombieCounter = 0;
                Point location = new Point(
                        WIDTH,
                        TOP_LEFT_Y + NUM_ROWS / 2 * CELL_HEIGHT);
                this.world.addSprites(this.levelUpZombieFactory.getZombie(
                        this.level, location, this.menu));
            }
        }
    }

    @Override
    public void render(Graphics g) {
    }

    @Override
    public void onDamaged(int damage) {
    }

    @Override
    public Rectangle getRange() {
        return new Rectangle(0, 0, 0, 0);
    }

    @Override
    public Dimension getBodyOffset() {
        return new Dimension(0, 0);
    }

    @Override
    public Dimension getBodySize() {
        return new Dimension(0, 0);
    }
}
