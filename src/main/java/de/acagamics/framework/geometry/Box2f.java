package de.acagamics.framework.geometry;

import javafx.scene.image.Image;

import java.util.Arrays;
import java.util.List;

public class Box2f extends Volume2f {

    private final Vec2f position;
    private final float width;
    private final float height;

    public Box2f() {
        this.position = new Vec2f();
        this.width = 0;
        this.height = 0;
    }

    public Box2f(Image image, Vec2f position) {
        this.position = position;
        this.width = (float) image.getWidth();
        this.height = (float) image.getHeight();
    }

    public Box2f(Vec2f position, Vec2f size) {
        this.position = position;
        this.width = size.getX();
        this.height = size.getY();
    }

    public Box2f(Vec2f position, float width, float height) {
        this.position = position;
        this.width = width;
        this.height = height;
    }

    public Box2f(Vec2f ... positionCollection){
        List<Vec2f> positions = Arrays.asList(positionCollection);
        if(!positions.isEmpty()) {
            float xMin = positions.get(0).getX();
            float xMax = positions.get(0).getX();
            float yMin = positions.get(0).getY();
            float yMax = positions.get(0).getY();
            for(Vec2f vec : positions) {
                xMin = Math.min(xMin, vec.getX());
                xMax = Math.max(xMax, vec.getX());
                yMin = Math.min(yMin, vec.getY());
                yMax = Math.max(yMax, vec.getY());
            }
            this.position = new Vec2f(xMin, yMin);
            this.width = xMax - xMin;
            this.height = yMax - yMin;
        } else {
            this.position = new Vec2f();
            this.width = 0;
            this.height = 0;
        }
    }

    public Box2f translate(Vec2f position) {
        return new Box2f(this.position.add(position), width, height);
    }

    public Box2f scale(float scalation) {
        return new Box2f(position, width * scalation, height * scalation);
    }
    public Box2f scale(float scalationX, float scalationY) {
        return new Box2f(position, width * scalationX, height * scalationY);
    }

    public List<Vec2f> getCorners() {
        return Arrays.asList(position, position.add(new Vec2f(width, 0)), position.add(new Vec2f(0, height)), position.add(new Vec2f(width, height)));
    }

    public boolean isInside(Vec2f vector){
        return vector.getX() > position.getX() && vector.getX() < position.getX() + width && vector.getY() > position.getY() && vector.getY() < position.getY() + height;
    }

    public Vec2f clip(Vec2f pos){
        if(isInside(pos)){
            return pos;
        }
        if(pos.getX() < position.getX()) {
            pos = new Vec2f(position.getX(), pos.getY());
        } else if(pos.getX() > position.getX() + width){
            pos = new Vec2f(position.getX() + width, pos.getY());
        }
        if(pos.getY() < position.getY()) {
            pos = new Vec2f(pos.getX(), position.getY());
        } else if(pos.getY() > position.getY() + height){
            pos = new Vec2f(pos.getX(), position.getY() + height);
        }
        return pos;
    }

    public Vec2f getPosition() {
        return position;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public Vec2f getSize() {
        return new Vec2f(width, height);
    }

    /**
     * Checks if vectors have same values.
     * @param other vector to be checked for equality
     * @return if vectors have same values
     */
    @Override
    public boolean equals(Object other) {
        if(other == null || getClass() != other.getClass()){
            return false;
        }
        if(other == this){
            return true;
        }
        List<Vec2f> rhsCorners = ((Box2f) other).getCorners();
        List<Vec2f> corners = getCorners();
        for(int i = 0; i < 4; i++){
            if(!rhsCorners.get(i).equals(corners.get(i))){
                return false;
            }
        }
        return true;
    }
}
