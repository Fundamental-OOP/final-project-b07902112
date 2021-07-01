package plant;

import fsm.State;

import java.util.List;

class PeashooterShootingSequence extends ShootingSequence {
    private static final int ATTACK_POS = 2;

    public PeashooterShootingSequence(List<? extends State> states,
                                      ShooterPlant plant) {
        super(states, plant);
    }

    @Override
    public void update() {
        super.update();
        if (this.currentPosition == ATTACK_POS)
            this.plant.shoot();
    }
}