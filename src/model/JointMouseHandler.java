package model;

public class JointMouseHandler implements MouseHandler {
    private final MouseHandler[] mouseHandlers;

    public JointMouseHandler(MouseHandler... mouseHandlers) {
        this.mouseHandlers = mouseHandlers;
    }

    @Override
    public boolean handle(Sprite sprite) {
        for (MouseHandler mouseHandler : this.mouseHandlers) {
            if (mouseHandler.handle(sprite))
                return true;
        }
        return false;
    }
}
