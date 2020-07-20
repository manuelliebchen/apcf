package de.acagamics.framework.ui.elements;

import de.acagamics.framework.geometry.Box2f;
import de.acagamics.framework.geometry.Vec2f;
import de.acagamics.framework.resources.ResourceManager;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Texture {
    Box2f box;
    Image image;
    float ratio;
    boolean flip;

    public Texture(Vec2f offset, String file, float radius, boolean flip){
        this.flip = flip;
        image = ResourceManager.getInstance().loadImage(file);
        ratio = (float) (image.getWidth() / image.getHeight() + 1) / 2;
        box = new Box2f(offset, radius*ratio, radius/ratio);
    }

    public void draw(GraphicsContext context, Vec2f position) {
        float flipFactor = flip ? 1 : -1;
        context.drawImage(image, position.getX() + box.getPosition().getX() * box.getWidth() - (flipFactor * box.getWidth()),
                position.getY() + box.getPosition().getY() * box.getHeight() - box.getHeight() , 2 * flipFactor * box.getWidth(),
                2 * box.getHeight() );
    }

}
