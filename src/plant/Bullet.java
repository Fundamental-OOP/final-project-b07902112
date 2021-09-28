package plant;

import fsm.*;
import media.AudioPlayer;
import menu.GameMenu;
import zombie.Zombie;

import java.awt.*;
import java.util.ArrayList;

import static fsm.FiniteStateMachine.Transition.from;
import static plant.Bullet.Event.*;

public abstract class Bullet extends ZombieDamagingPlant {
    public static final String AUDIO_SPLAT = "ProjectileSplat";
    public static final int HP = 1;
    public static final int DAMAGE = 1;
    private final FiniteStateMachine fsm = new FiniteStateMachine();
    private boolean exploded = false;

    public enum Event {
        COLLIDED
    }

    public Bullet(Point location, Dimension size, GameMenu menu) {
        super(location, size, size, HP, menu);
    }

    public void setStates(State flying, State explode, int explosionDuration) {
        State death = new DeathState(this);
        ArrayList<State> explodeAndDeath = new ArrayList<>();
        explodeAndDeath.add(explode);
        explodeAndDeath.add(death);
        State explodeToDeath = new WaitingPerFrame(
                explosionDuration, new FiniteSequence(explodeAndDeath));

        this.fsm.setInitialState(flying);
        this.fsm.addTransition(from(flying).when(COLLIDED).to(explodeToDeath));
    }

    @Override
    public void update() {
        this.fsm.update();
    }

    @Override
    public void render(Graphics graphics) {
        this.fsm.render(graphics);
    }

    @Override
    public void damage(Zombie zombie) {
        this.fsm.trigger(COLLIDED);
        if (!this.exploded) {
            AudioPlayer.playSounds(AUDIO_SPLAT);
            zombie.onDamaged(DAMAGE);
            this.exploded = true;
        }
    }
}
