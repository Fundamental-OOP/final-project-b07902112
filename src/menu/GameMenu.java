package menu;

import controller.Game;
import views.GameView;
import zombie.ZombieGenerator;
import media.AudioPlayer;
import model.Sprite;
import model.World;
import plant.Plant;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Path;

import static views.GameView.*;

public class GameMenu extends Sprite {
    public static final String AUDIO_PLANT = "PlantBeingPlanted1";
    public static final Point LOCATION = new Point(0, 0);
    public static final Dimension BODY_OFFSET = new Dimension(0, 0);
    public static final Dimension SIZE = new Dimension(1032, 87);
    public static final Dimension RANGE = BODY_OFFSET;
    public static final Rectangle SUN_RECT = new Rectangle(
            12, 61, 66 - 12, 83 - 61);
    public static final Rectangle MONEY_IMAGE_RECT = new Rectangle(
            0, BACKGROUND_HEIGHT - 23 * 2, 94 * 2, 23 * 2);
    public static final Rectangle MONEY_RECT = new Rectangle(
            27 * 2, BACKGROUND_HEIGHT - 18 * 2, (94 - 27) * 2, 18 * 2);
    public static final Point SHOVEL_LOCATION = new Point(1032, 0);
    public static final Font FONT = new Font("Times New Roman", Font.BOLD, 16);
    private final Plant[][] plants = new Plant[NUM_COLUMNS][NUM_ROWS];
    private Shovel shovel;
    private Image selectorImage;
    private Image moneyImage;
    private int numSun = 50;
    private Card currentCard = null;
    private int money = 200;

    public GameMenu(World world) {
        this.setLocation(this.location);
        this.setWorld(world);

        try {
            this.selectorImage =
                    ImageIO.read(Path.of("assets/Screen/ChooserBackground" +
                            ".png").toFile());
            this.moneyImage =
                    ImageIO.read(Path.of("assets/Screen/money.png").toFile());
        } catch (IOException e) {
            System.err.println("[Error] unable to read images for menu");
            System.exit(-1);
        }

        world.addSprites(
                new Background(),
                new ZombieGenerator(this));

        if (Game.DEBUG) {
            this.numSun = 100000;
            this.money = 100000;
        }
    }

    public void addCards() {
        Card[] cards = {
                new SunflowerCard(),
                new PeashooterCard(),
                new WallnutCard(),
                new WallnutCard(),
                new CherryBombCard(),
                new CherryBombCard(),
                new CherryBombCard(),
                new PotatoMineCard(),
                new PotatoMineCard(),
                new SnowPeashooterCard(),
                new RepeaterPeashooterCard(),
                new ChomperCard(),
                new JalapenoCard(),
                new JalapenoCard(),
                new JalapenoCard(),
                new SquashCard(),
                new SquashCard(),
        };
        for (int i = 0; i < cards.length; ++i) {
            Card card = cards[i];
            Point location = new Point(75 + 56 * i, 5);
            card.setAttributes(location, this);
            this.getWorld().addSprites(card);
        }

        this.shovel = new Shovel(SHOVEL_LOCATION);
        this.getWorld().addSprites(this.shovel);
    }

    public void increaseSun() {
        this.numSun += 25;
    }

    @Override
    public void update() {
    }

    @Override
    public void render(Graphics g) {
        // card selector
        g.drawImage(this.selectorImage, LOCATION.x, LOCATION.y,
                SIZE.width, SIZE.height, null);

        // sun counter
        g.setColor(Color.BLACK);
        String text = "" + this.numSun;
        FontMetrics metrics = g.getFontMetrics(FONT);
        int x = SUN_RECT.x + (SUN_RECT.width - metrics.stringWidth(text)) / 2;
        int y = SUN_RECT.y + ((SUN_RECT.height - metrics.getHeight()) / 2) +
                metrics.getAscent();
        g.setFont(FONT);
        g.drawString(text, x, y);

        // money
        g.drawImage(this.moneyImage, MONEY_IMAGE_RECT.x, MONEY_IMAGE_RECT.y,
                MONEY_IMAGE_RECT.width, MONEY_IMAGE_RECT.height, null);
        g.setColor(Color.WHITE);
        text = "" + this.money;
        x = MONEY_RECT.x + (MONEY_RECT.width - metrics.stringWidth(text)) / 2;
        y = MONEY_RECT.y + ((MONEY_RECT.height - metrics.getHeight()) / 2) +
                metrics.getAscent();
        g.drawString(text, x, y);
    }

    @Override
    public void onDamaged(int damage) {
    }

    @Override
    public Rectangle getRange() {
        return new Rectangle(LOCATION, RANGE);
    }

    @Override
    public Dimension getBodyOffset() {
        return BODY_OFFSET;
    }

    @Override
    public Dimension getBodySize() {
        return SIZE;
    }

    public void mouseEvent(Point mouseLocation) {
        int i = GameView.getColIdx(mouseLocation.x);
        int j = GameView.getRowIdx(mouseLocation.y);
        Point location = new Point(
                GameView.getCellAlignedXFromColIdx(i),
                GameView.getCellAlignedYFromRowIdx(j));
        if (i >= 0 && i < NUM_COLUMNS &&
                j >= 0 && j < NUM_ROWS) {
            if (this.shovel.selected() && this.plants[i][j] != null) {
                // if shovel is selected and cell has plant, then remove it
                AudioPlayer.playSounds(AUDIO_PLANT);
                Plant plant = this.plants[i][j];
                plant.setWorld(null);
                this.plants[i][j] = null;
                this.shovel.deselect();
            } else if (this.plants[i][j] == null &&
                    this.currentCard != null &&
                    this.currentCard.ready()) {
                // if card is selected, card is ready and cell has no plant,
                // then plant in that cell
                AudioPlayer.playSounds(AUDIO_PLANT);
                this.plants[i][j] = this.currentCard.getPlantFactory().getPlant(
                        location, this);
                this.world.addSprites(this.plants[i][j]);
                this.currentCard.coolDown();
                this.numSun -= this.currentCard.getCost();
                this.currentCard = null;
            }
        }
    }

    public void changeCard(Card card) {
        if (this.currentCard != null)
            this.currentCard.deselect();
        this.currentCard = card;
    }

    public int getSun() {
        return this.numSun;
    }

    public void clearCell(Plant plant) {
        int colIdx = plant.getColIdx();
        int rowIdx = plant.getRowIdx();
        this.plants[colIdx][rowIdx] = null;
    }

    public void increaseMoney() {
        this.money += 25;
    }

    public void decreaseMoney(int amount) {
        this.money -= amount;
    }

    public int getMoney() {
        return this.money;
    }
}
