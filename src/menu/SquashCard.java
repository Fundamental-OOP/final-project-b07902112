package menu;

import plant.Squash;

import java.awt.*;

public class SquashCard extends Card {
    @Override
    public void setAttributes(Point location, GameMenu menu) {
        super.setAttributes(location,
                "assets/Cards/card_squash.png",
                "assets/Cards/image12.png",
                "assets/Cards/coincard_squash.png",
                menu,
                new Squash.SquashFactory(),
                1000,
                50);
    }
}
