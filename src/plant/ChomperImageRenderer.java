package plant;

import model.Sprite;

import java.awt.*;

public class ChomperImageRenderer extends PlantImageRenderer {
    private static final Dimension chomperImageSize_ = new Dimension(105, 92);

    public ChomperImageRenderer(Sprite sprite) {
        super(sprite);
    }

    @Override
    public void render(Image image, Graphics g) {
        Rectangle body = this.sprite.getBody();
        g.drawImage(image, body.x, body.y,
                chomperImageSize_.width, chomperImageSize_.height, null);
    }
}
