package menu;

import plant.Wallnut;

import java.awt.*;

public class WallnutCard extends Card {
    @Override
    public void setAttributes(Point location, GameMenu menu) {
        super.setAttributes(location,
                "assets/Cards/card_wallnut.png",
                "assets/Cards/image16.png",
                "assets/Cards/coincard_wallnut.png",
                menu,
                new Wallnut.WallnutFactory(),
                3000,
                50);
    }
}