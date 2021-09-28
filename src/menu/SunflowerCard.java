package menu;

import plant.Sunflower;

import java.awt.*;

public class SunflowerCard extends Card {
    @Override
    public void setAttributes(Point location, GameMenu menu) {
        super.setAttributes(location,
                "assets/Cards/card_sunflower.png",
                "assets/Cards/image13.png",
                "assets/Cards/coincard_sunflower.png",
                menu,
                new Sunflower.SunflowerPlantFactory(),
                1000,
                50);
    }
}