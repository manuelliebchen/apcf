package de.acagamics.framework.ui.interfaces;

import de.acagamics.framework.geometry.Vec2f;

public abstract class GameObject {
    protected Vec2f position;

    public GameObject(Vec2f position) {
        this.position = position;
    }

    public Vec2f getPosition() {
        return position;
    }
}
