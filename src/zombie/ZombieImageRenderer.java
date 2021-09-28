package zombie;

import fsm.ImageRenderer;
import model.Sprite;

import java.awt.*;

/**
 * @author - johnny850807@gmail.com (Waterball)
 */
public class ZombieImageRenderer implements ImageRenderer {
    protected final Sprite sprite;

    public ZombieImageRenderer(Sprite sprite) {
        this.sprite = sprite;
    }

    @Override
    public void render(Image image, Graphics g) {
        Rectangle range = this.sprite.getRange();
        g.drawImage(image, range.x, range.y, range.width, range.height,
                null);
    }
}
