package de.acagamics.framework.ui.interfaces;

import javafx.scene.canvas.GraphicsContext;

/**
 * Simple drawing interface.
 */

public interface IDrawable {

	/**
	 * This method is invoked in each step and draws the content on the window
	 * @param context The GraphicsContext is used for drawing
	 */
	void draw(GraphicsContext context);
}
