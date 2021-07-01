package plant;

import menu.GameMenu;
import model.Sprite;
import views.GameView;

import java.awt.*;

public abstract class Plant extends Sprite {
    public static final int PLANT_DEFAULT_HP = 5;
    public static final Dimension BODY_OFFSET = new Dimension(0, 0);
    private final Dimension size;
    private final Dimension range;
    protected int hp;
    protected final GameMenu menu;
    protected int colIdx;
    protected int rowIdx;

    public Plant(Point center, Dimension size, Dimension range, GameMenu menu) {
        this(center, size, range, PLANT_DEFAULT_HP, menu);
    }

    public Plant(Point center, Dimension size, Dimension range, int hp,
                 GameMenu menu) {
        this.location = new Point(
                center.x - size.width / 2, center.y - size.height / 2);
        this.size = size;
        this.range = range;
        this.hp = hp;
        this.menu = menu;
        this.colIdx =
                GameView.getColIdx(this.location.x + GameView.CELL_WIDTH / 2);
        this.rowIdx =
                GameView.getRowIdx(this.location.y + GameView.CELL_HEIGHT / 2);
    }

    public int getHP() {
        return this.hp;
    }

    @Override
    public void onDamaged(int damage) {
        this.hp -= damage;
        if (this.hp <= 0) {
            this.world = null;
            if (this instanceof CellPlant)
                this.menu.clearCell(this);
        }
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

    public int getRowIdx() {
        return this.rowIdx;
    }

    public int getColIdx() {
        return this.colIdx;
    }
}
