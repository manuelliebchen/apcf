package de.acagamics.framework.ui.elements;

import de.acagamics.framework.geometry.Vec2f;
import de.acagamics.framework.ui.interfaces.IDrawable;
import javafx.scene.canvas.GraphicsContext;

import java.util.function.Supplier;

/**
 * Dynamic TextBox.
 */

public final class DynamicTextBox extends TextBox implements IDrawable {

	// Drawing status
	private Supplier<String> textSupplier;

	public DynamicTextBox(Vec2f relativPosition, Supplier<String> textSupplier) {
		super(relativPosition, textSupplier.get());
		this.text = textSupplier.get();
		this.textSupplier = textSupplier;
		
		calcFontTextSize();
	}

	/**
	 * Draws the button. First button image (imgUp,imgDown,imgInActive). Second:
	 * button text
	 * 
	 * @param context GraphicsContext for rendering the state when active.
	 */
	@Override
	public void draw(GraphicsContext context) {
		this.text = textSupplier.get();
		calcFontTextSize();
		
		super.draw(context);
	}
}
