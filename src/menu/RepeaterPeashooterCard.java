package menu;

import plant.RepeaterPeashooter;

import java.awt.*;

public class RepeaterPeashooterCard extends Card {
    @Override
    public void setAttributes(Point location, GameMenu menu) {
        super.setAttributes(location,
                "assets/Cards/card_repeaterpea.png",
                "assets/Cards/image8.png",
                "assets/Cards/coincard_repeaterpea.png",
                menu,
                new RepeaterPeashooter.RepeaterPeashooterFactory(),
                1000,
                200);
    }
}