package menu;

import plant.Jalapeno;

import java.awt.*;

public class JalapenoCard extends Card {
    @Override
    public void setAttributes(Point location, GameMenu menu) {
        super.setAttributes(location,
                "assets/Cards/card_jalapeno.png",
                "assets/Cards/image4.png",
                "assets/Cards/coincard_jalapeno.png",
                menu,
                new Jalapeno.JalapenoFactory(),
                3000,
                100);
    }
}
