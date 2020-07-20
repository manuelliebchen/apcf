package de.acagamics.framework.ui.elements;

import de.acagamics.framework.resources.DesignProperties;
import de.acagamics.framework.resources.ResourceManager;
import de.acagamics.framework.ui.interfaces.IDrawable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class Background implements IDrawable {
    @Override
    public void draw(GraphicsContext context) {
        context.setFill(ResourceManager.getInstance().loadProperties(DesignProperties.class).getBackgroundColor());
        Canvas canvas = context.getCanvas();
        context.fillRect(0,0, canvas.getWidth(), canvas.getHeight());
    }
}
