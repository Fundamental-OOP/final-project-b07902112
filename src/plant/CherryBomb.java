package plant;

import fsm.ImageRenderer;
import fsm.ImageState;
import fsm.State;
import media.AudioPlayer;
import menu.GameMenu;
import utils.ImageStateUtils;

import java.awt.*;
import java.util.List;

public class CherryBomb extends ExplosionPlant {
    public static final String AUDIO_EXPLOSION = "CherryBombExplosion";
    public static final Dimension SIZE = new Dimension(112, 81);
    public static final Dimension RANGE = new Dimension(0, 0);
    public static final int UPDATE_INTERVAL = 10;
    public static final int HP = 10000;

    public CherryBomb(Point center, GameMenu menu) {
        super(center, SIZE, RANGE, HP, menu);

        ImageRenderer imageRenderer = new PlantImageRenderer(this);
        List<ImageState> imageStates = ImageStateUtils.imageStatesFromFolder(
                "assets/Plants/CherryBomb", imageRenderer);
        ExplodingSequence explodingSequence =
                new ExplodingSequence(imageStates);
        super.setExplodingSequence(UPDATE_INTERVAL, explodingSequence);
    }

    @Override
    public void explode() {
        Point location = new Point(
                this.getX() + SIZE.width / 2,
                this.getY() + SIZE.height / 2);
        this.getWorld().addSprites(
                new CherryBombExplosion(location, this.menu));
        AudioPlayer.playSounds(AUDIO_EXPLOSION);
    }

    public static class CherryBombFactory implements PlantFactory {
        @Override
        public Plant getPlant(Point location, GameMenu menu) {
            return new CherryBomb(location, menu);
        }
    }
}

class CherryBombExplosion extends Explosion {
    private static final Dimension size_ = new Dimension(246, 185);
    private static final int explodeDuration_ = 10;

    public CherryBombExplosion(Point location, GameMenu menu) {
        super(location, size_, menu);

        ImageRenderer imageRenderer = new PlantImageRenderer(this);
        State explode = ImageStateUtils.imageStateFromPath(
                "assets/Screen/Boom.png", imageRenderer);
        super.setState(explode, explodeDuration_);
    }
}
