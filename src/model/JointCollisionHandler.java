package model;

import java.awt.*;

public class JointCollisionHandler implements CollisionHandler {
    private final CollisionHandler[] collisionHandlers;

    public JointCollisionHandler(CollisionHandler... collisionHandlers) {
        this.collisionHandlers = collisionHandlers;
    }

    @Override
    public void handle(Point originalLocation, Sprite from, Sprite to) {
        for (CollisionHandler collisionHandler : this.collisionHandlers)
            collisionHandler.handle(originalLocation, from, to);
    }
}
