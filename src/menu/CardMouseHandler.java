package menu;

import model.MouseHandler;
import model.Sprite;

public class CardMouseHandler implements MouseHandler {
    @Override
    public boolean handle(Sprite sprite) {
        if (sprite instanceof Card) {
            Card card = (Card) sprite;
            if (card.ready()) {
                // clicking on a card with remaining uses and enough sun toggles
                // the selection of card to plant
                if (card.selected()) {
                    card.getGameMenu().changeCard(null);
                    card.deselect();
                } else {
                    card.getGameMenu().changeCard(card);
                    card.select();
                }
            } else if (card.noRemainingUses()) {
                // clicking on a card with no remaining uses attempts to buy the
                // card
                card.attemptToBuy();
            }
            return true;
        }
        return false;
    }
}
