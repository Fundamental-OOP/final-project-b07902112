package menu;

import model.MouseHandler;
import model.Sprite;

public class ShovelMouseHandler implements MouseHandler {
    @Override
    public boolean handle(Sprite sprite) {
        if (sprite instanceof Shovel) {
            Shovel shovel = (Shovel) sprite;
            if (shovel.selected())
                shovel.deselect();
            else
                shovel.select();
            return true;
        }
        return false;
    }
}
