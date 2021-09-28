import menu.*;
import zombie.*;
import controller.Game;
import media.AudioPlayer;
import model.*;
import plant.*;
import views.GameView;

public class Main {
    public static void main(String[] args) {
        CollisionHandler collisionHandler = new JointCollisionHandler(
                new ZombieDamagingPlantCollisionHandler(),
                new ZombieCollisionHandler());
        InRangeHandler inRangeHandler = new JointInRangeHandler(
                new ShooterPlantInRangeHandler());
        MouseHandler mouseHandler = new JointMouseHandler(
                new SunMouseHandler(),
                new CardMouseHandler(),
                new ShovelMouseHandler(),
                new CoinMouseHandler());
        World world = new World(collisionHandler, inRangeHandler, mouseHandler);
        Game game = new Game(world);
        GameView gameView = new GameView(game);
        game.start();
        gameView.launch();

        AudioPlayer.addAudioByFileName("Music");
        AudioPlayer.addAudioByFileName(Chomper.AUDIO_EAT);
        AudioPlayer.addAudioByFileName(CherryBomb.AUDIO_EXPLOSION);
        AudioPlayer.addAudioByFileName(Attacking.AUDIO_BITE_1);
        AudioPlayer.addAudioByFileName(Attacking.AUDIO_BITE_2);
        AudioPlayer.addAudioByFileName(Attacking.AUDIO_BITE_3);
        AudioPlayer.addAudioByFileName(Coin.AUDIO_DROP);
        AudioPlayer.addAudioByFileName(Coin.AUDIO_COLLECT);
        AudioPlayer.addAudioByFileName(PotatoMine.AUDIO_READY);
        AudioPlayer.addAudioByFileName(PotatoMine.AUDIO_EXPLODE);
        AudioPlayer.addAudioByFileName(NormalZombie.AUDIO_POP);
        AudioPlayer.addAudioByFileName(NewspaperZombie.AUDIO_TORN);
        AudioPlayer.addAudioByFileName(NewspaperZombie.AUDIO_ENRAGED_1);
        AudioPlayer.addAudioByFileName(NewspaperZombie.AUDIO_ENRAGED_2);
        AudioPlayer.addAudioByFileName(GameMenu.AUDIO_PLANT);
        AudioPlayer.addAudioByFileName(ConeheadZombie.AUDIO_HIT);
        AudioPlayer.addAudioByFileName(BucketheadZombie.AUDIO_HIT);
        AudioPlayer.addAudioByFileName(Sun.AUDIO_COLLECT);
        AudioPlayer.addAudioByFileName(Game.AUDIO_NO);
        AudioPlayer.addAudioByFileName(Shovel.AUDIO_PICKED);
        AudioPlayer.addAudioByFileName(Bullet.AUDIO_SPLAT);
        AudioPlayer.addAudioByFileName(Jalapeno.AUDIO_EXPLOSION);
        AudioPlayer.addAudioByFileName(Squash.AUDIO_AIM);
        AudioPlayer.addAudioByFileName(Squash.AUDIO_LAND);
        AudioPlayer.playLoopSound("Music");
    }
}
