package menu;

import model.Sprite;
import views.GameView;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Path;

public class GameOverText extends Sprite {
    public static final Dimension SIZE = new Dimension(500, 414);
    public static final Rectangle RANGE = new Rectangle(0, 0, 0, 0);
    public static final Dimension BODY_OFFSET = new Dimension(0, 0);
    private Image gameOverImage;

    public GameOverText() {
        try {
            this.gameOverImage = ImageIO.read(
                    Path.of("assets/Screen/PvZ1ZombiesWon.png").toFile());
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
        g.drawImage(this.gameOverImage,
                GameView.WIDTH / 2 - SIZE.width / 2,
                GameView.HEIGHT / 2 - SIZE.height / 2,
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
