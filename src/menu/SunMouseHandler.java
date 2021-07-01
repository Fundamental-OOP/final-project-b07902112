package menu;

import model.MouseHandler;
import model.Sprite;


public class SunMouseHandler implements MouseHandler {
    @Override
    public boolean handle(Sprite sprite) {
        if (sprite instanceof Sun) {
            Sun sun = (Sun) sprite;
            sun.increaseSun();
            return true;
        }
        return false;
    }
}
