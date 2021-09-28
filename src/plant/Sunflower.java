package plant;

import fsm.*;
import menu.Sun;
import utils.ImageStateUtils;
import menu.GameMenu;

import java.awt.*;
import java.util.List;

public class Sunflower extends Plant implements EdiblePlant {
    public static final Dimension SIZE = new Dimension(73, 74);
    public static final Dimension RANGE = new Dimension(0, 0);
    public static final int UPDATE_INTERVAL = 5;
    // apparently, the sunflower produces a sun every 15000, so this interval
    // is calculated from 15000ms / 15ms where 15ms is the frame time
    public static final int PRODUCE_SUN_INTERVAL = 1000;
    private final WaitingPerFrame sequence;
    private int currentFrame = 0;

    public Sunflower(Point center, GameMenu menu) {
        super(center, SIZE, RANGE, menu);

        ImageRenderer imageRenderer = new PlantImageRenderer(this);
        List<ImageState> imageStates = ImageStateUtils.imageStatesFromFolder(
                "assets/Plants/Sunflower", imageRenderer);
        this.sequence = new WaitingPerFrame(UPDATE_INTERVAL,
                new CyclicSequence(imageStates));
    }

    @Override
    public void update() {
        this.sequence.update();
        if (this.world != null && ++this.currentFrame == PRODUCE_SUN_INTERVAL) {
            this.currentFrame = 0;
            this.getWorld().addSprites(new Sun(this.getLocation(), this.menu));
        }
    }

    @Override
    public void render(Graphics graphics) {
        this.sequence.render(graphics);
    }

    public static class SunflowerPlantFactory implements PlantFactory {
        @Override
        public Plant getPlant(Point location, GameMenu menu) {
            return new Sunflower(location, menu);
        }
    }
}
