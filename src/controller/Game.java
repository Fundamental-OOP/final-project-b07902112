package controller;

import media.AudioPlayer;
import menu.GameOverText;
import views.GameView;
import zombie.Zombie;
import model.Sprite;
import model.World;

public class Game extends GameLoop {
    public static final String AUDIO_NO = "Nooooo";
    public static final int GAME_LOOP_DELAY = 15;
    public static final boolean DEBUG = false;

    public Game(World world) {
        super(world, GAME_LOOP_DELAY);
    }

    public void addSprites(Sprite... sprites) {
        this.world.addSprites(sprites);
    }

    @Override
    public boolean gameIsOver() {
        for (Sprite sprite : this.world.getSprites()) {
            if (sprite instanceof Zombie &&
                    sprite.getBody().getX() <= GameView.HOUSE_X)
                return true;
        }
        return false;
    }

    @Override
    public void gameOver() {
        this.world.addSprites(new GameOverText());
        AudioPlayer.playSounds(AUDIO_NO);
    }
}
