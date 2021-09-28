package zombie;

import fsm.*;
import media.AudioPlayer;
import menu.GameMenu;
import model.*;

import java.util.List;

import java.awt.*;

import static fsm.FiniteStateMachine.Transition.from;
import static zombie.Zombie.Event.*;
import static utils.ImageStateUtils.imageStatesFromFolder;


public class ConeheadZombie extends Zombie {
    public static final String AUDIO_HIT = "ConeheadHit";
    private static final String folderPath = "assets/Zombies/ConeheadZombie/";
    private final GameMenu menu;

    public ConeheadZombie(int level, Point location, GameMenu menu) {
        super(level, location);
        this.shape = new SpriteShape(
                new Dimension(this.width, this.height),
                new Dimension(75, this.height / 2),
                new Dimension(this.width / 4, this.height / 3));

        ImageRenderer imageRenderer = new ZombieImageRenderer(this);
        List<ImageState> walkImageStates = imageStatesFromFolder(
                folderPath + "ConeheadZombie", imageRenderer);
        List<ImageState> attackImageStates = imageStatesFromFolder(
                folderPath + "ConeheadZombieAttack", imageRenderer);
        State walking = new WaitingPerFrame(5,
                new Walking(this, this.fsm, 2, walkImageStates));
        State slowWalking = new WaitingPerFrame(5,
                new Walking(this, this.fsm, 1, walkImageStates));
        State attacking = new WaitingPerFrame(5,
                new Attacking(this, this.fsm, attackImageStates));

        this.fsm.setInitialState(walking);
        this.fsm.addTransition(from(walking).when(ATTACK).to(attacking));
        this.fsm.addTransition(from(walking).when(SLOW).to(slowWalking));

        this.menu = menu;
    }

    @Override
    public int baseHP() {
        return 20;
    }

    @Override
    public int getThreshold() {
        return 10;
    }

    @Override
    public void onDamaged(int damage) {
        super.onDamaged(damage);
        if (this.world == null)
            return;
        AudioPlayer.playSounds(AUDIO_HIT);
        if (this.HP == 0) {
            this.getWorld().addSprites(new ZombieDie(
                    new Point(this.location),
                    "assets/Zombies/NormalZombie/ZombieDie"));
            this.world = null;
        } else if (this.HP <= this.threshold) {
            this.getWorld().addSprites(new NormalZombie(this.damage,
                    new Point((int) this.location.getX() + WIDTH / 2,
                            (int) this.location.getY() + HEIGHT / 2),
                    this.menu));
            this.world = null;
        }
    }

    @Override
    public void onExploded(int damage) {
        super.onDamaged(damage);
        if (this.world != null && this.HP == 0) {
            this.getWorld().addSprites(new ZombieBoom(new Point(this.location)));
            this.world = null;
        }
    }

    public static class ConeheadZombieFactory implements ZombieFactory {
        @Override
        public Zombie getZombie(int level, Point location, GameMenu menu) {
            return new ConeheadZombie(level, location, menu);
        }
    }
}
