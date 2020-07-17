package de.acagamics.framework.ui.elements;

import de.acagamics.framework.ui.interfaces.IDrawable;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public class RenderingLayer extends ArrayList<IDrawable> implements IDrawable{

	@Override
	public void draw(GraphicsContext context) {
		for(IDrawable drawable : this) {
			drawable.draw(context);
		}
	}
}
