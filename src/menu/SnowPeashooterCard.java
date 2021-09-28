package menu;

import plant.SnowPeashooter;

import java.awt.*;

public class SnowPeashooterCard extends Card {
    @Override
    public void setAttributes(Point location, GameMenu menu) {
        super.setAttributes(location,
                "assets/Cards/card_snowpea.png",
                "assets/Cards/image10.png",
                "assets/Cards/coincard_snowpea.png",
                menu,
                new SnowPeashooter.SnowPeashooterFactory(),
                1000,
                175);
    }
}