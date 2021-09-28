package plant;

import fsm.State;

import java.awt.*;

public class DeathState implements State {
    private final Plant plant;

    public DeathState(Plant plant) {
        this.plant = plant;
    }

    @Override
    public void update() {
        this.plant.onDamaged(this.plant.getHP());
    }

    @Override
    public void render(Graphics graphics) {
    }
}
