package zombie;

import fsm.Sequence;
import fsm.State;
import fsm.StateMachine;
import media.AudioPlayer;
import model.Sprite;
import model.World;

import java.awt.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author - johnny850807@gmail.com (Waterball)
 */
public class Attacking extends Sequence {
    public static final String AUDIO_BITE_1 = "ZombieBite1";
    public static final String AUDIO_BITE_2 = "ZombieBite2";
    public static final String AUDIO_BITE_3 = "ZombieBite3";
    private final Zombie zombie;
    private final StateMachine stateMachine;
    private final Set<Integer> damagingStateNumbers = new HashSet<>(List.of(6));

    public Attacking(Zombie zombie, StateMachine stateMachine,
                     List<? extends State> states) {
        super(states);
        this.zombie = zombie;
        this.stateMachine = stateMachine;
    }

    @Override
    public void update() {
        if (this.zombie.isAlive()) {
            super.update();
            if (this.damagingStateNumbers.contains(this.currentPosition)) {
                this.effectDamage();
            }
        }
    }

    private void effectDamage() {
        World world = this.zombie.getWorld();
        Rectangle damageArea = this.zombie.damageArea();
        var sprites = world.getSprites(damageArea);
        for (Sprite sprite : sprites) {
            if (!(sprite instanceof Zombie)) {
                sprite.onDamaged(this.zombie.getDamage());
            }
        }
        AudioPlayer.playRandomSound(AUDIO_BITE_1, AUDIO_BITE_2, AUDIO_BITE_3);
    }

    @Override
    protected void onSequenceEnd() {
        this.currentPosition = 0;
        this.stateMachine.reset();
    }
}
