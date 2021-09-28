package menu;

import model.MouseHandler;
import model.Sprite;

public class CoinMouseHandler implements MouseHandler {
    @Override
    public boolean handle(Sprite sprite) {
        if (sprite instanceof Coin) {
            Coin coin = (Coin) sprite;
            coin.increaseMoney();
            return true;
        }
        return false;
    }
}
