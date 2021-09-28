package views;

import controller.Game;
import menu.GameMenu;
import model.World;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameView extends JFrame {
    public static final int WIDTH = 1400;
    public static final int HEIGHT = 638;
    public static final int BACKGROUND_WIDTH = 1400;
    public static final int BACKGROUND_HEIGHT = 600;
    public static final int TOP_LEFT_X = 251, TOP_LEFT_Y = 109;
    public static final int CELL_WIDTH = 82, CELL_HEIGHT = 92;
    public static final int NUM_COLUMNS = 9, NUM_ROWS = 5;
    public static final int MID_X = TOP_LEFT_X + CELL_WIDTH * NUM_COLUMNS / 2;
    public static final int HOUSE_X = 187;
    private final Canvas canvas = new Canvas();
    private final Game game;
    private final GameMenu gameMenu;

    public GameView(Game game) {
        this.game = game;
        this.game.setView(this.canvas);

        this.gameMenu = new GameMenu(this.game.getWorld());
        this.game.addSprites(this.gameMenu);
        this.gameMenu.addCards();
    }

    public void launch() {
        // GUI stuff
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setContentPane(this.canvas);
        this.setSize(WIDTH, HEIGHT);
        this.setContentPane(this.canvas);
        this.setVisible(true);

        // Mouse listener
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                Point mouseLocation = new Point(e.getX(), e.getY() - 30);
                if (!GameView.this.game.getWorld().mouseEvent(mouseLocation)) {
                    // if the world has not processed the mouse event already
                    // then let the game menu process the mouse event to avoid
                    // giving mouse events to two different entities
                    GameView.this.gameMenu.mouseEvent(mouseLocation);
                }
            }
        });
    }

    public static int getRowIdx(int y) {
        return (y - TOP_LEFT_Y) / CELL_HEIGHT;
    }

    public static int getCellAlignedYFromRowIdx(int rowIdx) {
        return TOP_LEFT_Y + rowIdx * CELL_HEIGHT + CELL_HEIGHT / 2;
    }

    public static int getCellAlignedY(int y) {
        return getCellAlignedYFromRowIdx(getRowIdx(y));
    }

    public static int getColIdx(int x) {
        return (x - TOP_LEFT_X) / CELL_WIDTH;
    }

    public static int getCellAlignedXFromColIdx(int colIdx) {
        return TOP_LEFT_X + colIdx * CELL_WIDTH + CELL_WIDTH / 2;
    }

    public static int getCellAlignedX(int x) {
        return getCellAlignedXFromColIdx(getColIdx(x));
    }

    public static class Canvas extends JPanel implements Game.View {
        private World world;

        @Override
        public void render(World world) {
            this.world = world;
            this.repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(Color.WHITE);
            this.world.render(g);
        }
    }
}
