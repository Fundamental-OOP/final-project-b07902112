package menu;

import controller.Game;
import model.Sprite;
import plant.PlantFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;

public abstract class Card extends Sprite {
    public static final Dimension SIZE = new Dimension(53, 74);
    public static final Dimension RANGE = SIZE;
    public static final int VALID_USES = 5;
    private GameMenu menu;
    private PlantFactory plantFactory;
    private BufferedImage image, darkImage, moneyImage;
    private int coolDownDuration;
    private int coolDownCounter;
    private int cost;
    private boolean selected = false;
    private int remainingUses = 0;

    public abstract void setAttributes(Point location, GameMenu menu);

    public void setAttributes(Point location, String imagePath,
                              String darkImagePath, String moneyImagePath,
                              GameMenu menu, PlantFactory plantFactory,
                              int coolDownDuration, int cost) {
        this.location = location;

        this.menu = menu;
        this.plantFactory = plantFactory;

        try {
            this.image = ImageIO.read(Path.of(imagePath).toFile());
            this.darkImage = ImageIO.read(Path.of(darkImagePath).toFile());
            this.moneyImage = ImageIO.read(Path.of(moneyImagePath).toFile());
        } catch (IOException e) {
            System.err.println("[Error] unable to read card images");
            System.exit(-1);
        }

        if (Game.DEBUG)
            this.coolDownDuration = 10;
        else
            this.coolDownDuration = coolDownDuration;
        this.coolDownCounter = 0;
        this.cost = cost;
    }

    @Override
    public void update() {
        if (this.coolDownCounter != 0)
            --this.coolDownCounter;
    }

    @Override
    public void render(Graphics g) {
        if (this.remainingUses == 0) {
            // out of remaining uses, need to spend money
            g.drawImage(this.moneyImage, this.location.x, this.location.y,
                    SIZE.width, SIZE.height, null);
        } else {
            // still has remaining uses, so show normal sun cards
            g.drawImage(this.darkImage, this.location.x, this.location.y,
                    SIZE.width, SIZE.height, null);
            float ratio =
                    1 - (float) this.coolDownCounter / this.coolDownDuration;
            if (ratio < 1 || this.getCost() <= this.menu.getSun()) {
                // show the parts of bright card if the cool down is not done
                // yet, or show the full bright card if the card is ready and
                // the player has enough sun
                int subImageHeight = (int) (this.darkImage.getHeight() * ratio);
                int drawImageHeight = (int) (SIZE.height * ratio);
                if (drawImageHeight > 0) {
                    Image brightPart = this.image.getSubimage(
                            0, this.darkImage.getHeight() - subImageHeight,
                            this.darkImage.getWidth(), subImageHeight);
                    g.drawImage(brightPart,
                            this.location.x,
                            this.location.y + SIZE.height - drawImageHeight,
                            SIZE.width, drawImageHeight, null);
                }
                if (this.selected) {
                    g.setColor(Color.BLUE);
                    ((Graphics2D) g).setStroke(new BasicStroke(2));
                    g.drawRect(this.location.x, this.location.y,
                            SIZE.width, SIZE.height);
                }
            }
        }
    }

    @Override
    public void onDamaged(int damage) {
    }

    @Override
    public Rectangle getRange() {
        return new Rectangle(this.location, RANGE);
    }

    @Override
    public Dimension getBodyOffset() {
        return new Dimension(0, 0);
    }

    @Override
    public Dimension getBodySize() {
        return SIZE;
    }

    public GameMenu getGameMenu() {
        return this.menu;
    }

    public PlantFactory getPlantFactory() {
        return this.plantFactory;
    }

    public void coolDown() {
        this.coolDownCounter = this.coolDownDuration;
        this.selected = false;
        --this.remainingUses;
    }

    public boolean ready() {
        return this.remainingUses > 0 &&
                this.coolDownCounter == 0 &&
                this.cost <= this.menu.getSun();
    }

    public void select() {
        this.selected = true;
    }

    public void deselect() {
        this.selected = false;
    }

    public boolean selected() {
        return this.selected;
    }

    public int getCost() {
        return this.cost;
    }

    public boolean noRemainingUses() {
        return this.remainingUses == 0;
    }

    public void attemptToBuy() {
        if (this.menu.getMoney() >= this.cost) {
            this.menu.decreaseMoney(this.cost);
            this.remainingUses = VALID_USES;
        }
    }
}
