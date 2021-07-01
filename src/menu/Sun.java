package menu;

import fsm.*;
import media.AudioPlayer;
import plant.PlantImageRenderer;
import utils.ImageStateUtils;

import java.awt.*;
import java.util.List;

public class Sun extends Collectable {
    public static final String AUDIO_COLLECT = "SunCollected";
    private static final Dimension SIZE = new Dimension(78, 78);
    private static final Dimension RANGE = new Dimension(0, 0);
    private static final int UPDATE_INTERVAL = 10;
    // apparently, a sun is alive for 7000ms, thus the alive interval is
    // calculated as 7000ms / 15ms where 15ms is the frame time
    private static final int ALIVE_INTERVAL = 467;
    private final WaitingPerFrame sequence;
    private int currentFrame = 0;

    public Sun(Point location, GameMenu menu) {
        super(location, SIZE, RANGE, menu);

        ImageRenderer imageRenderer = new PlantImageRenderer(this);
        List<ImageState> imageStates = ImageStateUtils.imageStatesFromFolder(
                "assets/Plants/Sun", imageRenderer);
        this.sequence = new WaitingPerFrame(UPDATE_INTERVAL,
                new CyclicSequence(imageStates));
    }

    @Override
    public void update() {
        this.sequence.update();
        if (++this.currentFrame == ALIVE_INTERVAL)
            this.world = null;
    }

    @Override
    public void render(Graphics graphics) {
        this.sequence.render(graphics);
    }

    public void increaseSun() {
        AudioPlayer.playSounds(AUDIO_COLLECT);
        this.menu.increaseSun();
        this.world = null;
    }
}
