package zombie;

import fsm.*;
import menu.GameMenu;
import model.*;

import java.util.List;

import java.awt.*;

import static fsm.FiniteStateMachine.Transition.from;
import static zombie.Zombie.Event.*;
import static utils.ImageStateUtils.imageStatesFromFolder;


public class FlagZombie extends Zombie {
    private boolean underThreshold;
    private final ImageRenderer imageRenderer;
    private static final String folderPath = "assets/Zombies/FlagZombie/";

    public FlagZombie(int level, Point location) {
        super(level, location);
        this.shape = new SpriteShape(
                new Dimension(this.width, this.height),
                new Dimension(80, this.height / 2),
                new Dimension(this.width / 4, this.height / 3));

        this.imageRenderer = new ZombieImageRenderer(this);
        List<ImageState> walkImageStates = imageStatesFromFolder(
                folderPath + "FlagZombie", this.imageRenderer);
        List<ImageState> attackImageStates = imageStatesFromFolder(
                folderPath + "FlagZombieAttack", this.imageRenderer);
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
        return 15;
    }

    @Override
    public int getThreshold() {
        return 3;
    }

    private void NoHeadZombie() {
        List<ImageState> walkImageStates = imageStatesFromFolder(
                folderPath + "FlagZombieLostHead", this.imageRenderer);
        List<ImageState> attackImageStates = imageStatesFromFolder(
                folderPath + "FlagZombieLostHeadAttack", this.imageRenderer);
        State noHeadWalking = new WaitingPerFrame(5,
                new Walking(this, this.fsm, 2, walkImageStates));
        State noHeadSlowWalking = new WaitingPerFrame(5,
                new Walking(this, this.fsm, 1, walkImageStates));
        State noHeadAttacking = new WaitingPerFrame(5,
                new Attacking(this, this.fsm, attackImageStates));
        this.fsm.setInitialState(noHeadWalking);
        this.fsm.addTransition(from(noHeadWalking).when(ATTACK).to(noHeadAttacking));
        this.fsm.addTransition(from(noHeadWalking).when(SLOW).to(noHeadSlowWalking));

        // animation of losting head
        this.getWorld().addSprites(new ZombieHead(new Point(this.location)));
    }

    @Override
    public void onDamaged(int damage) {
        super.onDamaged(damage);
        if (this.world == null)
            return;
        if (this.HP < this.threshold && !this.underThreshold) {
            this.underThreshold = true;
            this.NoHeadZombie();
        }
        if (this.HP == 0) {
            this.getWorld().addSprites(new ZombieDie(
                    new Point(this.location),
                    "assets/Zombies/NormalZombie/ZombieDie"));
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

    public static class FlagZombieFactory implements ZombieFactory {
        @Override
        public Zombie getZombie(int level, Point location, GameMenu menu) {
            return new FlagZombie(level, location);
        }
    }
}
