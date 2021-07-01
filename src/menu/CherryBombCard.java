package menu;

import plant.CherryBomb;

import java.awt.*;

public class CherryBombCard extends Card {
    @Override
    public void setAttributes(Point location, GameMenu menu) {
        super.setAttributes(location,
                "assets/Cards/card_cherrybomb.png",
                "assets/Cards/image17.png",
                "assets/Cards/coincard_cherrybomb.png",
                menu,
                new CherryBomb.CherryBombFactory(),
                3000,
                150);
    }
}