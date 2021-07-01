package plant;

import fsm.ImageRenderer;
import model.Sprite;

import java.awt.*;

public class PlantImageRenderer implements ImageRenderer {
    protected final Sprite sprite;

    public PlantImageRenderer(Sprite sprite) {
        this.sprite = sprite;
    }

    @Override
    public void render(Image image, Graphics g) {
        Rectangle body = this.sprite.getBody();
        g.drawImage(image, body.x, body.y, body.width, body.height, null);
    }
}
