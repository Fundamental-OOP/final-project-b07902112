package menu;

import model.Sprite;
import views.GameView;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Path;

public class Background extends Sprite {
    public static final Point LOCATION = new Point(0, 0);
    public static final Dimension SIZE = new Dimension(
            GameView.BACKGROUND_WIDTH, GameView.BACKGROUND_HEIGHT);
    public static final Rectangle RANGE = new Rectangle(0, 0, 0, 0);
    public static final Dimension BODY_OFFSET = new Dimension(0, 0);
    private Image backgroundImage;

    public Background() {
        try {
            this.backgroundImage = ImageIO.read(
                    Path.of("assets/Items/Background/Background_0.jpg")
                            .toFile());
        } catch (IOException e) {
            System.err.println("[Error] unable to read background image");
            System.exit(-1);
        }
    }

    @Override
    public void update() {
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(this.backgroundImage, LOCATION.x, LOCATION.y,
                SIZE.width, SIZE.height, null);
    }

    @Override
    public void onDamaged(int damage) {
    }

    @Override
    public Rectangle getRange() {
        return RANGE;
    }

    @Override
    public Dimension getBodyOffset() {
        return BODY_OFFSET;
    }

    @Override
    public Dimension getBodySize() {
        return SIZE;
    }
}
