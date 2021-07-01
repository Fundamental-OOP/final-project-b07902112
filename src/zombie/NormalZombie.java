package zombie;

import fsm.*;
import media.AudioPlayer;
import menu.GameMenu;
import model.*;
import menu.Coin;

import java.awt.*;
import java.util.List;

import static fsm.FiniteStateMachine.Transition.from;
import static zombie.Zombie.Event.*;
import static utils.ImageStateUtils.imageStatesFromFolder;


public class NormalZombie extends Zombie{
    public static final String AUDIO_POP = "ZombieHeadPop";
    private boolean underThreshold;
    private final ImageRenderer imageRenderer;
    private static final String folderPath = "assets/Zombies/NormalZombie/";
    private final GameMenu menu;

    public NormalZombie(int level, Point location, GameMenu menu) {
        super(level, location);
        this.underThreshold = false;
        this.shape = new SpriteShape(
                new Dimension(this.width, this.height),
                new Dimension(60, this.height / 2),
                new Dimension(this.width / 4, this.height / 3));

        this.imageRenderer = new ZombieImageRenderer(this);
        List<ImageState> walkImageStates = imageStatesFromFolder(
                folderPath + "zombie", this.imageRenderer);
        List<ImageState> attackImageStates = imageStatesFromFolder(
                folderPath + "zombieAttack", this.imageRenderer);
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
        return 10;
    }

    @Override
    public int getThreshold() {
        return 3;
    }

    private void NoHeadZombie(){
        AudioPlayer.playSounds(AUDIO_POP);

        List<ImageState> walkImageStates = imageStatesFromFolder(
                folderPath + "ZombieLostHead", this.imageRenderer);
        List<ImageState> attackImageStates = imageStatesFromFolder(
                folderPath + "ZombieLostHeadAttack", this.imageRenderer);
        State noHeadWalking = new WaitingPerFrame(5,
                new Walking(this, this.fsm, 2, walkImageStates));
        State noHeadSlowWalking = new WaitingPerFrame(5,
                new Walking(this, this.fsm, 1, walkImageStates));
        State noHeadAttacking = new WaitingPerFrame(5,
                new Attacking(this, this.fsm, attackImageStates));
        this.fsm.setInitialState(noHeadWalking);
        this.fsm.addTransition(from(noHeadWalking).when(ATTACK).to(noHeadAttacking));
        this.fsm.addTransition(from(noHeadWalking).when(SLOW).to(noHeadSlowWalking));

        this.getWorld().addSprites(new ZombieHead(new Point(this.location)));
    }

    @Override
    public void onDamaged(int damage) {
        super.onDamaged(damage);
        if (this.world == null)
            return;
        if (this.HP < this.threshold && !this.underThreshold){
            this.underThreshold = true;
            this.NoHeadZombie();
        }
        if (this.HP == 0){
            this.getWorld().addSprites(new ZombieDie(
                    new Point(this.location),
                    folderPath + "ZombieDie"));
            Rectangle body = this.getBody();
            Point location = new Point(
                    body.x + body.width / 2, body.y + body.height / 2);
            this.getWorld().addSprites(new Coin(location, this.menu));
            this.world = null;
        }
    }

    @Override
    public void onExploded(int damage){
        super.onDamaged(damage);
        if (this.world != null && this.HP == 0){
            this.getWorld().addSprites(new ZombieBoom(new Point(this.location)));
            this.world = null;
        }
    }

    public static class NormalZombieFactory implements ZombieFactory {
        @Override
        public Zombie getZombie(int level, Point location, GameMenu menu) {
            return new NormalZombie(level, location, menu);
        }
    }
}
