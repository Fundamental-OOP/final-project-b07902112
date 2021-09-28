package model;

import java.awt.*;

public class JointInRangeHandler implements InRangeHandler {
    private final InRangeHandler[] inRangeHandlers;

    public JointInRangeHandler(InRangeHandler... inRangeHandlers) {
        this.inRangeHandlers = inRangeHandlers;
    }

    @Override
    public void handle(Point originalLocation, Sprite from, Sprite to) {
        for (InRangeHandler inRangeHandler : this.inRangeHandlers)
            inRangeHandler.handle(originalLocation, from, to);
    }
}
