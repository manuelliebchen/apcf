package de.acagamics.framework.geometry;

public class Line2f extends Geometry2f {

    private final Vec2f start;
    private final Vec2f end;

    public Line2f() {
        this.start = new Vec2f();
        this.end = new Vec2f();
    }

    public Line2f(Vec2f end) {
        this.start = new Vec2f();
        this.end = end;
    }

    public Line2f(Vec2f start, Vec2f end) {
        this.start = start;
        this.end = end;
    }

    public Line2f move(Vec2f position) {
        return new Line2f(start.add(position), end.add(position));
    }

    public Line2f moveEnd(Vec2f position) {
        return new Line2f(start, end.add(position));
    }

    public Vec2f getStart() {
        return start;
    }

    public Vec2f getEnd() {
        return end;
    }
}
