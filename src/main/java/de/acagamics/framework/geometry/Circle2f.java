package de.acagamics.framework.geometry;

public class Circle2f extends Volume2f {
    private final Vec2f position;
    private final float radius;

    public Circle2f() {
        this.position = new Vec2f();
        this.radius = 1;
    }

    public Circle2f(Vec2f position) {
        this.position = position;
        this.radius = 1;
    }

    public Circle2f(Vec2f position, float radius) {
        this.position = position;
        this.radius = radius;
    }

    public Circle2f move(float x, float y) {
        return new Circle2f(this.position.add(x, y), radius);
    }

    public Circle2f move(Vec2f position) {
        return new Circle2f(this.position.add(position), radius);
    }

    public Circle2f scale(float scalation) {
        return new Circle2f(this.position, radius * scalation);
    }

    public Vec2f getPosition() {
        return position;
    }

    public float getRadius() {
        return radius;
    }

    @Override
    public boolean isInside(Vec2f vector) {
        return position.distance(vector) < radius;
    }

    public Vec2f clip(Vec2f pos){
        if(isInside(pos)){
            return pos;
        }
        return pos.sub(position).clipLenght(radius).add(position);
    }
}
