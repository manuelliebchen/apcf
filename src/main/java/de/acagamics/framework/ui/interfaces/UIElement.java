package de.acagamics.framework.ui.interfaces;

import de.acagamics.framework.geometry.Box2f;

public abstract class UIElement implements IDrawable {

    protected Box2f box;

    public UIElement() {
        box = new Box2f();
    }

    public UIElement(Box2f box) {
        this.box = box;
    }

    public Box2f getSize() {
        return box;
    }
}
