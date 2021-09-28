package model;

import java.awt.*;
import java.util.*;
import java.util.List;

import static java.util.stream.Collectors.toSet;

public class World {
    private final CollisionHandler collisionHandler;
    private final CollisionHandler inRangeHandler;
    private final MouseHandler mouseHandler;
    private final List<Sprite> sprites = new LinkedList<>();
    private final List<Sprite> spritesToBeAdded = new ArrayList<>();

    public World(CollisionHandler collisionHandler,
                 InRangeHandler inRangeHandler,
                 MouseHandler mouseHandler,
                 Sprite... sprites) {
        this.collisionHandler = collisionHandler;
        this.inRangeHandler = inRangeHandler;
        this.mouseHandler = mouseHandler;
        this.addSprites(sprites);
    }

    public void addSprites(Sprite... sprites) {
        this.spritesToBeAdded.addAll(Arrays.asList(sprites));
    }

    public Collection<Sprite> getSprites() {
        return this.sprites;
    }

    public Collection<Sprite> getSprites(Rectangle area) {
        return this.sprites.stream()
                .filter(s -> area.intersects(s.getBody()))
                .collect(toSet());
    }

    public void move(Sprite from, Dimension offset) {
        from.getLocation().translate(offset.width, offset.height);
    }

    public void update() {
        // add all sprites here to avoid ConcurrentModificationException
        this.sprites.addAll(this.spritesToBeAdded);
        for (Sprite sprite : this.spritesToBeAdded)
            sprite.setWorld(this);
        this.spritesToBeAdded.clear();

        // update all sprites
        for (Sprite sprite : this.sprites)
            sprite.update();

        // collision and in range detection
        for (Sprite from : this.sprites) {
            for (Sprite to : this.sprites) {
                if (from.getBody().intersects(to.getBody()))
                    this.collisionHandler.handle(from.getLocation(), from, to);
                if (from.getRange().intersects(to.getBody()))
                    this.inRangeHandler.handle(from.getLocation(), from, to);
            }
        }

        // remove sprites if it is dead or out of range
        this.sprites.removeIf(sprite ->
                !sprite.isAlive() ||
                sprite.getBody().x < 0 ||
                sprite.getBody().x > 3000);
    }

    public boolean mouseEvent(Point mouseLocation) {
        List<Sprite> sprites = new ArrayList<>(this.sprites);
        for (Sprite sprite : sprites) {
            if (sprite.getBody().contains(new Point(mouseLocation))) {
                if (this.mouseHandler.handle(sprite))
                    return true;
            }
        }
        return false;
    }

    public void render(Graphics g) {
        List<Sprite> sprites = new ArrayList<>(this.sprites);
        for (Sprite sprite : sprites)
            sprite.render(g);
    }
}
