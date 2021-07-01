package menu;

import plant.Peashooter;

import java.awt.*;

public class PeashooterCard extends Card {
    @Override
    public void setAttributes(Point location, GameMenu menu) {
        super.setAttributes(location,
                "assets/Cards/card_peashooter.png",
                "assets/Cards/image5.png",
                "assets/Cards/coincard_peashooter.png",
                menu,
                new Peashooter.PeashooterFactory(),
                1000,
                100);
    }
}