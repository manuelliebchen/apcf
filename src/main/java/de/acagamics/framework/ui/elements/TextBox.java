package de.acagamics.framework.ui.elements;

import de.acagamics.framework.geometry.Box2f;
import de.acagamics.framework.geometry.Vec2f;
import de.acagamics.framework.resources.DesignProperties;
import de.acagamics.framework.resources.ResourceManager;
import de.acagamics.framework.ui.interfaces.ALIGNMENT;
import de.acagamics.framework.ui.interfaces.UIElement;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Image buttons with customs images and text. Registers if a user pressed on it.
 */

public class TextBox extends UIElement {

	// Drawing status
	protected String text = "";
	protected Color textColor = ResourceManager.getInstance().loadProperties(DesignProperties.class).getForegroundColor();
	protected Vec2f size = Vec2f.zero();
	protected Alignment alignment;
	protected Font font = ResourceManager.getInstance().loadProperties(DesignProperties.class).getStandartFont();
	protected ALIGNMENT textAlignment = ALIGNMENT.CENTER;

	/**
	 * Default button constructor with default buttons images and text color
	 * (black).
	 * 
	 * @param relativPosition   The relativ position where the button will be drawn.
	 * @param text The Text, which will be displayed on the button.
	 */
	public TextBox(Vec2f relativPosition, String text) {
		alignment = new Alignment(relativPosition);
		this.text = text;
		calcFontTextSize();
		this.box = new Box2f(relativPosition, size.getX(),size.getY());
	}

	public TextBox setFont(Font font) {
		this.font = font;
		calcFontTextSize();
		return this;
	}

	public TextBox setTextAlignment(ALIGNMENT textAlignment) {
		this.textAlignment = textAlignment;
		return this;
	}

	public TextBox setTextColor(Color textColor) {
		this.textColor = textColor;
		return this;
	}

	public TextBox setVerticalAlignment(ALIGNMENT verticalAlignment) {
		this.alignment.setVerticalAlignment( verticalAlignment);
		return this;
	}

	public TextBox setHorizontalAlignment(ALIGNMENT horizontalAlignment) {
		this.alignment.setHorizontalAlignment( horizontalAlignment);
		return this;
	}

	/**
	 * Change text of this TextBox.
	 * 
	 * @see #calcFontTextSize
	 * @param text The new Text of this TextBox
	 * @return this {@link TextBox} for further modifications.
	 */
	public TextBox setText(String text) {
		this.text = text;
		return this;
	}

	/**
	 * @param context GraphicsContext for rendering the state when active.
	 */
	public void draw(GraphicsContext context) {
		Vec2f position = alignment.getAlignedPosition(context);
		position = position.add(size.mult(-(textAlignment.getValue()), 0.5f));

		context.setFont(font);
		context.setFill(textColor);
		context.fillText(text, position.getX(), position.getY());
	}

	/**
	 * Updates buttonTextSize and centeredPosition if font oder text has changed
	 * 
	 * @see #setFont
	 * @see #setText
	 */
	protected void calcFontTextSize() {
		Text jfxtext = new Text(text);
		jfxtext.setFont(font);

		size = new Vec2f((float) jfxtext.getLayoutBounds().getWidth(), (float) jfxtext.getLayoutBounds().getHeight());
	}
}
