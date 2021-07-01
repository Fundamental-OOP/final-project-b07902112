package menu;

import menu.GameMenu;
import model.Sprite;

import java.awt.*;

public abstract class Collectable extends Sprite {
    public static final Dimension BODY_OFFSET = new Dimension(0, 0);
    private final Dimension size;
    private final Dimension range;
    protected final GameMenu menu;

    public Collectable(Point center, Dimension size, Dimension range,
                       GameMenu menu) {
        this.location = new Point(
                center.x - size.width / 2, center.y - size.height / 2);
        this.size = size;
        this.range = range;
        this.menu = menu;
    }

    @Override
    public Rectangle getRange() {
        return new Rectangle(this.location, this.range);
    }

    @Override
    public Dimension getBodyOffset() {
        return BODY_OFFSET;
    }

    @Override
    public Dimension getBodySize() {
        return this.size;
    }

    @Override
    public void onDamaged(int damage) {
    }
}
