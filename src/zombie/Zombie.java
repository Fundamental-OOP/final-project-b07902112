package zombie;

import fsm.FiniteStateMachine;
import model.Sprite;
import model.SpriteShape;

import java.awt.*;

import static zombie.Zombie.Event.*;


public abstract class Zombie extends Sprite {
    public static final int WIDTH = 120;
    public static final int HEIGHT = 160;
    protected int HP;
    protected final int damage;
    protected final int height;
    protected final int width;
    protected int threshold;
    protected SpriteShape shape;
    protected final FiniteStateMachine fsm = new FiniteStateMachine();

    public enum Event {
        ATTACK, SLOW
    }

    public Zombie(int level, Point location) {
        this.HP = this.baseHP() + level;
        this.damage = 1;
        this.threshold = this.getThreshold();
        this.height = HEIGHT;
        this.width = WIDTH;
        this.location = new Point((int) location.getX() - this.width / 2,
                                  (int) location.getY() - this.height / 2);
    }

    public abstract int baseHP();

    public abstract int getThreshold();

    public int getDamage(){
        return this.damage;
    }

    public void attack(){
        this.fsm.trigger(ATTACK);
    }

    public void slowDown(){
        this.fsm.trigger(SLOW);
    }

    public abstract void onExploded(int damage);

    @Override
    public void update() {
        this.fsm.update();
    }

    @Override
    public void render(Graphics g) {
        this.fsm.render(g);
    }

    @Override
    public void onDamaged(int damage){
        this.HP = Math.max(0, this.HP - damage);
    }

    @Override
    public Point getLocation() {
        return this.location;
    }

    @Override
    public Rectangle getRange() {
        return new Rectangle(this.location, this.shape.size);
    }

    @Override
    public Dimension getBodyOffset() {
        return this.shape.bodyOffset;
    }

    @Override
    public Dimension getBodySize() {
        return this.shape.bodySize;
    }

    public Rectangle damageArea() {
        return this.getArea(
                new Dimension(30, 90),
                new Dimension(50, 60));
    }
}
