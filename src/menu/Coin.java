package menu;

import fsm.*;
import media.AudioPlayer;
import plant.PlantImageRenderer;
import utils.ImageStateUtils;

import java.awt.*;
import java.util.ArrayList;

public class Coin extends Collectable {
    public static final String AUDIO_DROP = "CoinDrop";
    public static final String AUDIO_COLLECT = "CoinCollection";
    public static final Dimension SIZE = new Dimension(78 / 2, 78 / 2);
    public static final Dimension RANGE = new Dimension(0, 0);
    public static final int UPDATE_INTERVAL = 10;
    // apparently, a sun is alive for 7000ms, thus the alive interval is
    // calculated as 7000ms / 15ms where 15ms is the frame time
    public static final int ALIVE_INTERVAL = 467;
    private final WaitingPerFrame sequence;
    private int currentFrame = 0;

    public Coin(Point location, GameMenu menu) {
        super(location, SIZE, RANGE, menu);

        ImageRenderer imageRenderer = new PlantImageRenderer(this);
        State imageState = ImageStateUtils.imageStateFromPath(
                "assets/Cards/coin.png", imageRenderer);
        ArrayList<State> imageStates = new ArrayList<>();
        imageStates.add(imageState);
        this.sequence = new WaitingPerFrame(UPDATE_INTERVAL,
                new CyclicSequence(imageStates));

        AudioPlayer.playSounds(AUDIO_DROP);
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

    public void increaseMoney() {
        AudioPlayer.playSounds(AUDIO_COLLECT);
        this.menu.increaseMoney();
        this.world = null;
    }
}
