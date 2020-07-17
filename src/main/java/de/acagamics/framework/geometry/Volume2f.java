package de.acagamics.framework.geometry;

public abstract class Volume2f extends Geometry2f {

    public abstract boolean isInside(Vec2f vector);

    public abstract Vec2f clip(Vec2f pos);
}
