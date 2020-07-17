package de.acagamics.framework.ui.elements;

import de.acagamics.framework.geometry.Box2f;
import de.acagamics.framework.geometry.Vec2f;
import de.acagamics.framework.resources.ResourceManager;
import de.acagamics.framework.ui.interfaces.ALIGNMENT;
import de.acagamics.framework.ui.interfaces.UIElement;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class ImageElement extends UIElement {

	protected Image image;
	protected float size;
	protected Alignment alignment;

	public ImageElement(Vec2f relativPosition, String image, float size) {
		super(new Box2f(ResourceManager.getInstance().loadImage(image), relativPosition));
		this.image = ResourceManager.getInstance().loadImage(image);
		alignment = new Alignment(relativPosition);
		this.size = size;
	}

	public ImageElement setVerticalAlignment(ALIGNMENT verticalAlignment) {
		this.alignment.setVerticalAlignment( verticalAlignment);
		return this;
	}

	public ImageElement setHorizontalAlignment(ALIGNMENT horizontalAlignment) {
		this.alignment.setHorizontalAlignment( horizontalAlignment);
		return this;
	}

	@Override
	public void draw(GraphicsContext context) {
		Vec2f position = alignment.getAlignedPosition(context);
		float ration = (float) (image.getHeight() / image.getWidth());
		context.drawImage(image, position.getX() - size, position.getY() - size * ration, 2 * size, 2 * size * ration);
	}

}
