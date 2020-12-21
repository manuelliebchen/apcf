package de.acagamics.framework.geometry;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /**
     * Generates a movement vector for every Circle that moves the Circles to have no collisions anymore.
     * @param circles Circles to be moved
     * @return vectors for circles to be moved
     */
    public static Map<Circle2f, Vec2f> relexCollisions(List<Circle2f> circles) {
        boolean collision = false;
        Map<Circle2f, Vec2f> update = new HashMap<>();
        for(Circle2f c : circles){
            update.put(c, Vec2f.zero());
        }
        float overlapSum;
        do {
            overlapSum = 0;
            for( int i = 0; i < circles.size()-1; ++i){
                for( int j = i+1; j < circles.size(); ++j) {
                    Circle2f c1 = circles.get(i);
                    Circle2f c2 = circles.get(j);
                    Vec2f dist = c1.getPosition().add(update.get(c1)).sub(c2.getPosition().add(update.get(c2)));
                    if(dist.length() < 1E-5f) {
                        continue;
                    }
                    float overlap = -dist.length() + (c1.getRadius() + c2.getRadius());
                    if( overlap > Float.MIN_NORMAL){
                        overlapSum += overlap;
                        Vec2f divHalf = dist.getNormalized().mult(overlap/2);
                        update.put(c1, update.get(c1).add(divHalf));
                        update.put(c2, update.get(c2).add(divHalf.mult(-1)));
                    }
                }
            }
        } while(overlapSum > 1E-5f);
        return update;
    }

    public Vec2f clip(Vec2f pos){
        if(isInside(pos)){
            return pos;
        }
        return pos.sub(position).clipLenght(radius).add(position);
    }
}
