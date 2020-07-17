package de.acagamics.framework.geometry;

import javafx.scene.canvas.GraphicsContext;

public class Illustrator {

    private GraphicsContext context;

    public Illustrator(GraphicsContext context) {
        this.context = context;
    }

    /**
     * Draws a geometry object on the canvas.
     * @param geometry an object to be drawn.
     */
    public void draw(Geometry2f geometry){
        if(geometry instanceof Vec2f){
            draw((Vec2f) geometry);
        } else if(geometry instanceof Line2f){
            draw((Line2f) geometry);
        } else if(geometry instanceof Circle2f){
            draw((Circle2f) geometry);
        } else if(geometry instanceof Box2f){
            draw((Box2f) geometry);
        }
    }

    /**
     * Draws a point on the canvas.
     * @param vec a point to be drawn.
     */
    public void draw(Vec2f vec) {
        float radius = (float) context.getLineWidth()/2;
        context.fillOval(vec.getX() - radius,
                vec.getY() - radius, 2 * radius,
                2 * radius);
    }

    /**
     * Draws a line from v1 to v2.
     * @param v1 Starting Point.
     * @param v2 End Point
     */
    public void drawLine(Vec2f v1, Vec2f v2){
        context.strokeLine(v1.getX(), v1.getY(), v2.getX(), v2.getY());
    }

    /**
     * Draws line.
     */
    public void draw(Line2f line){
        context.strokeLine(line.getStart().getX(), line.getStart().getY(), line.getEnd().getX(), line.getEnd().getY());
    }

    /**
     * Draws a circle.
     */
    public void drawCircle(Vec2f position, float radius) {
        context.strokeOval(position.getX() - radius,
                position.getY() - radius, 2 * radius,
                2 * radius);
    }

    /**
     * Draws a circle.
     */
    public void draw(Circle2f circle) {
        context.strokeOval(circle.getPosition().getX() - circle.getRadius(),
                circle.getPosition().getY() - circle.getRadius(), 2 * circle.getRadius(),
                2 * circle.getRadius());
    }

    /**
     * Draws a box.
     */
    public void drawBox(Vec2f position, float width, float height) {
        context.strokeRect(position.getX(),
                position.getY() , width,
                height);
    }

    /**
     * Draws a box.
     */
    public void draw(Box2f box) {
        context.strokeRect(box.getPosition().getX(),
                box.getPosition().getY() , box.getWidth(),
                box.getHeight());
    }
}
