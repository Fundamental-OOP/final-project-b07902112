package zombie;

import fsm.*;
import media.AudioPlayer;
import menu.GameMenu;
import model.*;

import java.awt.*;
import java.util.List;

import static fsm.FiniteStateMachine.Transition.from;
import static zombie.Zombie.Event.*;
import static utils.ImageStateUtils.imageStatesFromFolder;


public class NewspaperZombie extends Zombie{
    public static final String AUDIO_TORN = "NewspaperTorn";
    public static final String AUDIO_ENRAGED_1 = "NewspaperZombieEnragedSample1";
    public static final String AUDIO_ENRAGED_2 = "NewspaperZombieEnragedSample2";
    private boolean hasHead = true;
    private boolean hasPaper = true;
    private final ImageRenderer imageRenderer;
    private static final String folderPath = "assets/Zombies/NewspaperZombie/";

    public NewspaperZombie(int level, Point location) {
        super(level, location);
        this.shape = new SpriteShape(
                new Dimension(this.width, this.height),
                new Dimension(40, this.height / 2),
                new Dimension(this.width / 4, this.height / 3));

        this.imageRenderer = new ZombieImageRenderer(this);
        List<ImageState> walkImageStates = imageStatesFromFolder(
                folderPath + "NewspaperZombie",
                this.imageRenderer);
        List<ImageState> attackImageStates = imageStatesFromFolder(
                folderPath + "NewspaperZombieAttack",
                this.imageRenderer);
        State walking = new WaitingPerFrame(5,
                new Walking(this, this.fsm, 2, walkImageStates));
        State slowWalking = new WaitingPerFrame(5,
                new Walking(this, this.fsm, 1, walkImageStates));
        State attacking = new WaitingPerFrame(5,
                new Attacking(this, this.fsm, attackImageStates));

        this.fsm.setInitialState(walking);
        this.fsm.addTransition(from(walking).when(ATTACK).to(attacking));
        this.fsm.addTransition(from(walking).when(SLOW).to(slowWalking));
    }

    @Override
    public int baseHP() {
        return 20;
    }

    @Override
    public int getThreshold() {
        return 15;
    }

    private void NoHeadZombie(){
        AudioPlayer.playSounds(NormalZombie.AUDIO_POP);
        List<ImageState> walkImageStates = imageStatesFromFolder(
                folderPath + "NewspaperZombieLostHead",
                this.imageRenderer);
        List<ImageState> attackImageStates = imageStatesFromFolder(
                folderPath + "NewspaperZombieLostHeadAttack",
                this.imageRenderer);
        State noHeadWalking = new WaitingPerFrame(5,
                new Walking(this, this.fsm, 2, walkImageStates));
        State noHeadSlowWalking = new WaitingPerFrame(5,
                new Walking(this, this.fsm, 1, walkImageStates));
        State noHeadAttacking = new WaitingPerFrame(5,
                new Attacking(this, this.fsm, attackImageStates));
        this.fsm.setInitialState(noHeadWalking);
        this.fsm.addTransition(
                from(noHeadWalking).when(ATTACK).to(noHeadAttacking));
        this.fsm.addTransition(
                from(noHeadWalking).when(SLOW).to(noHeadSlowWalking));

        this.getWorld().addSprites(new ZombieHead(new Point(this.location)));
    }

    private void NoPaperZombie(){
        AudioPlayer.playSounds(AUDIO_TORN);
        AudioPlayer.playRandomSound(AUDIO_ENRAGED_1, AUDIO_ENRAGED_2);
        List<ImageState> walkImageStates = imageStatesFromFolder(
                folderPath + "NewspaperZombieNoPaper",
                this.imageRenderer);
        List<ImageState> attackImageStates = imageStatesFromFolder(
                folderPath + "NewspaperZombieNoPaperAttack",
                this.imageRenderer);
        State noPaperWalking = new WaitingPerFrame(5,
                new Walking(this, this.fsm, 4, walkImageStates));
        State noPaperSlowWalking = new WaitingPerFrame(5,
                new Walking(this, this.fsm, 2, walkImageStates));
        State noPaperAttacking = new WaitingPerFrame(5,
                new Attacking(this, this.fsm, attackImageStates));
        this.fsm.setInitialState(noPaperWalking);
        this.fsm.addTransition(
                from(noPaperWalking).when(ATTACK).to(noPaperAttacking));
        this.fsm.addTransition(
                from(noPaperWalking).when(SLOW).to(noPaperSlowWalking));
    }

    @Override
    public void onDamaged(int damage) {
        super.onDamaged(damage);
        if (this.world == null)
            return;
        if (this.HP < this.threshold && this.hasPaper){
            this.hasPaper = false;
            this.threshold = 3;
            this.NoPaperZombie();
        }
        if (this.HP < this.threshold && this.hasHead){
            this.hasHead = false;
            this.NoHeadZombie();
        }
        if (this.HP == 0){
            this.getWorld().addSprites(new ZombieDie(
                    new Point(this.location),
                    folderPath + "NewspaperZombieDie"));
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

    public static class NewspaperZombieFactory implements ZombieFactory {
        @Override
        public Zombie getZombie(int level, Point location, GameMenu menu) {
            return new NewspaperZombie(level, location);
        }
    }
}
