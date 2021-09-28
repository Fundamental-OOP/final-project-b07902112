package model;

import java.awt.*;

/**
 * @author - johnny850807@gmail.com (Waterball)
 */
public abstract class Sprite {
    protected World world;
    protected Point location = new Point();

    public abstract void update();

    public abstract void render(Graphics g);

    public abstract void onDamaged(int damage);

    public World getWorld() {
        return this.world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public Point getLocation() {
        return this.location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public int getX() {
        return this.getRange().x;
    }

    public int getY() {
        return this.getRange().y;
    }

    public abstract Rectangle getRange();

    public abstract Dimension getBodyOffset();

    public abstract Dimension getBodySize();

    public Rectangle getBody() {
        return this.getArea(this.getBodyOffset(), this.getBodySize());
    }

    public Rectangle getArea(Dimension offset, Dimension size) {
        return new Rectangle(new Point(
                offset.width + this.location.x,
                offset.height + this.location.y), size);
    }

    public boolean isAlive() {
        return this.world != null;
    }
}
