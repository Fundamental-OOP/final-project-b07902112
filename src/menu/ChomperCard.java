package menu;

import plant.Chomper;

import java.awt.*;

public class ChomperCard extends Card {
    @Override
    public void setAttributes(Point location, GameMenu menu) {
        super.setAttributes(location,
                "assets/Cards/card_chomper.png",
                "assets/Cards/image1.png",
                "assets/Cards/coincard_chomper.png",
                menu,
                new Chomper.ChomperFactory(),
                1000,
                150);
    }
}