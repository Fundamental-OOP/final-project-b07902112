package controller;

import model.World;

public class GameLoop {
    protected final World world;
    protected View view;
    private final long gameLoopDelay;

    public GameLoop(World world, long gameLoopDelay) {
        this.world = world;
        this.gameLoopDelay = gameLoopDelay;
    }

    public void setView(View view) {
        this.view = view;
    }

    public void start() {
        new Thread(this::gameLoop).start();
    }

    public boolean gameIsOver() {
        return false;
    }

    public void gameOver() {
    }

    public void gameLoop() {
        while (!this.gameIsOver()) {
            this.world.update();
            this.view.render(this.world);
            this.delay(this.gameLoopDelay);
        }
        this.gameOver();
        this.world.update();
        this.view.render(this.world);
    }

    public void delay(long ms) {
        try {
            Thread.sleep(ms);
        } catch (java.lang.InterruptedException exception) {
            System.err.printf(
                    "[Error] unable to sleep in game loop, cause: %s\n",
                    exception.getCause().toString());
        }
    }

    public World getWorld() {
        return this.world;
    }

    public interface View {
        void render(World world);
    }
}
