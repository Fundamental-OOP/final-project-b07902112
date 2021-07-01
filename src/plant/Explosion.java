package plant;

import fsm.State;
import fsm.WaitingPerFrame;
import menu.GameMenu;
import zombie.Zombie;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;

public class Explosion extends ZombieDamagingPlant {
    public static final int DAMAGE = 100;
    protected WaitingPerFrame explodeToDeath;
    protected HashSet<Zombie> damagedZombies;
    private ExplodingSequence explodeToDeathSequence;
    private State lastExplodeState;

    public Explosion(Point location, Dimension size, GameMenu menu) {
        super(location, size, size, menu);
    }

    public void setState(State explode, int explosionDuration) {
        List<State> explodeStates = new ArrayList<>();
        explodeStates.add(explode);
        this.setState(explodeStates, explosionDuration);
    }

    public void setState(List<? extends State> explode, int updateInterval) {
        State death = new DeathState(this);
        List<State> explodeAndDeath = new ArrayList<>(explode);
        explodeAndDeath.add(death);
        this.lastExplodeState = explode.get(explode.size() - 1);

        this.explodeToDeathSequence = new ExplodingSequence(explodeAndDeath);
        this.explodeToDeath = new WaitingPerFrame(updateInterval,
                this.explodeToDeathSequence);
        this.damagedZombies = new HashSet<>();
    }

    @Override
    public void update() {
        this.explodeToDeath.update();
    }

    @Override
    public void render(Graphics graphics) {
        this.explodeToDeath.render(graphics);
    }

    @Override
    public void damage(Zombie zombie) {
        if (!this.damagedZombies.contains(zombie)) {
            this.damagedZombies.add(zombie);
            zombie.onExploded(DAMAGE);
        }
    }

    public boolean done() {
        return this.explodeToDeathSequence.currentState() ==
                this.lastExplodeState;
    }
}
