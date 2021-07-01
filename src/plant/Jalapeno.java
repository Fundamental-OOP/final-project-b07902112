package plant;

import fsm.*;
import media.AudioPlayer;
import menu.GameMenu;
import utils.ImageStateUtils;
import views.GameView;

import java.awt.*;
import java.util.List;

public class Jalapeno extends ExplosionPlant {
    public static final String AUDIO_EXPLOSION = "JalapenoExplosion";
    public static final Dimension SIZE = new Dimension(112, 81);
    public static final Dimension RANGE = new Dimension(0, 0);
    public static final int UPDATE_INTERVAL = 10;
    private static final int HP = 10000;

    public Jalapeno(Point center, GameMenu menu) {
        super(center, SIZE, RANGE, HP, menu);

        ImageRenderer imageRenderer = new PlantImageRenderer(this);
        List<ImageState> imageStates = ImageStateUtils.imageStatesFromFolder(
                "assets/Plants/Jalapeno/Jalapeno", imageRenderer);
        ExplodingSequence explodingSequence = new ExplodingSequence(imageStates);
        super.setExplodingSequence(UPDATE_INTERVAL, explodingSequence);
    }

    @Override
    public void explode() {
        Point location = new Point(
                GameView.MID_X, this.getY() + SIZE.height / 2);
        this.getWorld().addSprites(
                new JalapenoExplosion(location, this.menu));
        AudioPlayer.playSounds(AUDIO_EXPLOSION);
    }

    public static class JalapenoFactory implements PlantFactory {
        @Override
        public Plant getPlant(Point location, GameMenu menu) {
            return new Jalapeno(location, menu);
        }
    }
}

class JalapenoExplosion extends Explosion {
    public static final Dimension SIZE = new Dimension(
            755, GameView.CELL_HEIGHT);
    public static final int UPDATE_INTERVAL = 10;

    public JalapenoExplosion(Point location, GameMenu menu) {
        super(location, SIZE, menu);

        ImageRenderer imageRenderer = new PlantImageRenderer(this);
        List<ImageState> explodeStates = ImageStateUtils.imageStatesFromFolder(
                "assets/Plants/Jalapeno/JalapenoExplode", imageRenderer);
        super.setState(explodeStates, UPDATE_INTERVAL);
    }
}
