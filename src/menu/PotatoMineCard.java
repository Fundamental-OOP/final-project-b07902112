package menu;

import plant.PotatoMine;

import java.awt.*;

public class PotatoMineCard extends Card {
    @Override
    public void setAttributes(Point location, GameMenu menu) {
        super.setAttributes(location,
                "assets/Cards/card_potatomine.png",
                "assets/Cards/image6.png",
                "assets/Cards/coincard_potatomine.png",
                menu,
                new PotatoMine.PotatoMineFactory(),
                3000,
                25);
    }
}