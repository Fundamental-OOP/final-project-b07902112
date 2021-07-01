package plant;

import model.Sprite;

import java.awt.*;

public class SquashImageRenderer extends PlantImageRenderer {
    public static final Dimension SIZE = new Dimension(100, 226);

    public SquashImageRenderer(Sprite sprite) {
        super(sprite);
    }

    @Override
    public void render(Image image, Graphics g) {
        Point location = this.sprite.getLocation();
        g.drawImage(image, location.x, location.y, SIZE.width, SIZE.height,
                null);
    }
}
