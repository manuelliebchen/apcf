package de.acagamics.framework.ui.elements;

import de.acagamics.framework.geometry.Vec2f;
import de.acagamics.framework.ui.interfaces.ALIGNMENT;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class Alignment {

    private final Vec2f relativPosition;
    protected ALIGNMENT verticalAlignment = ALIGNMENT.LEFT;
    protected ALIGNMENT horizontalAlignment = ALIGNMENT.UPPER;

    protected Alignment(Vec2f relativPosition) {
        this.relativPosition = relativPosition;
    }

    public Alignment setVerticalAlignment(ALIGNMENT verticalAlignment) {
        this.verticalAlignment = verticalAlignment;
        return this;
    }

    public Alignment setHorizontalAlignment(ALIGNMENT horizontalAlignment) {
        this.horizontalAlignment = horizontalAlignment;
        return this;
    }

    public Vec2f getAlignedPosition(GraphicsContext graphics) {
        Canvas canvas = graphics.getCanvas();
        return relativPosition.add((float) canvas.getWidth() * verticalAlignment.getValue(), (float) canvas.getHeight() * horizontalAlignment.getValue());
    }

    public Vec2f transpose(GraphicsContext graphics, Vec2f position) {
        Canvas canvas = graphics.getCanvas();
        return position.sub((float) canvas.getWidth() * verticalAlignment.getValue(), (float) canvas.getHeight() * horizontalAlignment.getValue());
    }

    public Vec2f getRelativPosition() {
        return relativPosition;
    }
}
