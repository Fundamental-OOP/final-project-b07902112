package menu;

import media.AudioPlayer;
import model.Sprite;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Path;

public class Shovel extends Sprite {
    public static final String AUDIO_PICKED = "Shovel";
    public static final Dimension SIZE = new Dimension(70, 70);
    public static final Rectangle RANGE = new Rectangle(0, 0, 0, 0);
    public static final Dimension BODY_OFFSET = new Dimension(0, 0);
    private Image shovelImage;
    private boolean selected = false;

    public Shovel(Point location) {
        this.location = location;
        try {
            this.shovelImage =
                    ImageIO.read(Path.of("assets/Screen/shovel.jpg").toFile());
        } catch (IOException e) {
            System.err.println("[Error] unable to read shovel image");
            System.exit(-1);
        }
    }

    @Override
    public void update() {
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(this.shovelImage, this.location.x, this.location.y,
                SIZE.width, SIZE.height, null);
        if (this.selected) {
            g.setColor(Color.BLUE);
            ((Graphics2D) g).setStroke(new BasicStroke(2));
            g.drawRect(this.location.x, this.location.y,
                    SIZE.width, SIZE.height);
        }
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

    public boolean selected() {
        return this.selected;
    }

    public void select() {
        this.selected = true;
        AudioPlayer.playSounds(AUDIO_PICKED);
    }

    public void deselect() {
        this.selected = false;
    }
}
