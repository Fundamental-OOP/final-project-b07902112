package plant;

import menu.GameMenu;
import java.awt.*;

public interface PlantFactory {
    Plant getPlant(Point location, GameMenu menu);
}
